import com.example.dao.StudentMapper;
import com.example.dao.TeacherMapper;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:liangzhengtao.lzt@lazada.com">liangzhengtao.lzt</a>
 * @version 1.0
 * @date 2020/12/12 2:03 上午
 * @desc
 */
public class MyTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.getTeacher();
        for (Teacher teacher : teacherList) {
            System.out.println(teacher);
        }
        sqlSession.close();
    }

    @Test
    public void test() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeachers(1);
        System.out.println(teacher);

        sqlSession.close();
    }

    @Test
    public void tests() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeachers2(1);
        System.out.println(teacher);

        sqlSession.close();
    }
}
