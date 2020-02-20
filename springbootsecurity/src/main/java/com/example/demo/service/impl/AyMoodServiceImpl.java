package com.example.demo.service.impl;

import com.example.demo.pojo.AyMod;
import com.example.demo.repository.AyMoodRepository;
import com.example.demo.service.AyMoodService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @author Administrator
 */
@Service
public class AyMoodServiceImpl implements AyMoodService {
    @Resource
    private AyMoodRepository ayMoodRepository;

    @Resource
    private AyMoodProducer ayMoodProducer;

    private static Destination destination = new ActiveMQQueue("asyncSave");

    @Override
    public AyMod save(AyMod ayMod) {
        return ayMoodRepository.save(ayMod);
    }

    @Override
    public String asynSave(AyMod ayMod) {
        ayMoodProducer.sendMessage(destination,ayMod);
        return "success";
    }
}
