package com.example.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Administrator
 */
@WebListener
public class AyUserListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        System.out.println("ServletContext上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        System.out.println("ServletContext上下文销毁");
    }
}
