package com.example.springbootvuedemo1.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvuedemo1.entity.SC;
import com.example.springbootvuedemo1.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService extends IService<Student> {
    List<Student> listAllStudent(int pageNum,int pageSize);

    List<Student> listOneStudent(Student student);

    List<Student> listStudentByCid(Integer cid);

    int delStudent(int id);

    int delStudentInSC(int sid,int cid);

    int addStudent(Student student);

    int addStudentInSC(SC sc);

    int modStudent(Student student);

    Map<String,Object> listReqStudent(int pageNum, int pageSize);

    int agreeStudent(SC sc);

    int disStudent(SC sc);


    //IPage pageC(IPage<Student> page);
}
