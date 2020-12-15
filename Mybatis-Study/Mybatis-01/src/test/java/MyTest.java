import com.example.dao.BlogMapper;
import com.example.dao.StudentMapper;
import com.example.dao.TeacherMapper;
import com.example.pojo.Blog;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.utils.IDUtils;
import com.example.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyTest {
    public static void main(String[] args) {
        SqlSession session = MybatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(IDUtils.getId());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("小白");
        blog.setCreateTime(new Date());
        blog.setViews(9999);

        mapper.addBlog(blog);

        blog.setId(IDUtils.getId());
        blog.setTitle("Java如此简单");
        mapper.addBlog(blog);
        blog.setId(IDUtils.getId());
        blog.setTitle("Spring如此简单");
        mapper.addBlog(blog);
        blog.setId(IDUtils.getId());
        blog.setTitle("微服务如此简单");
        mapper.addBlog(blog);

        session.close();
    }

    @Test
    public void queryBlogUpdateBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        map.put("title", "Mybatis如此简单2");
//        map.put("author", "小白");
//        map.put("views", 9999);
        map.put("id", "8b74dfdfc9e44d6093f60a7a0cfd2935");
        mapper.updateBlog(map);
    }

    @Test
    public void queryBlogIF(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        map.put("title", "Mybatis如此简单2");
//        map.put("author", "小白");
//        map.put("views", 9999);
//        map.put("id", "8b74dfdfc9e44d6093f60a7a0cfd2935");
        List<Blog> blogs = mapper.queryBlogIF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void queryForEach(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("177e66fd930a484cbf87dadbea38be63");
        map.put("ids", ids);
        List<Blog> blogs = mapper.queryBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }
}
