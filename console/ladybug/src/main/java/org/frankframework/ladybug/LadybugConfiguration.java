/*
   Copyright 2024 WeAreFrank!

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
package org.frankframework.ladybug;

import nl.nn.testtool.web.ApiServlet;
import nl.nn.testtool.web.FrontendServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LadybugConfiguration {
	private static final Logger log = LoggerFactory.getLogger("ladybug");

	@Bean
	public ServletRegistrationBean<ApiServlet> ladybugApiServletBean() {
		ServletRegistrationBean<ApiServlet> registrationBean = new ServletRegistrationBean<>();
		ApiServlet servlet = new ApiServlet();
		registrationBean.setServlet(servlet);
		registrationBean.addUrlMappings("/iaf" + ApiServlet.getDefaultMapping());
		registrationBean.setInitParameters(ApiServlet.getDefaultInitParameters());
		registrationBean.setName("Ladybug-" + servlet.getClass().getSimpleName());
		registrationBean.setLoadOnStartup(0);

		log.info("created servlet {} endpoint {}", registrationBean.getServletName(), registrationBean.getUrlMappings());
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean<FrontendServlet> ladybugFrontendServletBean() {
		ServletRegistrationBean<FrontendServlet> registrationBean = new ServletRegistrationBean<>();
		FrontendServlet servlet = new FrontendServlet();
		registrationBean.setServlet(servlet);
		registrationBean.addUrlMappings("/iaf" + FrontendServlet.getDefaultMapping());
		registrationBean.setName("Ladybug-" + servlet.getClass().getSimpleName());
		registrationBean.setLoadOnStartup(0);

		log.info("created servlet {} endpoint {}", registrationBean.getServletName(), registrationBean.getUrlMappings());
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean<TesttoolServlet> testtoolServletBean() {
		ServletRegistrationBean<TesttoolServlet> registrationBean = new ServletRegistrationBean<>();
		TesttoolServlet servlet = new TesttoolServlet();
		registrationBean.setServlet(servlet);
		registrationBean.addUrlMappings("/iaf/testtool");
		registrationBean.setName("TestTool");
		registrationBean.setLoadOnStartup(0);

		log.info("created servlet {} endpoint {}", registrationBean.getServletName(), registrationBean.getUrlMappings());
		return registrationBean;
	}
}