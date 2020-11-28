package com.example.dao;

import com.example.pojo.User;
import com.example.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        List<User> userList =  userDao.getUserList();

        for (User user : userList) {
            System.out.println(user);
        }

        //关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void getUserById(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);

        //关闭SqlSession
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void addUser(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.addUser(new User(6, "小李", "11443"));
        if(res > 0){
            System.out.println("插入成功!");
        }
        //提交事务
        sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void updateUser(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.updateUser(new User(4, "呵呵", "111223"));
        if(res > 0){
            System.out.println("update success!");
        }
        //提交事务
        sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void deleteUser(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.deleteUser(4);
        if(res > 0){
            System.out.println("delete success!");
        }
        //提交事务
        sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }
}
