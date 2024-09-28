package com.openclassrooms.mdd.posts_api.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.mdd.common.configuration.RsaKeyProperties;

@Configuration
@EnableConfigurationProperties(RsaKeyProperties.class)
@ComponentScan("com.openclassrooms.mdd.common.configuration")
public class ExtraSecurityConfiguration {
    
}
