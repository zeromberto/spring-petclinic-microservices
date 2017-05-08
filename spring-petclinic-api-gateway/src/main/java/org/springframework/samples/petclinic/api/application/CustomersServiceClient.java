package org.springframework.samples.petclinic.api.application;

import kieker.monitoring.probe.spring.flow.RestOutInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.api.infrastructure.config.LoadbalancerConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author Maciej Szarlinski
 */
@Component
@RibbonClient(name = "customers-service", configuration = LoadbalancerConfiguration.class)
@Configuration
public class CustomersServiceClient {

    @Bean
    @LoadBalanced
    RestTemplate loadBalancedRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestOutInterceptor()));
        return restTemplate;
    }

    public OwnerDetails getOwner(final int ownerId) {
        return loadBalancedRestTemplate().getForObject("http://customers-service/owners/{ownerId}", OwnerDetails.class, ownerId);
    }
}
