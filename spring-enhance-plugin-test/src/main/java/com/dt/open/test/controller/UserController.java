package com.dt.open.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/home")
    public String homeUser(@RequestParam(name = "name", required = true) String userName) {
        if (null == userName || userName.trim() == "") {
            return "you are nobody...";
        }
        return "This is " + userName + "'s home...";
    }

    @GetMapping("/add")
    public String addUser(@RequestParam(name = "age", required = false, defaultValue = "0") Integer userAge,
                          @RequestParam(name = "name", required = true) String userName) {
        if (userAge <= 0 || null == userName || userName.trim() == "") {
            return "0";
        }
        //userDao.addUser(userAge, userName);
        return "1";
    }

}
