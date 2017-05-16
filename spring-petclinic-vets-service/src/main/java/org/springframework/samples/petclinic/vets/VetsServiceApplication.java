package org.springframework.samples.petclinic.vets;

import kieker.monitoring.probe.spring.executions.OperationExecutionWebRequestRegistrationInterceptor;
import kieker.monitoring.probe.spring.flow.RestInInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@ImportResource("classpath:/META-INF/aop.xml")
public class VetsServiceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(VetsServiceApplication.class, args);
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addWebRequestInterceptor(new OperationExecutionWebRequestRegistrationInterceptor());
    	registry.addInterceptor(new RestInInterceptor("_VetsService"));
    }
}
