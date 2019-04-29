package cc.dt.mch.demo.service.impl;

import cc.dt.mch.demo.dao.UserMapper;
import cc.dt.mch.demo.pojo.User;
import cc.dt.mch.demo.service.UserService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//使用真是数据库
@ActiveProfiles(profiles = "dev")
@SpringBootTest
@Rollback(false)//not rollback,but the default is rollback.
public class UserServiceImplTest {

    @Qualifier("usi")
    @Resource
    UserService u;

    @Resource
    private UserMapper userMapper;

    @Test
    public void insertList() {
        userMapper.deleteAll();

        List<User> users= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user= User.of().setAge(18+i).setNation("han").setBirth(new Date());
            users.add(user);
            System.out.println(JSON.toJSON(user));
        }



        u.insertList(users);
    }

    @Test
    public void testInsert(){

        u.insert(User.of().setAge(25)) ;
    }
}