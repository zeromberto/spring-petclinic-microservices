package org.springframework.samples.petclinic.api.infrastructure.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Configuration;

import kieker.monitoring.probe.spring.executions.OperationExecutionWebRequestRegistrationInterceptor;
import kieker.monitoring.probe.spring.flow.RestInInterceptor;
import kieker.monitoring.probe.spring.flow.RestOutInterceptor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Configuration
@RequiredArgsConstructor
public class ZuulHandlerBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

//    @Override
//    public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {
//
//        if (bean instanceof ZuulHandlerMapping) {
//            val zuulHandlerMapping = (ZuulHandlerMapping) bean;
//            zuulHandlerMapping.setInterceptors(new Object[] {
//            		new OperationExecutionWebRequestRegistrationInterceptor(),
//        			new RestInInterceptor()
//            });
//        }
//
//        return super.postProcessAfterInstantiation(bean, beanName);
//    }

}