package com.example.demo.service.impl;

import com.example.demo.dao.AyUserDao;
import com.example.demo.error.BusException;
import com.example.demo.pojo.AyUser;
import com.example.demo.repository.AyUserRepository;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Administrator
 */
@Service
public class AyUserServiceImpl implements AyUserService {

    @Resource
    private AyUserRepository ayUserRepository;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AyUserDao ayUserDao;

    private static final String ALL_USER = "ALL_USER_LIST";
    Logger logger =  LogManager.getLogger(this.getClass());

    @Override
    public AyUser findById(String id) {
        List<AyUser> list = redisTemplate.opsForList().range(ALL_USER,0,-1);
        if (list != null && list.size()>0){
            for (AyUser user:list){
                if (user.getId().equals(id)){
                    return user;
                }
            }
        }

        AyUser ayUser = ayUserRepository.getOne(id);
        if (ayUser != null){
            redisTemplate.opsForList().leftPush(ALL_USER,ayUser);
        }
        return ayUser;
    }

    @Override
    public List<AyUser> findAll() {
        try {
            System.out.println("开始了!!!!!");
            long start = System.currentTimeMillis();
            List<AyUser> list = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            System.out.println("完成任务，耗时"+(end-start)+"毫秒");
            return list;
        }catch (Exception ex){
            logger.error("error!!!!!",ex);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public AyUser save(AyUser ayUser) {
        return ayUserRepository.save(ayUser);
    }

    @Override
    public void delete(String id) {
       ayUserRepository.deleteById(id);
       logger.info("id:"+id+"用户被删除");
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public AyUser findByName(String name) {
        return ayUserRepository.findByName(name);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUser> findByIdIn(Collection<String> ids) {
        return ayUserRepository.findByIdIn(ids);
    }

    @Override
    public AyUser findByNameAndPassword(String name, String password) {
        return ayUserDao.findByNameAndPassword(name,password);
    }

    @Override
    @Async
    public Future<List<AyUser>> findAsynAll() {
        try {
            System.out.println("开始了!!!!!");
            long start = System.currentTimeMillis();
            List<AyUser> list = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            System.out.println("完成任务，耗时"+(end-start)+"毫秒");
            return new AsyncResult<List<AyUser>>(list);
        }catch (Exception ex){
            logger.error("error!!!!!",ex);
            return new AsyncResult<List<AyUser>>(null);
        }
    }

    @Override
    @Retryable(value = {BusException.class},maxAttempts = 5,backoff = @Backoff(delay = 5000,multiplier = 2))
    public AyUser findByNameAndPasswordRetry(String name, String password) {
        System.out.println("findByNameAndPasswordRetry方法失败重试了!!!!!");
        throw new BusException();
    }
}
