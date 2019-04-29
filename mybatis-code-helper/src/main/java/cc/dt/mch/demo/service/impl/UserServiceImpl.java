package cc.dt.mch.demo.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import cc.dt.mch.demo.pojo.User;
import cc.dt.mch.demo.dao.UserMapper;
import cc.dt.mch.demo.service.UserService;

@Service
@Qualifier("usi")
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user){
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user){
        return userMapper.insertSelective(user);
    }

    @Override
    public int insertList(List<User> users){
        return userMapper.insertList(users);
    }

    @Override
    public int updateByPrimaryKeySelective(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
