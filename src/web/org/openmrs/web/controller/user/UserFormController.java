package org.openmrs.web.controller.user;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.web.WebConstants;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class UserFormController extends SimpleFormController {
	
    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    
    /**
	 * 
	 * Allows for other Objects to be used as values in input tags.
	 *   Normally, only strings and lists are expected 
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest, org.springframework.web.bind.ServletRequestDataBinder)
	 */
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		
		Context context = (Context) request.getSession().getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		
		binder.registerCustomEditor(java.lang.Integer.class, 
				new CustomNumberEditor(java.lang.Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, 
        		new CustomDateEditor(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, context.getLocale()), true));
	}

	/**
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#processFormSubmission(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object obj, BindException errors) throws Exception {
		
		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		User user = (User)obj;
		UserService us = context.getUserService();
		
		if (context != null && context.isAuthenticated()) {
			// check if username is already in the database
				if (us.hasDuplicateUsername(user)) {
					errors.rejectValue("username", "error.username.taken");
				}
				
			// check if password and password confirm are identical
				String password = request.getParameter("password");
				if (password == null) password = "";
				String confirm = request.getParameter("confirm");
				if (confirm == null) confirm = "";
				
				if (!password.equals(confirm))
					errors.reject("error.password.match");
				
				if (password.length() == 0 && user.getUserId() == null)
					errors.reject("error.password.weak");
				
			//check password strength
				if (password.length() > 0) {
					if (password.length() < 6)
						errors.reject("error.password.length");
					if (StringUtils.isAlpha(password))
						errors.reject("error.password.characters");
					if (password.equals(user.getUsername()) || password.equals(user.getSystemId()))
						errors.reject("error.password.weak");
				}
					
			// add Roles to user (because spring can't handle lists as properties...)
				String[] roles = request.getParameterValues("roles");
				Set<Role> newRoles = new HashSet<Role>();
				if (roles != null) {
					for (String r : roles) {
						Role role = us.getRole(r);
						newRoles.add(role);
						user.addRole(role);
					}
				}
				
				/*  TODO check if user can delete privilege
				Collection<Collection> lists = Helper.compareLists(user.getRoles(), set);
				
				Collection toDel = (Collection)lists.toArray()[1];
				for (Object o : toDel) {
					Role r = (Role)o;
					for (Privilege p : r.getPrivileges())
						if (!user.hasPrivilege(p.getPrivilege()))
							throw new APIException("Privilege required: " + p.getPrivilege());
				}
				*/
				
				if (user.getRoles() == null)
					newRoles.clear();
				else
					user.getRoles().retainAll(newRoles);
		}
		else {
			errors.reject("auth.invalid");
		}
				
		return super.processFormSubmission(request, response, obj, errors);
	}
	

	/**
	 * 
	 * The onSubmit function receives the form/command object that was modified
	 *   by the input form and saves it to the db
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object obj, BindException errors) throws Exception {
		
		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		User user = (User)obj;
		String view = getFormView();
		
		if (context != null && context.isAuthenticated()) {
			
			UserService us = context.getUserService();

			String password = request.getParameter("password");
			
			Map<String, String> properties = user.getProperties();
			if (properties == null)
				properties = new HashMap<String, String>();
			
			Boolean newChangePassword = false;
			String chk = request.getParameter(OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD);
			
			if (chk != null)
				newChangePassword = true;
			
			if (!newChangePassword.booleanValue() && properties.containsKey(OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD)) {
				properties.remove(OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD);
			}
			if (newChangePassword.booleanValue()) {
				properties.put(OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD, newChangePassword.toString());
			}
			
			user.setProperties(properties);
			
			if ((context.getAuthenticatedUser().isSuperUser() && password != null) && !password.equals("")) {
				log.debug("calling changePassword");
				us.changePassword(user, password);
			}
			
			if (user.getUserId() == null)
				us.createUser(user, password);
			else
				us.updateUser(user);
			
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "User.saved");
			view = getSuccessView();
		}
		
		return new ModelAndView(new RedirectView(view));
	}

	/**
	 * 
	 * This is called prior to displaying a form for the first time.  It tells Spring
	 *   the form/command object to load into the request
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		
		User user = null;
		
		if (context != null && context.isAuthenticated()) {
			UserService us = context.getUserService();
			String userId = request.getParameter("userId");
	    	if (userId != null)
	    		user = us.getUser(Integer.valueOf(userId));
		}
		
		if (user == null)
			user = new User();
		
        return user;
    }
    
    protected Map referenceData(HttpServletRequest request, Object obj, Errors errors) throws Exception {
		
		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = (User)obj;
		
		List<Role> roles = context.getUserService().getRoles();
		if (roles == null)
			roles = new Vector<Role>();
		
		for (String s : OpenmrsConstants.AUTO_ROLES()) {
			Role r = new Role(s);
			roles.remove(r);
		}
		
		if (context != null && context.isAuthenticated()) {
			map.put("roles", roles);
			if (user.getUserId() == null || context.hasPrivilege("Edit Passwords")) 
				map.put("modifyPasswords", true);
			map.put("changePasswordName", OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD);
			String s = "";
			if (user.getProperties() != null)
				if (user.getProperties().containsKey(OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD))
					s = user.getProperties().get(OpenmrsConstants.USER_PROPERTY_CHANGE_PASSWORD);
			map.put("changePassword", new Boolean(s).booleanValue());
		}	
		return map;
    }
    
}