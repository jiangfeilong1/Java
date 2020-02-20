package com.example.demo.dao;

import com.example.demo.pojo.AyUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface AyUserDao {
    AyUser findByNameAndPassword(@Param("name") String name, @Param("password")String password);
}
