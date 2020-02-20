package com.example.demo.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * @author Administrator
 */
public class TestTask {
    private static final Logger logger = (Logger) LogManager.getLogger(TestTask.class);
    public void run(){
        logger.info("定时器运行了");
    }
}
