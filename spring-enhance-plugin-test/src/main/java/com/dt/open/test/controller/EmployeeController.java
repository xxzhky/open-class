package com.dt.open.test.controller;

import com.dt.open.test.pojo.User;
import com.dt.open.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/emp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmployeeController {

    private final IUserService userService;

    @Autowired
    public EmployeeController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
