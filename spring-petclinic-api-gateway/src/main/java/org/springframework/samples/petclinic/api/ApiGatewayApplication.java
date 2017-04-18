package org.springframework.samples.petclinic.api;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kieker.monitoring.probe.spring.executions.OperationExecutionWebRequestRegistrationInterceptor;
import kieker.monitoring.probe.spring.flow.RestInInterceptor;
import kieker.monitoring.probe.spring.flow.RestOutInterceptor;
import kieker.monitoring.probe.spring.flow.ZuulPostInterceptor;
import kieker.monitoring.probe.spring.flow.ZuulPreInterceptor;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@ImportResource("classpath:/META-INF/aop.xml")
public class ApiGatewayApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate loadBalancedRestTemplate() {
    	RestTemplate result = new RestTemplate();
    	result.setInterceptors(Collections.singletonList(new RestOutInterceptor()));
        return result;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addWebRequestInterceptor(new OperationExecutionWebRequestRegistrationInterceptor()).addPathPatterns("/**");
    	registry.addInterceptor(new RestInInterceptor()).addPathPatterns("/**");
    }
	
	@Bean
	public ZuulPreInterceptor zuulPreInterceptor() {
		return new ZuulPreInterceptor();
	}
	
	@Bean
	public ZuulPostInterceptor zuulPostInterceptor() {
		return new ZuulPostInterceptor();
	}
}
