package com.example.springbootvuedemo1.controller;

import com.alibaba.fastjson.JSON;
import com.example.springbootvuedemo1.entity.Question;
import com.example.springbootvuedemo1.entity.Teacher;
import com.example.springbootvuedemo1.service.Impl.ExaminationImpl;
import com.example.springbootvuedemo1.util.CacheService;
import com.example.springbootvuedemo1.util.R;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
public class ExaminationController {
    //定义service
    @Resource
    ExaminationImpl examinationImpl;
    @Resource
    private CacheService cacheService;
    /*
    * 功能描述：教师用户创建试卷，获取前端传来的数据（包括试卷的基本信息和题目的基本信息），返回是否创建成功的信息
    * eid:试卷id
    * ename:试卷名称
    * edescribe:试卷描述
    * tid:教师id
    * subject:科目
    * grade:年级
    * type:试卷类型
    * */
    private byte[][] defaultImage = new byte[3][];//默认图片

    @ResponseBody
    @RequestMapping(value = "/createExamination",produces = "application/json; charset=UTF-8")
    public R createExamination(MultipartFile multipartFile, String ename, String edescribe, String questions,Integer defaultImageIndex){
        Teacher teacher= cacheService.getObject("user",Teacher.class);
        if(teacher==null){
            return R.error(100,"请先登录！");
        }
        byte[] image=null;
        //如果没有上传图片，使用默认图片
        if(multipartFile==null){
            //如果默认图片为空，读取默认图片
            if(defaultImage[0]==null){
                try {
                    defaultImage[0]= IOUtils.toByteArray(getClass().getResourceAsStream("/static/examdefaultpic/default01.jpg"));
                    defaultImage[1]=IOUtils.toByteArray(getClass().getResourceAsStream("/static/examdefaultpic/default02.jpg"));
                    defaultImage[2]=IOUtils.toByteArray(getClass().getResourceAsStream("/static/examdefaultpic/default03.jpg"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            image=defaultImage[defaultImageIndex];
        }
        else {
            //获取文件二进制格式
            try {
                image = multipartFile.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("questions:"+questions);
        System.out.println("ename:"+ename);
        System.out.println("edescribe:"+edescribe);
        //调用service层,创建试卷
        examinationImpl.createExamination(ename,edescribe,teacher.getTid(), JSON.parseArray(questions,Question.class),image);
        return R.ok("创建成功！");
    }

    /*
    * 功能描述：教师和学生用户根据关键词查询试卷，返回所有试卷的信息
    * */
    @ResponseBody
    @RequestMapping("/selectExamination")
    public R selectExamination(String keyword,String tname,String keydescribe, Integer page, Integer limit){
        //调用service层,查询试卷
        List list = examinationImpl.selectExamination(keyword,tname,keydescribe,page,limit);
        //获取查询结果的总数
        int count = examinationImpl.selectExaminationCount(keyword,tname,keydescribe);
        //将查询结果转换为json数据
        return R.ok().setDataAndCount(list,count);
    }

    /*
    * 功能描述：教师用户删除试卷，获取前端传来的数据，返回是否删除成功的信息
    * */
    @ResponseBody
    @RequestMapping("/deleteExamination")
    public R deleteExamination(Integer eid){
        Teacher teacher= cacheService.getObject("user",Teacher.class);
        if(teacher==null){
            return R.error(100,"请先登录！");
        }
        System.out.println("eid:"+eid);
        //调用service层,删除试卷
        examinationImpl.deleteExamination(eid);
        return R.ok("删除成功！");
    }

    /*
    * 功能描述：教师用户修改试卷，获取前端传来的数据，返回是否修改成功的信息
     */
    @ResponseBody
    @RequestMapping("/updateExamination")
    public R updateExamination(Integer eid,String ename,String edescribe){
        Teacher teacher= cacheService.getObject("user",Teacher.class);
        if(teacher==null){
            return R.error(100,"请先登录！");
        }
        //调用service层,修改试卷
        examinationImpl.updateExamination(eid,ename,edescribe);
        return R.ok("修改成功！");
    }

}
