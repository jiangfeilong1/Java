package com.example.demo.service.impl;

import com.example.demo.error.BusException;
import com.example.demo.pojo.AyUser;
import com.example.demo.pojo.AyUserRoleRel;
import com.example.demo.service.AyRoleService;
import com.example.demo.service.AyUserRoleRelService;
import com.example.demo.service.AyUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Generated;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adminisatrator
 */
@Service
public class CustomUserService implements UserDetailsService {
    @Resource
    private AyUserService ayUserService;

    @Resource
    private AyRoleService ayRoleService;

    @Resource
    private AyUserRoleRelService ayUserRoleRelService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        AyUser ayUser =  ayUserService.findByName(name);
        if (ayUser == null){
            throw new BusException("用户不存在!!!!!");
        }
        List<AyUserRoleRel> list = ayUserRoleRelService.findByUserId(ayUser.getId());
        List<GrantedAuthority> list1 = new ArrayList<GrantedAuthority>();
        if (list != null && list.size() > 0){
            for (AyUserRoleRel rel:list){
                String roleName = ayRoleService.find(rel.getRoleId()).getName();
                list1.add(new SimpleGrantedAuthority(roleName));
            }
        }
        return new User(ayUser.getName(),ayUser.getPassword(),list1);
    }
}
