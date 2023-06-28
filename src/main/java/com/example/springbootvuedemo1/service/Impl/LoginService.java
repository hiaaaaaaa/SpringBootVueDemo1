package com.example.springbootvuedemo1.service.Impl;

import com.example.springbootvuedemo1.entity.Manager;
import com.example.springbootvuedemo1.entity.Student;
import com.example.springbootvuedemo1.entity.Teacher;
import com.example.springbootvuedemo1.mapper.ManagerMapper;
import com.example.springbootvuedemo1.mapper.StudentMapper;
import com.example.springbootvuedemo1.mapper.TeacherMapper;
import com.example.springbootvuedemo1.util.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private CacheService cacheService;
    //检查账号、密码是否正确
    public Map check(String userName, String pwd, String role){
        Map<String,Object> rs = new HashMap<>();
        if("teacher".equals(role)){
            // 1.根据教师工号查询教师信息
            Teacher t = teacherMapper.selectByTeacherName(userName);
            // 2.如果教师信息查询到了，则对比密码
            if(t!=null && t.getPassword().equals(pwd)){
                cacheService.add("user",t);
                rs.put("msg","教师登录成功！");
                rs.put("user",t.getUserName());
                t.setImage(null);
                t.setPassword(null);
                rs.put("teacher",t);
            }else {
                rs.put("msg", "教师登录失败！");
                rs.put("user", null);
            }
        }
        else if ("student".equals(role)){
            // 1.根据学员账号查询学员信息
            Student s = studentMapper.selectByStudentName(userName);
            // 2.如果学员信息查询到了，则对比密码
            if(s!=null && s.getPassword().equals(pwd)){
                cacheService.add("user",s);
                rs.put("msg","学生登录成功！");
                rs.put("user",s.getUserName());
                s.setImage(null);
                s.setPassword(null);
                rs.put("student",s);
            }else {
                rs.put("msg", "学生登录失败！");
                rs.put("user", null);
            }
        }
        else if ("manager".equals(role)){
            // 1.根据学员账号查询学员信息
            Manager m = managerMapper.selectByManagerName(userName);
            // 2.如果学员信息查询到了，则对比密码
            if(m!=null && m.getPassword().equals(pwd)){
                cacheService.add("user",m);
                rs.put("msg","管理员登录成功！");
                rs.put("user",m.getUserName());
                m.setImage(null);
                m.setPassword(null);
                rs.put("manager",m);
                System.out.println("管理员登录成功");
            }else {
                rs.put("msg", "管理员登录失败！");
                rs.put("user", null);
                System.out.println("管理员登录失败");
            }
        }
        else{
            rs.put("msg","角色信息不正确！");
            rs.put("user",null);
       }
        return rs;
    }

    //注册功能——检查注册的角色
    public Map regcheck(String userName, String pwd, String role){
        Map<String,Object> rm = new HashMap<>();
        if("student".equals(role)){
            List<Student> student = studentMapper.registerCheck(userName);//查询是否存在准备注册的账号
            if(student.size()==0)
            {
                rm.put("msg","学生账号注册成功！请登录！");
                rm.put("user",null);
            }
            else{//账号已存在，则返回登录界面
                for (Student s:student){
                    rm.put("msg","该账号已存在！请直接登录！");
                    rm.put("user",s.getUserName());
                }
            }
        }
        else if("teacher".equals(role)){
            List<Teacher> teacher = teacherMapper.registerCheck(userName);//查询是否存在准备注册的账号
            if(teacher.size()==0)
            {
                rm.put("msg","教师账号注册成功！请登录！");
                rm.put("user",null);
            }
            else{//账号已存在，则返回登录界面
                for (Teacher t:teacher){
                    rm.put("msg","该账号已存在！请直接登录！");
                    rm.put("user",t.getUserName());
                }
            }
        }
        else{
            List<Manager> manager = managerMapper.registerCheck(userName);//查询是否存在准备注册的账号
            if(manager.size()==0)
            {
                rm.put("msg","管理员账号注册成功！请登录！");
                rm.put("user",null);
            }
            else{//账号已存在，则返回登录界面
                for (Manager m:manager){
                    rm.put("msg","该账号已存在！请直接登录！");
                    rm.put("user",m.getUserName());
                }
            }
        }
        return rm;
    }

}
