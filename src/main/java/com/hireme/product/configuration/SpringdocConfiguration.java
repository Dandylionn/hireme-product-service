package com.hireme.product.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

@Configuration
public class SpringdocConfiguration {

    @Bean
    public StandardReflectionParameterNameDiscoverer standardReflectionParameterNameDiscoverer() {
        return new StandardReflectionParameterNameDiscoverer();
    }
}
