package com.example.demo.mail;

import com.example.demo.pojo.AyUser;

import java.util.List;

/**
 * @author Administrator
 */
public interface SendJunkMailService {
    boolean sendJunkMail(List<AyUser> ayUsers);
}
