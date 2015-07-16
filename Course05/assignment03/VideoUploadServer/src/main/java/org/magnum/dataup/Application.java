/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import javax.servlet.MultipartConfigElement;

import org.magnum.dataup.repository.VideoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Tell Spring to automatically inject any dependencies that are marked in
//our classes with @Autowired
@EnableAutoConfiguration
//Tell Spring to automatically create a JPA implementation of our
//VideoRepository
@EnableJpaRepositories(basePackageClasses = VideoRepository.class)
//Tell Spring that this object represents a Configuration for the
//application
@Configuration
//Tell Spring to turn on WebMVC (e.g., it should enable the DispatcherServlet
//so that requests can be routed to our Controllers)
@EnableWebMvc
//Tell Spring to go and scan our controller package (and all sub packages) to
//find any Controllers or other components that are part of our applciation.
//Any class in this package that is annotated with @Controller is going to be
//automatically discovered and connected to the DispatcherServlet.
@ComponentScan
public class Application {

	private static final String MAX_REQUEST_SIZE = "150MB";

	// The entry point to the application.
	public static void main(String[] args) {
		// This call tells spring to launch the application and
		// use the configuration specified in LocalApplication to
		// configure the application's components.
		SpringApplication.run(Application.class, args);
	}

	// This configuration element adds the ability to accept multipart
	// requests to the web container.
	@Bean
    public MultipartConfigElement multipartConfigElement() {
		// Setup the application container to be accept multipart requests
		final MultiPartConfigFactory factory = new MultiPartConfigFactory();
		// Place upper bounds on the size of the requests to ensure that
		// clients don't abuse the web container by sending huge requests
		factory.setMaxFileSize(MAX_REQUEST_SIZE);
		factory.setMaxRequestSize(MAX_REQUEST_SIZE);

		// Return the configuration to setup multipart in the container
		return factory.createMultipartConfig();
	}

}
