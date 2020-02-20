package com.example.demo;

import com.example.demo.pojo.AyUser;
import com.example.demo.service.AyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private AyUserService ayUserService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testRepository(){
        List<AyUser> list = ayUserService.findAll();
        System.out.println("findAll():"+list.size());

        List<AyUser> list1 = ayUserService.findByName("zz");
        System.out.println("findByName():"+list1.size());
        Assert.isTrue(list1.get(0).getName().equals("zz"),"data error");

        List<AyUser> list2 = ayUserService.findByNameLike("%z%");
        System.out.println("findByNameLike():"+list2.size());
        Assert.isTrue(list2.get(0).getName().equals("zz"),"data error");

        List<String> ids= new ArrayList<String>();
        ids.add ("1");
        ids.add ("2");
        List<AyUser> list3 = ayUserService.findByIdIn(ids);
        System.out.println("findByIdIn ():"+list3.size());

        PageRequest pageRequest = PageRequest.of(0,10);
        Page<AyUser> list4 = ayUserService.findAll(pageRequest);
        System. out.println("page findAll():"+list4.getTotalPages()+"/"+list4.getSize());

        AyUser ayUser = new AyUser();
        ayUser.setId("6");
        ayUser.setName("xx");
        ayUser.setPassword("123456789");
        ayUserService.save(ayUser);

        ayUserService.delete("1");

    }

    @Test
    public void save() {
        AyUser ayUser = new AyUser();
        ayUser.setId("8");
        ayUser.setName("hh");
        ayUser.setPassword("000000");
        ayUserService.save(ayUser);
    }
}
