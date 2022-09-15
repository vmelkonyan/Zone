package com.codulate.zone;

import com.codulate.zone.properties.ActiveMqProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

@ConditionalOnProperty(value = "activemq.enabled")
@RequiredArgsConstructor
@Configuration
@EnableJms
public class ActiveMqConfig {
    private final ActiveMqProperties activeMqProperties;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL(activeMqProperties.brokerUrl());
        connectionFactory.setPassword(activeMqProperties.password());
        connectionFactory.setUserName(activeMqProperties.user());
        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setMessageConverter(messageConverter());
        template.setPubSubDomain(true);
        template.setDestinationResolver(destinationResolver());
        template.setDeliveryPersistent(true);
        return template;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicConnectionFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    DynamicDestinationResolver destinationResolver() {
        return new DynamicDestinationResolver() {
            @Override
            public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
                if (destinationName.endsWith("Topic")) {
                    pubSubDomain = true;
                } else {
                    pubSubDomain = false;
                }
                return super.resolveDestinationName(session, destinationName, pubSubDomain);
            }
        };
    }
}
