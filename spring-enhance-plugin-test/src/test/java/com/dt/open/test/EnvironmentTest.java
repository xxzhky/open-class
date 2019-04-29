package com.dt.open.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(properties ={ "app.token=pengjunlee" })
public class EnvironmentTest {

    @Autowired
    Environment env;

    @Autowired
    ConfigurableEnvironment configurableEnvironment;

    @Before
    public void setUp(){
        TestPropertyValues.of("app.secret:55a4b77eda").applyTo(configurableEnvironment);
    }

    @Test
    public void testEnv(){
        assertEquals("pengjunlee", env.getProperty("app.token"));
        assertEquals("55a4b77eda", env.getProperty("app.secret"));
    }

}
