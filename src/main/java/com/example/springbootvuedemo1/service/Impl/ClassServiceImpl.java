package com.example.springbootvuedemo1.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootvuedemo1.entity.Class;
import com.example.springbootvuedemo1.entity.Student;
import com.example.springbootvuedemo1.mapper.ClassMapper;
import com.example.springbootvuedemo1.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {


    @Autowired
    @Resource
    private ClassMapper classMapper;

    /*
     *作者：周慧
     * */
    //学生查看已加入班级情况
    @Override
    public List<Class> getYClassInfoBySid(int sid,int pageNum, int pageSize) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        return classMapper.getYClassInfoBySid(sid,page).getRecords();
    }

    //学生查看未加入班级情况
    @Override
    public List<Class> getNClassInfoBySid(int sid,int pageNum, int pageSize) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        return classMapper.getNClassInfoBySid(sid,page).getRecords();
    }


    /*
     *作者：郭旭
     * */

    // 查询所有班级
    public List<Class> selectAllClass() {
        return classMapper.selectAllClass();
    }


    // 根据关键字查询班级
    public List<Class> selectClass(String keyword){
        return classMapper.selectClass(keyword);
    }

    // 增加新班级
    public void createClass(String cname, String cdescribe, Integer tid){
        classMapper.createClass(cname,cdescribe,tid);
    }

    //删除整个班级
    public void deleteClass(Integer cid){
        classMapper.deleteClassById(cid);
    }

    // 更新班级信息
    public void updateClass(Integer cid, String cname, String cdescribe){
        classMapper.updateClassById(cname, cdescribe, cid);
    }

}
