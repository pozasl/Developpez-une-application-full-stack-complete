package com.openclassrooms.mdd.subscribtions_api.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.mdd.common.configuration.RsaKeyProperties;

/**
 * Extra Security Config 
 */
@Configuration
@ComponentScan({"com.openclassrooms.mdd.common.configuration", "com.openclassrooms.mdd.common.exception"})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ExtraSecurityConfig {

}
