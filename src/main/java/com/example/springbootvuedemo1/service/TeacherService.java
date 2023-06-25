package com.example.springbootvuedemo1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvuedemo1.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService extends IService<Teacher> {

    List<Teacher> listAllTeacher(int pageNum, int pageSize);

    List<Teacher> listOneTeacher(Teacher teacher);

    int addTeacher(Teacher teacher);

    int delTeacher(int id);

    int modTeacher(Teacher teacher);

}
