package com.example.demo.quartz;

import com.example.demo.mail.SendJunkMailService;
import com.example.demo.pojo.AyUser;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Component
@Configuration
@EnableScheduling
public class SendMailQuartz {
    private static final Logger logger = (Logger) LogManager.getLogger(SendMailQuartz.class);
    @Resource
    private SendJunkMailService sendJunkMailService;
    @Resource
    private AyUserService ayUserService;

    @Scheduled(cron = "0/5 * * * * *")
    public void reportCurrentByCron(){
        List<AyUser> list = ayUserService.findAll();
        if (list != null || list.size() <= 0) {
            return;
        }
        sendJunkMailService.sendJunkMail(list);
        logger.info("定时器运行了");
    }
}
