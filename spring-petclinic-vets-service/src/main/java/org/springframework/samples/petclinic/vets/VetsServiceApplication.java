package org.springframework.samples.petclinic.vets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.samples.petclinic.vets.infrastructure.config.VetsProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kieker.monitoring.probe.spring.executions.OperationExecutionWebRequestRegistrationInterceptor;
import kieker.monitoring.probe.spring.flow.RestInInterceptor;

@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@EnableConfigurationProperties(VetsProperties.class)
//@ImportResource("classpath:/META-INF/aop.xml")
public class VetsServiceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(VetsServiceApplication.class, args);
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addWebRequestInterceptor(new OperationExecutionWebRequestRegistrationInterceptor());
//    	registry.addInterceptor(new RestInInterceptor());
    }
}
