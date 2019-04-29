package com.dt.open.test.dao;

import com.dt.open.OpenClassApplication;
import com.dt.open.test.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenClassApplication.class)
public class UserRepositoryTest {
    @Autowired
    private IUserRepository userRepository;

    @Test
    public void test() {
        userRepository.deleteAll();
        userRepository.save(new User("张三",12));
        userRepository.save(new User("李四",24));
        userRepository.save(new User("王五",36));
        userRepository.save(new User("王麻子",48));
        userRepository.save(new User("狗不理",56));
        System.out.println("执行成功！");

        assertEquals(5, userRepository.findAll().size());
        List<User> userList = userRepository.findAll();
        for (User user : userList){
            System.out.println(user.getName()+":"+user.getAge());
        }

        // 测试findByName, 查询姓名为张三的User.Age=12
        assertEquals(12, userRepository.findByName("张三").getAge().longValue());

        // 测试findUser, 查询姓名为王五的User.Age=36
        assertEquals(36, userRepository.findUser("王五").getAge().longValue());

        // 测试findByNameAndAge, 查询姓名为王麻子并且年龄为48的User
        assertEquals("王麻子", userRepository.findByNameAndAge("王麻子", 48).getName());

        // 测试删除姓名为狗不理的User
        userRepository.delete(userRepository.findByName("狗不理"));

        // 测试findAll, 删除后为4条记录
        assertEquals(4, userRepository.findAll().size());
    }
}