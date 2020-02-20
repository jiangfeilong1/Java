package com.example.demo.listener;

import com.example.demo.pojo.AyUser;
import com.example.demo.service.AyUserService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @author Administrator
 */
@WebListener
public class AyUserListener implements ServletContextListener {
    @Resource
    private AyUserService ayUserService;

    @Resource
    private RedisTemplate redisTemplate;
    private static final String ALL_USER = "ALL_USER_LIST";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        List<AyUser> list = ayUserService.findAll();
        redisTemplate.delete(ALL_USER);
        redisTemplate.opsForList().leftPushAll(ALL_USER,list);
        List<AyUser> list1 = redisTemplate.opsForList().range(ALL_USER,0,-1);
        System.out.println("缓存中目前的用户有:"+list1.size()+"人");
        System.out.println("ServletContext上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        System.out.println("ServletContext上下文销毁");
    }
}
