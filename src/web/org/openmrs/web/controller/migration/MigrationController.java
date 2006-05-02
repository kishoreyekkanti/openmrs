package org.openmrs.web.controller.migration;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.EncounterService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.migration.MigrationHelper;
import org.openmrs.web.WebConstants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class MigrationController implements Controller {

	protected final Log log = LogFactory.getLog(getClass());
	
	public ModelAndView handleRequest(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {

		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		
		if (context == null) {
			httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "auth.session.expired");
			response.sendRedirect(request.getContextPath() + "/logout");
			return null;
		}
		
		String message = request.getParameter("message");
		if (message == null || message.length() == 0) {
			message = "Paste some xml";
		}
		
		EncounterService es = context.getEncounterService();
		List<Location> locations = es.getLocations();
		UserService us = context.getUserService();
		List<User> users = us.getUsers();
		
		Map myModel = new HashMap();
		myModel.put("message", message);
		myModel.put("locations", locations);
		myModel.put("users", users);
		
		return new ModelAndView("/migration/migration", "model", myModel);
	}
	
	public ModelAndView uploadUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserConfigurationException, ParseException {
		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		if (context == null) {
			httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "auth.session.expired");
			response.sendRedirect(request.getContextPath() + "/logout");
			return null;
		}
		String xml = request.getParameter("user_xml");
		log.debug("xml to upload = " + xml);
		int numAdded = MigrationHelper.importUsers(context, MigrationHelper.parseXml(xml));
		return new ModelAndView(new RedirectView("migration.form?message=" + URLEncoder.encode("Added " + numAdded + " users", "UTF-8")));
	}
	
	public ModelAndView uploadLocations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserConfigurationException {
		HttpSession httpSession = request.getSession();
		Context context = (Context) httpSession.getAttribute(WebConstants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		if (context == null) {
			httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "auth.session.expired");
			response.sendRedirect(request.getContextPath() + "/logout");
			return null;
		}
		String xml = request.getParameter("location_xml");
		log.debug("xml to upload = " + xml);
		int numAdded = MigrationHelper.importLocations(context, MigrationHelper.parseXml(xml));	
		return new ModelAndView(new RedirectView("migration.form?message=" + URLEncoder.encode("Uploaded " + numAdded + " locations", "UTF-8")));
	}

}
