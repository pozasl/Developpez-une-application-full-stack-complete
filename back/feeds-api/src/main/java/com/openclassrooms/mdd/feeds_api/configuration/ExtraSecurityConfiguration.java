package com.openclassrooms.mdd.feeds_api.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.mdd.common.configuration.RsaKeyProperties;

@Configuration
@EnableConfigurationProperties(RsaKeyProperties.class)
@ComponentScan({"com.openclassrooms.mdd.common.configuration", "com.openclassrooms.mdd.common.exception"})
public class ExtraSecurityConfiguration {
    
}
