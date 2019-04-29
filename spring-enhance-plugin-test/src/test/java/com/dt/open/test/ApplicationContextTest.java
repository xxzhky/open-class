package com.dt.open.test;

import com.dt.open.OpenClassApplication;
import com.dt.open.test.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OpenClassApplication.class, TestConfig.class})
public class ApplicationContextTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testCfg(){
        Runnable bean =applicationContext.getBean(Runnable.class);
        assertNotNull(bean);
        bean.run();
    }
}
