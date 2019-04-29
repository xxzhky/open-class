package com.dt.open.test.controller;

import com.dt.open.OpenClassApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenClassApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {

        String url= String.format("http://localhost:%d/", port);
        //System.out.println(String.format("port is [%d]", port));
        this.base= new URL(url);
    }

    @Test
    public void index() {

    }

    @Test
    public void test1() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                this.base.toString() + "/test", String.class, "");

        System.out.println(String.format("测试结果: %s", response.getBody()));
    }

    @Test
    public void testAddUser() {
        String result1 = restTemplate.getForObject("/user/add?name=pengjunlee", String.class);
        assertEquals("0", result1);
        String result2 = restTemplate.getForObject("/user/add?age=20&name=Tracy", String.class);
        assertEquals("1", result2);
    }
}