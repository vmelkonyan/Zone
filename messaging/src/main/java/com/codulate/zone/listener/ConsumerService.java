package com.codulate.zone.listener;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.service.PointValidationServiceImpl;
import com.codulate.zone.service.ZoneServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerService {
    private final PointValidationServiceImpl pointValidationService;
    private final ZoneServiceImpl zoneService;

    @JmsListener(destination = "userPointTopic")
    @SendTo("userZone")
    public String receiveAndForwardMessageFromTopic(final Message jsonMessage) throws JMSException {
        String messageData;
        if (jsonMessage instanceof TextMessage textMessage) {
            messageData = textMessage.getText();
        } else {
            messageData = new String(((ActiveMQBytesMessage) jsonMessage).getContent().getData(), StandardCharsets.UTF_8);
        }
        ObjectMapper mapper = new ObjectMapper();
        CoordinateDto coordinateDto;
        try {
            coordinateDto = mapper.readValue(messageData, CoordinateDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (coordinateDto == null) {
            return "";
        }
        for (ZoneDto zoneDto : zoneService.getAllZone()) {
            if (pointValidationService.validate(zoneDto, coordinateDto)) {
                String name = zoneDto.getName();
                String zoneMessage = String.format("Zone %s violated", name);
                return zoneMessage;
            }
        }
        return "";
    }

    @JmsListener(destination = "userZone")
    public void receiveMessageFromTopic(final Message jsonMessage) throws JMSException {

        String messageData;
        log.info("zoneMessage:{}", "Received message in 2nd topic " + jsonMessage);
        if (jsonMessage instanceof TextMessage textMessage) {
            messageData = textMessage.getText();
        } else {
            messageData = new String(((ActiveMQBytesMessage) jsonMessage).getContent().getData(), StandardCharsets.UTF_8);
        }
        log.info("zoneMessage:{}" , messageData);

    }
}
