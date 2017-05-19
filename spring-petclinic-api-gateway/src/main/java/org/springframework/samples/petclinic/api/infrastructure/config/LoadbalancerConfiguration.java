package org.springframework.samples.petclinic.api.infrastructure.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config for Ribbon which is implicitly used by Zuul
 * Created by mrombach on 08.05.2017.
 */

@Configuration
@RibbonClient(name = "*", configuration = LoadbalancerConfiguration.Config.class)
public class LoadbalancerConfiguration {

    static class Config {

        @Bean
        public IRule ribbonRule() {
//            return new RoundRobinRule();
            return new WeightedResponseTimeRule();
        }
    }
}
