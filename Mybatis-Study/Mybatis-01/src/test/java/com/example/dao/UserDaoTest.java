package com.example.dao;

import com.example.pojo.User;
import com.example.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class UserDaoTest {

    static Logger logger = Logger.getLogger(UserDaoTest.class);
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.queryUserById(1);
        System.out.println(user);

        //mapper.updateUser(new User(2, "aaa", "bbbb"));
        sqlSession.clearCache();
        System.out.println("===============================");
        User user2 = mapper.queryUserById(1);
        System.out.println(user == user2);

        sqlSession.close();
    }

    @Test
    public void testLog4j(){
        logger.info("info: 进入了testLog4j");
        logger.debug("debug: 进入了log4j");
        logger.error("error: 进入了testLog4j");
    }
}
