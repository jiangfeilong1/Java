package com.example.demo.controller;

import com.example.demo.error.BusException;
import com.example.demo.pojo.AyUser;
import com.example.demo.service.AyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/ayUser")
public class AyUserController {
    @Resource
    private AyUserService ayUserService;

    @RequestMapping("/test")
    public String test(Model model){
        List<AyUser> ayUsers = ayUserService.findAll();
        model.addAttribute("users",ayUsers);
        return "ayUser";
    }

    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<AyUser> ayUsers = ayUserService.findAll();
        model.addAttribute("users",ayUsers);
        throw new BusException("业务异常");
    }

    @RequestMapping("/findByNameAndPasswordRetry")
    public String findByNameAndPasswordRetry(Model model){
        AyUser ayUsers = ayUserService.findByNameAndPasswordRetry("hhh","0000");
        model.addAttribute("users",ayUsers);
        return "ayUser";
    }
}
