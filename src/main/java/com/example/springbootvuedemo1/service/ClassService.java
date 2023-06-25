package com.example.springbootvuedemo1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvuedemo1.entity.Class;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService extends IService<Class> {

    List<Class> getYClassInfoBySid(int sid,int pageNum,int pageSize);

    List<Class> getNClassInfoBySid(int sid,int pageNum,int pageSize);

    //List<Class> listMyAllY();

    //查询班级
    //List<Class> chaxunbanjijiaruqingkuang();

    //    @Override
    //    public List<Class> listY(){
    //        return classMapper.listMyAll();
    //    }
        //查询班级
//    List<Class> selectClass();
//
//    //查询num
//    int selectnum();
//    List<Class> listY();
}
