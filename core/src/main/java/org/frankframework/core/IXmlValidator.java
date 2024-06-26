/*
   Copyright 2020-2023 WeAreFrank!

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.frankframework.core;

import java.util.Set;

import org.frankframework.configuration.ConfigurationException;
import org.frankframework.validation.IXSD;

public interface IXmlValidator extends IValidator {

	ConfigurationException getConfigurationException();

	String getMessageRoot();

	/**
	 * @return noNamespaceSchemalocation, if specified
	 */
	String getSchema();
	String getSchemaLocation();
	Set<IXSD> getXsds() throws ConfigurationException;

	/**
	 * Provide additional generic documentation on the validation of the
	 * subsequent processing. This documentation will be included in generated
	 * schema's like WSDL or OpenApi
	 */
	String getDocumentation();
}
