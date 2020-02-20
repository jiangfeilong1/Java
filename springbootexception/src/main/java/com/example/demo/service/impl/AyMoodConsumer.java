package com.example.demo.service.impl;

import com.example.demo.pojo.AyMod;
import com.example.demo.service.AyMoodService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class AyMoodConsumer {
    @Resource
    private AyMoodService ayMoodService;

    @JmsListener(destination = "ay.queue")
    public void receiveQueue(String text){
        System.out.println("用户发表说说"+text+"成功!!!!!!");
    }

    @JmsListener(destination = "asyncSave")
    public void receiveQueue(AyMod ayMod){
        ayMoodService.asynSave(ayMod);
    }
}
