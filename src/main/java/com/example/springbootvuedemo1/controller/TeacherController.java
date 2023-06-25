package com.example.springbootvuedemo1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springbootvuedemo1.entity.Teacher;
import com.example.springbootvuedemo1.service.Impl.LoginService;
import com.example.springbootvuedemo1.service.Impl.TeacherServiceImpl;
import com.example.springbootvuedemo1.service.TeacherService;
import com.example.springbootvuedemo1.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacher/listallTeacher")
    public List<Teacher> getall(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return teacherService.listAllTeacher(pageNum, pageSize);
    }

    @PostMapping("/teacher/FQlistTeacher")
    @ResponseBody
    public List<Teacher> FQlist(@RequestBody Teacher teacher){
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        //模糊查询
        lambdaQueryWrapper.like(Teacher::getTname, teacher.getTname());
        //完全匹配
        //lambdaQueryWrapper.eq(User::getName,user.getName());
        return teacherService.list();
    }

    @GetMapping("/teacher/listoneTeacher")
    @ResponseBody
    public R getone(Teacher teacher){
        List<Teacher> list = this.teacherService.listOneTeacher(teacher);
        return R.ok().setData(list);
    }

    @PostMapping("/teacher/saveTeacher")
    @ResponseBody
    public R insert(@RequestBody Teacher teacher){
        int result = this.teacherService.addTeacher(teacher);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"添加更新失败");
        }
    }

    @DeleteMapping ("/teacher/delTeacher")
    @ResponseBody
    public R delete(@RequestBody Teacher teacher){
        int result = this.teacherService.delTeacher(teacher.getTid());
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"删除数据失败");
        }
    }

    @PutMapping("/teacher/modTeacher")
    @ResponseBody
    public R mod(@RequestBody Teacher teacher){
        int result = this.teacherService.modTeacher(teacher);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"修改更新数据失败");
        }
    }


    @Autowired
    private TeacherServiceImpl teacherServiceImpl;


    @Autowired
    private LoginService loginService;
    private String login_userName;

    @RequestMapping("/Teacher")
    public R loginTea(String userName, String pwd, String role, HttpServletRequest request){
        Map rs=loginService.check(userName,pwd,role);
        login_userName=userName;
        if (rs.get("user")!=null){
            request.getSession().setAttribute("user",rs.get("user"));
            request.getSession().setAttribute("role",role);
            return R.ok().setData(rs.get("teacher")).setRole(role);
        }else {
            request.setAttribute("msg",rs.get("msg"));
            return R.error(100,"登录失败");
        }
    }

    //显示个人资料（教师）
    @ResponseBody
    @RequestMapping("/selTeaByName")
    public Teacher selTea(HttpServletRequest request){
        Teacher teacher=teacherServiceImpl.selectByTeacherName(login_userName);
        return teacher;
    }

    //修改个人资料（教师）
    @RequestMapping("/updateTea")
    public Teacher updateTea(String tname,
                            String sex,
                            Integer age,
                            String email,
                            String phoneNumber,
                            HttpServletRequest request){
        teacherServiceImpl.updateTeacher(tname,sex,age,email,phoneNumber,login_userName);
        Teacher teacher=teacherServiceImpl.selectByTeacherName(login_userName);
        return teacher;
    }

    //修改密码（教师）
    @RequestMapping("/updateTeaPwd")
    public String updateTeaPwd(String password){

        teacherServiceImpl.updatePassword(password,login_userName);
        Teacher teacher=teacherServiceImpl.selectByTeacherName(login_userName);
        return teacher.getPassword();
    }

    //注册接口
    @RequestMapping("/loginInTea")
    public R loginInTea(String userName, String pwd, String role, HttpServletRequest request){
        Map rm = loginService.regcheck(userName,pwd,role);
        if (rm.get("user")==null){//账号不存在
            request.getSession().setAttribute("user",rm.get("user"));
            request.getSession().setAttribute("role",role);
            teacherServiceImpl.loginIn(userName,pwd);
            System.out.println("教师账号注册成功");
            return R.ok();
        }
        else {//账号存在
            request.setAttribute("msg",rm.get("msg"));
            System.out.println("教师账号已存在");
            return R.error(100,"该教师账号已存在");
        }
    }


}
