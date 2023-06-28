package com.example.springbootvuedemo1.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootvuedemo1.entity.Teacher;
import com.example.springbootvuedemo1.mapper.TeacherMapper;
import com.example.springbootvuedemo1.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> listAllTeacher(int pageNum, int pageSize) {
        //使用mybatis-plus的分页插件
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        return teacherMapper.listAll(page).getRecords();
    }

    @Override
    public List<Teacher> listOneTeacher(Teacher teacher) {
        List<Teacher> list = this.teacherMapper.listOne(teacher);
        return list;
    }

    @Override
    public int addTeacher(Teacher teacher) {
        int result = this.teacherMapper.addUpdate(teacher);
        return result;
    }

    @Override
    public int delTeacher(int id) {
        int result = this.teacherMapper.delById(id);
        return result;
    }

    @Override
    public int modTeacher(Teacher teacher) {
        int result = this.teacherMapper.modUpdate(teacher);
        return result;
    }

    /*
    * 作者：万梓欣
    * */

    public boolean loginCheck(String userName,String password){
        int i = teacherMapper.loginCheck(userName, password);
        return i>0;
    }

    //根据账号查询个人资料
    public Teacher selectByTeacherName(String userName){
        return teacherMapper.selectByTeacherName(userName);
    }

    //修改个人资料
    public void updateTeacher(String tname, String sex, Integer age, String email,String phoneNumber,String userName)
    {
        teacherMapper.updateTeacher(tname,sex,age,email,phoneNumber,userName);
    }

    //修改密码
    public void updatePassword(String password,String userName)
    {
        teacherMapper.updatePassword(password,userName);
    }

    //注册功能
    public void loginIn(String userName,String password,byte[] image){
        teacherMapper.loginIn(userName,password,image);
    }

    public Teacher getImagebaseService_Tea(String userName) {
        return teacherMapper.getImagebase_Tea(userName);
    }

    //修改头像
    public void updateImage_Tea(byte[] image,String userName){
        teacherMapper.updateImage_Tea(image,userName);
    }

}
