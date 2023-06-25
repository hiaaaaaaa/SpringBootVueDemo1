package com.example.springbootvuedemo1.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvuedemo1.entity.SC;
import com.example.springbootvuedemo1.entity.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    List<Student> listAllStudent(int pageNum,int pageSize);

    List<Student> listOneStudent(Student student);

    int delStudent(int id);

    int addStudent(Student student);

    int modStudent(Student student);

    List<Student> listReqStudent(int pageNum,int pageSize);


    int agreeStudent(SC sc);

    int disStudent(SC sc);


    //IPage pageC(IPage<Student> page);
}
