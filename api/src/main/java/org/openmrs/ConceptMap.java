/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * The concept map object represents a mapping of Concept to ConceptSource. A concept can have 0 to
 * N mappings to any and all concept sources in the database.
 */
@Root
public class ConceptMap extends BaseConceptMap implements java.io.Serializable {
	
	public static final long serialVersionUID = 754677L;
	
	// Fields
	
	private Integer conceptMapId;
	
	private Concept concept;
	
	private ConceptReferenceTerm conceptReferenceTerm;
	
	// Constructors
	
	/** default constructor */
	public ConceptMap() {
	}
	
	/** constructor with concept map id */
	public ConceptMap(Integer conceptMapId) {
		this.conceptMapId = conceptMapId;
	}
	
	/**
	 * Convenience constructor that takes the term to be mapped to and the type of the map
	 * 
	 * @param conceptReferenceTerm the concept reference term to map to
	 * @param conceptMapType the concept map type for this concept reference term map
	 */
	public ConceptMap(ConceptReferenceTerm conceptReferenceTerm, ConceptMapType conceptMapType) {
		this.conceptReferenceTerm = conceptReferenceTerm;
		setConceptMapType(conceptMapType);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConceptMap) {
			ConceptMap c = (ConceptMap) obj;
			
			if (getConceptMapId() == null)
				return false;
			
			return (this.conceptMapId.equals(c.getConceptMapId()));
		}
		return false;
	}
	
	/**
	 * @see org.openmrs.BaseOpenmrsObject#toString()
	 */
	@Override
	public String toString() {
		if (conceptMapId == null)
			return "";
		return conceptMapId.toString();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.getConceptMapId() == null)
			return super.hashCode();
		return this.getConceptMapId().hashCode();
	}
	
	/**
	 * @return the concept
	 */
	@Element
	public Concept getConcept() {
		return concept;
	}
	
	/**
	 * @param concept the concept to set
	 */
	@Element
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	/**
	 * Comments on concept maps are no longer supported since version 1.9, therefore a call to this
	 * methods is useless
	 * 
	 * @return Returns the comment.
	 * @deprecated
	 */
	@Deprecated
	public String getComment() {
		if (getConceptReferenceTerm() == null)
			return null;
		return getConceptReferenceTerm().getDescription();
	}
	
	/**
	 * Comments on concept maps are no longer supported since version 1.9, therefore a call to this
	 * methods is useless
	 * 
	 * @param comment The comment to set.
	 * @deprecated
	 */
	@Deprecated
	public void setComment(String comment) {
		//do nothing, we don't want the description of the associated term to be set/edited from here,
		//it should done from the concept reference term form
	}
	
	/**
	 * @return Returns the conceptMapId.
	 */
	@Attribute
	public Integer getConceptMapId() {
		return conceptMapId;
	}
	
	/**
	 * @param conceptMapId The conceptMapId to set.
	 */
	@Attribute
	public void setConceptMapId(Integer conceptMapId) {
		this.conceptMapId = conceptMapId;
	}
	
	/**
	 * The conceptSource should be accessed from the associated ConceptReferenceTerm since version
	 * 1.9
	 * 
	 * @return Returns the source.
	 * @deprecated
	 * @see ConceptReferenceTerm#getConceptSource()
	 */
	@Deprecated
	public ConceptSource getSource() {
		if (getConceptReferenceTerm() == null)
			return null;
		return getConceptReferenceTerm().getConceptSource();
	}
	
	/**
	 * The conceptSource should be set on the associated ConceptReferenceTerm since version 1.9
	 * 
	 * @param source The source to set.
	 * @deprecated
	 * @see ConceptReferenceTerm#setConceptSource(ConceptSource)
	 */
	@Deprecated
	public void setSource(ConceptSource source) {
		if (getConceptReferenceTerm() != null)
			getConceptReferenceTerm().setConceptSource(source);
	}
	
	/**
	 * The sourceCode should be accessed from the associated ConceptReferenceTerm since version 1.9
	 * 
	 * @return Returns the sourceCode.
	 * @deprecated
	 * @see ConceptReferenceTerm#getCode()
	 */
	@Deprecated
	public String getSourceCode() {
		if (getConceptReferenceTerm() == null)
			return null;
		return getConceptReferenceTerm().getCode();
	}
	
	/**
	 * The sourceCode should be set on the associated ConceptReferenceTerm since version 1.9
	 * 
	 * @param sourceCode The sourceCode to set.
	 * @deprecated
	 * @see ConceptReferenceTerm#setCode(String)
	 */
	@Deprecated
	public void setSourceCode(String sourceCode) {
		if (getConceptReferenceTerm() != null)
			getConceptReferenceTerm().setCode(sourceCode);
	}
	
	/**
	 * @return the conceptReferenceTerm
	 * @since 1.9
	 */
	public ConceptReferenceTerm getConceptReferenceTerm() {
		return conceptReferenceTerm;
	}
	
	/**
	 * @param conceptReferenceTerm the conceptReferenceTerm to set
	 * @since 1.9
	 */
	public void setConceptReferenceTerm(ConceptReferenceTerm conceptReferenceTerm) {
		this.conceptReferenceTerm = conceptReferenceTerm;
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return getConceptMapId();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setConceptMapId(id);
	}
	
}
