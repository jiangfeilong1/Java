package com.example.demo.repository;

import com.example.demo.pojo.AyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
public interface AyUserRepository extends JpaRepository<AyUser,String> {
    AyUser findByName(String name);
    List<AyUser> findByNameLike(String name);
    List<AyUser> findByIdIn(Collection<String> ids);
}
