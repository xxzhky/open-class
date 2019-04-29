package com.dt.open.test.service;

import com.dt.open.test.dao.IUserRepository;
import com.dt.open.test.pojo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public boolean updateUser(User user) {
        User u= userRepository.save(user);
        return u!=null?true:false;
    }

    @Override
    public User findOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean updateUsername(Long id, String username) {
        User user = findOne(id);
        if (user == null) {
            return false;
        }
        user.setName(username);
        //return userRepository.updateUser(user);
        return updateUser(user);
    }
}
