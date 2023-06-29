package com.example.springbootvuedemo1.controller;

import com.example.springbootvuedemo1.entity.Class;
import com.example.springbootvuedemo1.entity.Student;
import com.example.springbootvuedemo1.entity.Teacher;
import com.example.springbootvuedemo1.service.ClassService;
import com.example.springbootvuedemo1.service.Impl.ClassServiceImpl;
import com.example.springbootvuedemo1.util.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private CacheService cacheService;

    //学生查看已加入班级的整体情况
    @GetMapping("/class/yijiaru")
    @ResponseBody
    public List<Class> getYClassInfoBySid(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        Student student=cacheService.getObject("user",Student.class);
        return classService.getYClassInfoBySid(student.getSid(),pageNum, pageSize);
    }

    //学生查看没有加入的班级的整体情况
    @GetMapping("/class/weijiaru")
    @ResponseBody
    public List<Class> getNClassInfoBySid(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        Student student=cacheService.getObject("user",Student.class);
        return classService.getNClassInfoBySid(student.getSid(),pageNum, pageSize);
    }

    //教师查看自己创建的班级列表信息
    @GetMapping("/teacher/selectownclass")
    @ResponseBody
    public List<Class> getOwnClass(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        Teacher teacher=cacheService.getObject("user",Teacher.class);
        return classService.getOwnClass(teacher.getTid(),pageNum, pageSize);
    }

    @Resource
    ClassServiceImpl classServiceImpl;

    /**
     * 功能描述： 教师用户创建班级，获取前端数据，传回是否创建成功的信息
     * cid：班级id
     * cname：班级name
     * cdescribe：班级描述
     * tid：创建该班级的教师
     * */
    @ResponseBody
    @RequestMapping("/createClass")
    public String createClass(String cname, String cdescribe){
        Teacher teacher = cacheService.getObject("user",Teacher.class);
        if (teacher == null){
            return "请先登录！";
        }
        System.out.println("cname:"+cname);
        System.out.println("cdescribe"+cdescribe);
        classServiceImpl.createClass(cname,cdescribe, teacher.getTid());
        return "创建班级成功！";
    }



    /**
     * 功能描述： 教师用户、学生用户查询班级信息，前端返回所有的班级信息
     * */
    @ResponseBody
    @RequestMapping("/selectAllClass")
    public List<Class> selectAllClass(){
        List list = classServiceImpl.selectAllClass();
        // 将查到的结果转化为JSON数据
//        String json = ObjectToJson(list);
//        System.out.println("所有班级信息为："+json);
        return list;
    }


    /**
     * 功能描述： 教师用户、学生用户根据关键词查询班级信息，前端返回所有符合条件的班级信息
     * */
    @ResponseBody
    @RequestMapping("/selectClass")
    public List<Class> selectClass(String keyword){
        List list = classServiceImpl.selectClass(keyword);
        // 将查到的结果转化为JSON数据
//        String json = ObjectToJson(list);
//        System.out.println("关键字对应的班级信息为："+json);
        return list;
    }


    /**
     * 功能描述： 教师用户删除班级信息，前端返回是否成功删除班级的信息
     * */
    @ResponseBody
    @RequestMapping("/deleteClass")
    public String deleteClass(Integer cid){
        System.out.println("班级名称："+cid);
        classServiceImpl.deleteClass(cid);
        return "班级删除成功！";
    }


    /*
     * 功能描述：教师用户修改班级信息，获取前端传来的数据，返回是否修改成功的信息
     */
    @ResponseBody
    @RequestMapping("/updateClass")
    public String updateClass(Integer cid, String cname, String cdescribe){
        classServiceImpl.updateClass(cid,cname,cdescribe);
        return "班级更新成功！";
    }

}
