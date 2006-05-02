package org.openmrs.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOContext;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.PatientSetDAO;
import org.openmrs.reporting.PatientSet;
import org.openmrs.util.OpenmrsConstants;

public class PatientSetService {

	private Context context;
	private DAOContext daoContext;
	
	public PatientSetService(Context c, DAOContext d) {
		this.context = c;
		this.daoContext = d;
	}

	private PatientSetDAO getPatientSetDAO() {
		if (!context.hasPrivilege(OpenmrsConstants.PRIV_VIEW_PATIENT_SETS)) {
			throw new APIAuthenticationException("Privilege required: " + OpenmrsConstants.PRIV_VIEW_PATIENT_SETS);
		}
		return daoContext.getPatientSetDAO();
	}
	
	/**
	 * @param ps The set you want to export as XML
	 * @return an XML representation of this patient-set, including patient characteristics, and observations
	 */
	public String exportXml(PatientSet ps) {
		return getPatientSetDAO().exportXml(ps);
	}

	public String exportXml(Integer patientId) {
		return getPatientSetDAO().exportXml(patientId);
	}
	
	public PatientSet getPatientsByCharacteristics(String gender, Date minBirthdate, Date maxBirthdate) throws DAOException {
		return getPatientSetDAO().getPatientsByCharacteristics(gender, minBirthdate, maxBirthdate);
	}
	
	public PatientSet getPatientsHavingNumericObs(Concept concept, PatientSetService.Modifier modifier, Number value) {
		return getPatientsHavingNumericObs(concept.getConceptId(), modifier, value);
	}
	
	public PatientSet getPatientsHavingNumericObs(Integer conceptId, PatientSetService.Modifier modifier, Number value) {
		return getPatientSetDAO().getPatientsHavingNumericObs(conceptId, modifier, value);
	}
	
	public PatientSet getPatientsHavingTextObs(Concept concept, String value) {
		return getPatientsHavingTextObs(concept.getConceptId(), value);
	}
	
	public PatientSet getPatientsHavingTextObs(Integer conceptId, String value) {
		return getPatientSetDAO().getPatientsHavingTextObs(conceptId, value);
	}
	
	public Map<Integer, String> getShortPatientDescriptions(PatientSet patients) {
		return getPatientSetDAO().getShortPatientDescriptions(patients);
	}
	
	public Map<Integer, List<Obs>> getObservations(PatientSet patients, Concept concept) {
		return getPatientSetDAO().getObservations(patients, concept);
	}
	
	public Map<Integer, Map<String, Object>> getCharacteristics(PatientSet patients) {
		return getPatientSetDAO().getCharacteristics(patients);
	}
	
	public enum Modifier {
		EXISTS (""),
		LESS_THAN ("<"),
		LESS_EQUAL ("<="),
		EQUAL ("="),
		GREATER_EQUAL (">="),
		GREATER_THAN (">");

		public final String sqlRep;
		Modifier(String sqlRep) {
			this.sqlRep = sqlRep;
		}
		public String getSqlRepresentation() {
			return sqlRep;
		}
	}
	
}
