package org.openmrs.web.dwr;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.ConceptWord;

import uk.ltd.getahead.dwr.util.JavascriptUtil;

public class ConceptListItem {
	
	protected final Log log = LogFactory.getLog(getClass());

	private Integer conceptId;
	private String name;
	private String shortName;
	private String description;
	private String synonym; 
	private Boolean retired;
	private String hl7Abbreviation;
	private String className;

	public ConceptListItem() { }
	
	public ConceptListItem(ConceptWord word) {
		if (word != null) {
			
			Concept concept = word.getConcept();
			Locale locale = new Locale(word.getLocale());
			initialize(concept, locale);
			synonym = word.getSynonym();
		}
	}
	
	public ConceptListItem(Concept concept, Locale locale){
		initialize(concept, locale);
	}
	
	private void initialize(Concept concept, Locale locale) {
		if (concept != null) {
			conceptId = concept.getConceptId();
			ConceptName cn = concept.getName(locale);
			name = shortName = description = "";
			if (cn != null) {
				name = escape(cn.getName());
				shortName = escape(cn.getShortName());
				description = escape(cn.getDescription());
			}
			synonym = "";
			retired = concept.isRetired();
			hl7Abbreviation = concept.getDatatype().getHl7Abbreviation();
			className = concept.getConceptClass().getName();
		}
	}
	
	private String escape(String input) {
		JavascriptUtil util = new JavascriptUtil();
		
		input = util.escapeJavaScript(input);
		
		input = input.replace("<", "&lt;");
		input = input.replace(">", "&gt;");
		
		return input;
	}
	
	public Integer getConceptId() {
		return conceptId;
	}

	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}

	public String getHl7Abbreviation() {
		return hl7Abbreviation;
	}

	public void setHl7Abbreviation(String hl7Abbreviation) {
		this.hl7Abbreviation = hl7Abbreviation;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}