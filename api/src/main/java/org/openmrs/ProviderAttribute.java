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

import org.openmrs.attribute.Attribute;
import org.openmrs.attribute.BaseAttribute;

/**
 * A value for a user-defined {@link ProviderAttributeType} that is stored on a {@link Provider}.
 * @see Attribute
 */
public class ProviderAttribute extends BaseAttribute<Provider> implements Attribute<Provider> {
	
	private Provider provider;
	
	private Integer providerAttributeId;
	
	public Integer getProviderAttributeId() {
		return providerAttributeId;
	}
	
	public void setProviderAttributeId(Integer providerAttributeId) {
		this.providerAttributeId = providerAttributeId;
	}
	
	public Provider getProvider() {
		return provider;
	}
	
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	@Override
	public Integer getId() {
		return null;
	}
	
	@Override
	public void setId(Integer id) {
	}
}
