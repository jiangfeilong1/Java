package com.example.demo.service;

import com.example.demo.pojo.AyUserRoleRel;

import java.util.List;

/**
 * @author Administrator
 */
public interface AyUserRoleRelService {
    List<AyUserRoleRel> findByUserId(String userId);
}
