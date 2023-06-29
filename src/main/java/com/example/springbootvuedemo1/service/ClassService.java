package com.example.springbootvuedemo1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvuedemo1.entity.Class;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService extends IService<Class> {

    List<Class> getYClassInfoBySid(int sid,int pageNum,int pageSize);

    List<Class> getNClassInfoBySid(int sid,int pageNum,int pageSize);

}
