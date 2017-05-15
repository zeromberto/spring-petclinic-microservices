package org.springframework.samples.petclinic.api;

import kieker.monitoring.probe.spring.executions.OperationExecutionWebRequestRegistrationInterceptor;
import kieker.monitoring.probe.spring.flow.RestInInterceptor;
import kieker.monitoring.probe.spring.flow.RestOutInterceptor;
import kieker.monitoring.probe.spring.flow.ZuulPostInterceptor;
import kieker.monitoring.probe.spring.flow.ZuulPreInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.cache.StandardCacheManager;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;

@EnableZuulProxy
@EnableDiscoveryClient
@Configuration
@EnableWebMvc
@SpringBootApplication
@ImportResource("classpath:/META-INF/aop.xml")
public class ApiGatewayApplication extends WebMvcConfigurerAdapter {
	
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
    	RestTemplate result = new RestTemplate();
//    	result.setInterceptors(Collections.singletonList(new RestOutInterceptor()));
        return result;
    }
    
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addWebRequestInterceptor(new OperationExecutionWebRequestRegistrationInterceptor()).addPathPatterns("/api/**");
//    	registry.addInterceptor(new RestInInterceptor()).addPathPatterns("api/**");
//    }
	
	@Bean
	public ZuulPreInterceptor zuulPreInterceptor() {
		return new ZuulPreInterceptor();
	}
	
	@Bean
	public ZuulPostInterceptor zuulPostInterceptor() {
		return new ZuulPostInterceptor();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(60);
		registry.setOrder(1);
	}
	
    @Bean
    public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCache(true);
		return resolver;
    }
    
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		StandardCacheManager manager = (StandardCacheManager) engine.getCacheManager();
		manager.setExpressionCacheMaxSize(-1);
		manager.setTemplateCacheMaxSize(-1);
		engine.setTemplateResolver(templateResolver());
		return engine;
	}
	
	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setTemplateMode("HTML5");
		resolver.setSuffix(".html");
		resolver.setOrder(0);
		return resolver;
	}
}
