package com.example.demo;

import com.example.demo.pojo.AyUser;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private AyUserService ayUserService;

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
}
