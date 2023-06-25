package com.example.springbootvuedemo1.service.Impl;

import com.example.springbootvuedemo1.entity.Manager;
import com.example.springbootvuedemo1.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    public boolean loginCheck(String userName,String password){
        int i = managerMapper.loginCheck(userName, password);
        return i>0;
    }

    //根据账号查询个人资料
    public Manager selectByManagerName(String userName){
        return managerMapper.selectByManagerName(userName);
    }

    //修改个人资料
    public void updateManager(String sname, String sex, Integer age, String email,String phoneNumber,String userName)
    {
        managerMapper.updateManager(sname,sex,age,email,phoneNumber,userName);
    }

    //修改密码
    public void updatePassword(String password,String userName)
    {
        managerMapper.updatePassword(password,userName);
    }

    //注册功能
    public void loginIn(String userName,String password){
        managerMapper.loginIn(userName,password);
    }

}
