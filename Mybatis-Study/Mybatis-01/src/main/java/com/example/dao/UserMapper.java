package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Select("select * from user")
    List<User> getUsers();

    //模糊查询
    List<User> getUserLike(String value);

    //分页
    List<User> getUserByLimit(Map<String, Integer> map);

    //分页RowBounds方式实现
    List<User> getUserByRowBounds();

    //查询全部用户
    List<User> getUserList();

    //根据id查询用户
    User getUserById(int id);

    User getUserById2(Map<String,Object> map);

    //insert一个用户
    int addUser(User user);

    //万能的Map
    int addUer2(Map<String,Object> map);

    //update一个用户
    int updateUser(User user);

    //delete一个用户
    int deleteUser(int id);
}
