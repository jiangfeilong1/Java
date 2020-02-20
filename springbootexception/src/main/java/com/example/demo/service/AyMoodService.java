package com.example.demo.service;

import com.example.demo.pojo.AyMod;

/**
 * @author Administrator
 */
public interface AyMoodService {
    AyMod save(AyMod ayMod);
    String asynSave(AyMod ayMod);
}
