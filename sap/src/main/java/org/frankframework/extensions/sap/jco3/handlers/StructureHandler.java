/*
   Copyright 2013 Nationale-Nederlanden

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
package org.frankframework.extensions.sap.jco3.handlers;

import com.sap.conn.jco.JCoStructure;

/**
 * Handler for structure xml elements like:
 * <pre>
 * &lt;I_HCDAK&gt;
 *   &lt;TIMESTAMP&gt;20120419152332000003&lt;/TIMESTAMP&gt;
 *   &lt;SENDDATE&gt;2012-04-19&lt;/SENDDATE&gt;
 *   &lt;ACCOUNTID&gt;4403106&lt;/ACCOUNTID&gt;
 *   TABLE|STRUCTURE
 * &lt;/I_HCDAK&gt;
 * </pre>
 * @author  Jaco de Groot
 * @since   5.0
 */
public class StructureHandler extends RecordHandler {

	public StructureHandler(JCoStructure structure) {
		super(structure);
	}

}
