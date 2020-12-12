package com.example.dao;


import com.example.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    //获取老师
    List<Teacher> getTeacher();

    //获取指定老师下的所有学生及老师的信息
    Teacher getTeachers(@Param("tid") int id);

    Teacher getTeachers2(@Param("tid") int id);
}
