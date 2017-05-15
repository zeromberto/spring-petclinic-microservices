package org.springframework.samples.petclinic.api.infrastructure.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Created by mrombach on 08.05.2017.
 */
public class LoadbalancerConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

//    @Bean
//    public IPing ribbonPing(IClientConfig config) {
//        return new PingUrl(false,"/owners");
//    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new RoundRobinRule();
//        return new WeightedResponseTimeRule();
    }
}
