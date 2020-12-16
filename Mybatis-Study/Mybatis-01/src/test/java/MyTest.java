import com.example.dao.BlogMapper;
import com.example.dao.StudentMapper;
import com.example.dao.TeacherMapper;
import com.example.dao.UserMapper;
import com.example.pojo.Blog;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.pojo.User;
import com.example.utils.IDUtils;
import com.example.utils.MybatisUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyTest {

    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.queryUserById(1);
        System.out.println(user);


        sqlSession.close();
    }
}
