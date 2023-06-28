package com.example.springbootvuedemo1.controller;

import cn.hutool.core.codec.Base64Encoder;
import com.example.springbootvuedemo1.entity.Manager;
import com.example.springbootvuedemo1.service.Impl.LoginService;
import com.example.springbootvuedemo1.service.Impl.ManagerService;
import com.example.springbootvuedemo1.util.CacheService;
import com.example.springbootvuedemo1.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;


    @Autowired
    private LoginService loginService;
    private String login_userName;

    @RequestMapping("/Manager")
    public R loginMan(String userName, String pwd, String role, HttpServletRequest request){
        Map rs=loginService.check(userName,pwd,role);
        login_userName=userName;
        if (rs.get("user")!=null){
            request.getSession().setAttribute("user",rs.get("user"));
            request.getSession().setAttribute("role",role);
            return R.ok().setData(rs.get("manager")).setRole(role);
        }else {
            request.setAttribute("msg",rs.get("msg"));
            return R.error(100,"登录失败");
        }
    }

    //显示个人资料（管理员）
    @RequestMapping("/selManByName")
    public Manager selMan(HttpServletRequest request){
        Manager manager=managerService.selectByManagerName(login_userName);
        return manager;
    }

    //修改个人资料（学生）
    @RequestMapping("/updateMan")
    public Manager updateMan(String mname,
                             String sex,
                             Integer age,
                             String email,
                             String phoneNumber,
                             HttpServletRequest request){
        managerService.updateManager(mname,sex,age,email,phoneNumber,login_userName);
        Manager manager=managerService.selectByManagerName(login_userName);
        return manager;
    }

    //修改密码（学生）
    @RequestMapping("/updateManPwd")
    public String updateManPwd(String password){

        managerService.updatePassword(password,login_userName);
        Manager manager=managerService.selectByManagerName(login_userName);
        return manager.getPassword();
    }

    //注册接口
    @RequestMapping("/loginInMan")
    public R loginInMan(String userName, String pwd, String role, MultipartFile multipartFile, HttpServletRequest request){
        Map rm = loginService.regcheck(userName,pwd,role);
        byte[] image=null;
        //获取文件二进制格式
        try {
            image = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (rm.get("user")==null){//账号不存在
            request.getSession().setAttribute("user",rm.get("user"));
            request.getSession().setAttribute("role",role);
            managerService.loginIn(userName,pwd,image);
            System.out.println("管理员账号注册成功");
            return R.ok();
        }
        else {//账号存在
            request.setAttribute("msg",rm.get("msg"));
            System.out.println("管理员账号已存在");
            return R.error(100,"账号已存在");
        }
    }

    //显示头像
    @GetMapping("/getImage")
    public Manager gteImage(){
        Manager manager = managerService.getImagebaseService(login_userName);
        //将图片转换为base64编码
        String imageBase64= Base64Encoder.encode(manager.getImage());
        manager.setImageBase64(imageBase64);
        return manager;//前端获取imageBase64字段用于显示头像
    }

    //修改头像
    @RequestMapping("/updateImageData")
    public Manager updateImageData( MultipartFile multipartFile){
        byte[] image=null;
        //获取文件二进制格式
        try {
            image = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        managerService.updateImage(image,login_userName);
        Manager manager = managerService.getImagebaseService(login_userName);
        //将图片转换为base64编码
        String imageBase64= Base64Encoder.encode(manager.getImage());
        manager.setImageBase64(imageBase64);

        return manager;


    }
}
