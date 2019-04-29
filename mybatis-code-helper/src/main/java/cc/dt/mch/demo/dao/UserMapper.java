package cc.dt.mch.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import cc.dt.mch.demo.pojo.User;

@Mapper
public interface UserMapper {

    int insert(@Param("user") User user);

    int insertSelective(@Param("user") User user);

    int insertList(@Param("users") List<User> users);

    int updateByPrimaryKeySelective(@Param("user") User user);

    int deleteAll();

	int updateEducationBackground(@Param("updatedEducationBackground")String updatedEducationBackground);

	List<User> getAll();

	List<User> findByidNumber(@Param("idNumber")String idNumber);



    User findById(@Param("id")Integer id);

	List<User> findByAge(@Param("age")Integer age);

	List<User> findByAgeAndGender(@Param("age")Integer age,@Param("gender")String gender);

	List<User> findByNationLike(@Param("likeNation")String likeNation);



}
