package com.dt.open.hotloaded.plugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorTest {

    @Autowired
    ApplicationContext  applicationContext;

    @Test
    public void div() {
        Calculator bean = applicationContext.getBean(Calculator.class);
        bean.div(4,0);
        assertNotNull(bean);
    }

    @Test/*(expected = AopInvocationException.class)*/
    public void divWithEx() {
        Calculator bean = applicationContext.getBean(Calculator.class);
        try {
            //fail("Expected an ArithmeticException to be thrown");
            bean.div(4,0);
        } catch (AopInvocationException ex) {
            assertThat(ex.getMessage(),
                    is("Null return value from advice does not match primitive return type for: public int com.dt.open.hotloaded.plugin.Calculator.div(int,int)"));
        }

        assertNotNull(bean);
    }
}