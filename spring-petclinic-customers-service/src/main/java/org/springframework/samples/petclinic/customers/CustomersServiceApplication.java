package org.springframework.samples.petclinic.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.samples.petclinic.monitoring.MonitoringConfig;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kieker.monitoring.probe.spring.executions.OperationExecutionWebRequestRegistrationInterceptor;
import kieker.monitoring.probe.spring.flow.RestInInterceptor;

@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@ImportResource("classpath:/META-INF/aop.xml")
//@Import(MonitoringConfig.class)
public class CustomersServiceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(CustomersServiceApplication.class, args);
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addWebRequestInterceptor(new OperationExecutionWebRequestRegistrationInterceptor());
    	registry.addInterceptor(new RestInInterceptor());
    }
}
