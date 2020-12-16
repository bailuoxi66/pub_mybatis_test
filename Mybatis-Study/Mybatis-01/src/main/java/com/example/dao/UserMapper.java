package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    //根据id查询用户
    User queryUserById(@Param("id") int id);

    int updateUser(User user);
}
