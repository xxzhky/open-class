package com.dt.open.test.service;

import com.dt.open.test.pojo.User;

import java.util.List;

public interface IUserService {
    boolean updateUser(User user);
    User findOne(Long id);
    List<User> findAll();
    boolean updateUsername(Long id, String username);
}
