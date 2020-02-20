package com.example.demo.mail.impl;

import com.example.demo.mail.SendJunkMailService;
import com.example.demo.pojo.AyUser;
import com.example.demo.quartz.SendMailQuartz;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Resource
    private AyUserService ayUserService;

    @Value("${spring.mail.username}")
    private String from;

    private static final Logger logger = (Logger) LogManager.getLogger(SendJunkMailServiceImpl.class);

    @Override
    public boolean sendJunkMail(List<AyUser> ayUsers) {
        try {
            if (ayUsers == null || ayUsers.size() <= 0) {
                return Boolean.FALSE;
            }
            for (AyUser user:ayUsers){
                MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject("地瓜今日特卖");
                mimeMessageHelper.setTo("jfl2962735074@163.com");
                mimeMessageHelper.setText(user.getName()+"地瓜今日特卖，一斤只要9元，一斤只要9元");
                this.javaMailSender.send(mimeMessage);
            }
        }catch (Exception ex){
            logger.error("error and",ayUsers,ex);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
