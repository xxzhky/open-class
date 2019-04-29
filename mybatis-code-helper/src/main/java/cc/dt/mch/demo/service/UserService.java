package cc.dt.mch.demo.service;

import java.util.List;
import cc.dt.mch.demo.pojo.User;
public interface UserService{

    int insert(User user);

    int insertSelective(User user);

    int insertList(List<User> users);

    int updateByPrimaryKeySelective(User user);
}
