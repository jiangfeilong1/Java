package com.example.demo.service.impl;

import com.example.demo.dao.AyUserDao;
import com.example.demo.pojo.AyUser;
import com.example.demo.repository.AyUserRepository;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class AyUserServiceImpl implements AyUserService {

    @Resource(name = "ayUserRepository")
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
        return ayUserRepository.findAll();
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
    public List<AyUser> findByName(String name) {
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
}
