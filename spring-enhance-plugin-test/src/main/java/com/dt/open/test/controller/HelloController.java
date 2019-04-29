package com.dt.open.test.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "Hello Spring Boot,Index!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Spring Boot Test Demo!";
    }

}
