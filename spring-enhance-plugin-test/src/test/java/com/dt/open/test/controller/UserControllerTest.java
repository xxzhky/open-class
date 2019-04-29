package com.dt.open.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void homeUser() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/user/home").param("name", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("you are nobody..."));
        mvc.perform(MockMvcRequestBuilders.get("/user/home").param("name", "pengjunlee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This is pengjunlee's home..."));
    }
}