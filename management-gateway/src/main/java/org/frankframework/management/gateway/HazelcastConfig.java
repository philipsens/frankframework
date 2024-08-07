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
package org.frankframework.management.gateway;

import java.util.Properties;

import org.frankframework.management.bus.BusMessageUtils;
import org.frankframework.util.PropertyLoader;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.impl.DefaultNodeContext;
import com.hazelcast.instance.impl.HazelcastInstanceFactory;

public class HazelcastConfig {

	public static final String REQUEST_TOPIC_NAME = "frank_integration_request_topic";
	public static final String AUTHENTICATION_HEADER_KEY = BusMessageUtils.HEADER_PREFIX+"user";

	static Config createHazelcastConfig() {
		System.setProperty("hazelcast.config.schema.validation.enabled", "false");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String resource = "frankframework-hazelcast.xml";
		Properties properties = new PropertyLoader("hazelcast.properties");
		return Config.loadFromClasspath(classLoader, resource, properties);
	}

	static HazelcastInstance newHazelcastInstance(String name) {
		Config config = HazelcastConfig.createHazelcastConfig();
		config.getMemberAttributeConfig().setAttribute("name", name);
		return HazelcastInstanceFactory.newHazelcastInstance(config, name, new DefaultNodeContext());
	}
}
