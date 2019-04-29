package com.dt.open.test.dao;

import com.dt.open.test.pojo.User;
import com.dt.open.test.service.IUserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//使用真是数据库
@Rollback(true)
public class UserDaoTest {

    @Autowired
    private IUserRepository repository;

    @Test
    public void testSave() {
        User u= new User("Tom",90);

        assertThat(u.getName(), Matchers.is(repository.save(u).getName()));;
    }
}
