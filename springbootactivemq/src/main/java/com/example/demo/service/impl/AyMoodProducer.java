package com.example.demo.service.impl;



import com.example.demo.pojo.AyMod;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;


/**
 * @author Administrator
 */
@Service
public class AyMoodProducer {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    public void sendMessage(Destination destination, final AyMod ayMod){
        jmsMessagingTemplate.convertAndSend(destination,ayMod);
    }
}
