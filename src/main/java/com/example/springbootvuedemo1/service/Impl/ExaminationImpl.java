package com.example.springbootvuedemo1.service.Impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Encoder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootvuedemo1.entity.Examination;
import com.example.springbootvuedemo1.entity.Question;
import com.example.springbootvuedemo1.mapper.ExaminationMapper;
import com.example.springbootvuedemo1.mapper.QuestionMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationImpl {
    @Autowired
    //定义mapper
    ExaminationMapper examinationMapper;
    @Autowired
    QuestionMapper questionMapper;
    //查询所有试卷
    public List<Examination> selectAllExamination(){
        return examinationMapper.selectAllExamination();
    }
    //根据老师姓名、试卷名关键词、试卷描述关键词查询试卷，科目、年级、试卷类型可以为空
    public List<Examination> selectExamination(String keyword,String tname,String keydescribe,Integer page,Integer limit){
        //实现分页功能
        PageHelper.startPage(page, limit);
        List<Examination> examinations=examinationMapper.selectExamination(keyword,tname,keydescribe);
        for(int i=0;i<examinations.size();i++){
            //将图片转换为base64编码
            String imageBase64=Base64Encoder.encode(examinations.get(i).getImage());
            examinations.get(i).setImageBase64(imageBase64);
        }
        return examinations;
    }

    //据老师姓名、试卷名关键词、试卷描述关键词查询试卷总数，科目、年级、试卷类型可以为空
    public Integer selectExaminationCount(String keyword,String tname,String keydescribe){
        return examinationMapper.selectExaminationCount(keyword,tname,keydescribe);
    }

    public void createExamination(String ename, String edescribe, Integer tid, List<Question> questions,byte[] image) {
        //插入试卷表
        examinationMapper.createExamination(ename,edescribe,tid,image);
        //获取刚刚插入的试卷的id
        Integer eid=examinationMapper.selectLastEid();
        System.out.println(questions);
        //插入试题表
        for (int i=0;i<questions.size();i++){
            Question q=questions.get(i);
            questionMapper.uploadQuestion(eid,i+1,q.getQdescribe(),q.getAnswer(),q.getPoint(),q.getQtype(),q.getA(),q.getB(),q.getC(),q.getD());
        }
    }

    public void deleteExamination(Integer eid) {
        questionMapper.deleteQuestionByEid(eid);
        examinationMapper.deleteExaminationById(eid);
    }

    public void updateExamination(Integer eid, String ename, String edescribe) {
        examinationMapper.updateExaminationById(ename,edescribe,eid);
    }
}
