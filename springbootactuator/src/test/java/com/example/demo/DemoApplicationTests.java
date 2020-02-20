package com.example.demo;

import com.example.demo.pojo.AyMod;
import com.example.demo.pojo.AyUser;
import com.example.demo.service.AyMoodService;
import com.example.demo.service.AyUserService;
import com.example.demo.service.impl.AyMoodProducer;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private AyUserService ayUserService;

    @Resource
    private AyMoodProducer ayMoodProducer;

    @Resource
    private AyMoodService ayMoodService;

    @Resource
    private RedisTemplate redisTemplate;
    Logger logger =  LogManager.getLogger(this.getClass());

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final String ALL_USER = "ALL_USER_LIST";
    @Test
    void contextLoads() {
    }

    @Test
    public void TestRedis(){
        redisTemplate.opsForValue().set("name","ay");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);

        redisTemplate.delete(name);

        redisTemplate.opsForValue().set("name","ay1");

        name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Transactional
    @Test
    public void testFindById(){
        Long redisUserSize = 0L;
        AyUser ayUser = ayUserService.findById("3");
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("目前缓存中的用户数量为:"+redisUserSize);
        System.out.println("----->ID:"+ayUser.getId()+"name:"+ayUser.getName());

        AyUser ayUser1 = ayUserService.findById("4");
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("目前缓存中的用户数量为:"+redisUserSize);
        System.out.println("----->ID:"+ayUser1.getId()+"name:"+ayUser1.getName());

        AyUser ayUser2 = ayUserService.findById("5");
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("目前缓存中的用户数量为:"+redisUserSize);
        System.out.println("----->ID:"+ayUser2.getId()+"name:"+ayUser2.getName());

       /* AyUser ayUser3 = ayUserService.findById("1");
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("目前缓存中的用户数量为:"+redisUserSize);
        System.out.println("----->ID:"+ayUser3.getId()+"name:"+ayUser3.getName());*/

        AyUser ayUser4 = ayUserService.findById("7");
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("目前缓存中的用户数量为:"+redisUserSize);
        System.out.println("----->ID:"+ayUser4.getId()+"name:"+ayUser4.getName());
    }

    @Test
    public void testLog4j2(){
        ayUserService.delete("6");
        logger.info("删除成功");
    }

    @Test
    public void testMybatis(){
       AyUser ayUser = ayUserService.findByNameAndPassword("hhh","0000");
       logger.info(ayUser.getName()+ ayUser.getPassword());
       System.out.println("将集结就军军军军" +
               "咔咔咔咔咔咔扩扩扩扩"+ayUser.getName()+ ayUser.getPassword());
    }

    @Test
    public void testMood(){
        AyMod ayMod = new AyMod();
        ayMod.setId("1");
        ayMod.setContent("这是我的第一条微信说说!!!!");
        ayMod.setUserId("1");
        ayMod.setPublishTime(new Date());
        ayMod.setPraiseNum(0);
        AyMod ayMod1 = ayMoodService.save(ayMod);
    }

    @Test
    public void testQueue(){
        Destination destination = (Destination) new ActiveMQQueue("ay_queue");
        ayMoodProducer.sendMessage(destination,"hello mq!!!!!");
    }

    @Test
    public void testAsynMood(){
        AyMod ayMod = new AyMod();
        ayMod.setId("2");
        ayMod.setContent("这是我的第一条异步微信说说!!!!");
        ayMod.setUserId("1");
        ayMod.setPublishTime(new Date());
        ayMod.setPraiseNum(0);
        String msg = ayMoodService.asynSave(ayMod);
        System.out.println("异步发表说说:"+msg);
    }

    @Test
    public void testAsyn(){
        long start = System.currentTimeMillis();
        System.out.println("第一次查询所有用户");
        List<AyUser> list = ayUserService.findAll();
        System.out.println("第二次查询所有用户");
        List<AyUser> list1 = ayUserService.findAll();
        System.out.println("第三次查询所有用户");
        List<AyUser> list2 = ayUserService.findAll();
        long end = System.currentTimeMillis();
        System.out.println("完成任务，耗时"+(end-start)+"毫秒");
    }

    @Test
    public void testAsyn2() throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("第一次查询所有用户");
        Future<List<AyUser>> list = ayUserService.findAsynAll();
        System.out.println("第二次查询所有用户");
        Future<List<AyUser>> list1 = ayUserService.findAsynAll();
        System.out.println("第三次查询所有用户");
        Future<List<AyUser>> list2 = ayUserService.findAsynAll();
        while (true){
            if (list.isDone() && list1.isDone() && list2.isDone()){
                  break;
            }else {
                Thread.sleep(10);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("完成任务，耗时"+(end-start)+"毫秒");
    }
}
