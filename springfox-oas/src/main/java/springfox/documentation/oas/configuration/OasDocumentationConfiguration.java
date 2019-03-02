/*
 *
 *  Copyright 2017-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.documentation.oas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerMapping;
import springfox.documentation.oas.mappers.ServiceModelToOasMapper;
import springfox.documentation.oas.web.OasController;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.WebMvcPropertySourcedRequestMappingHandlerMapping;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.JsonSerializer;

@Configuration
@Import({ SpringfoxWebMvcConfiguration.class })
@ComponentScan(basePackages = {
    "springfox.documentation.oas.web",
    "springfox.documentation.oas.mappers"
})
public class OasDocumentationConfiguration {
  @Bean
  public JacksonModuleRegistrar swagger2Module() {
    return new OpenApiJacksonModule();
  }

  @Bean
  public HandlerMapping swagger2ControllerMapping(
      Environment environment,
      DocumentationCache documentationCache,
      ServiceModelToOasMapper mapper,
      JsonSerializer jsonSerializer) {
    return new WebMvcPropertySourcedRequestMappingHandlerMapping(
        environment,
        new OasController(environment, documentationCache, mapper, jsonSerializer));
  }
}
