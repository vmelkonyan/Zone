package com.codulate.zone.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.activemq")
public record ActiveMqProperties (String brokerUrl, String user, String password){
}
