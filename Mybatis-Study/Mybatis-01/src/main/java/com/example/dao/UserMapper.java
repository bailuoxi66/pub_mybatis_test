package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Select("select * from user")
    List<User> getUsers();

    //方法存在多个参数，基本类型的参数必须加上@Param("id")注解
    @Select("select * from user where id = #{id}")
    User getUserByID(@Param("id") int id);

    @Insert("insert into user(id, name, pwd) values (#{id}, #{name}, #{passwd})")
    int addUser(User user);

    @Update("update user set name=#{name}, pwd = #{passwd} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);
}
