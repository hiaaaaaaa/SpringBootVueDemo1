package com.example.springbootvuedemo1.service.Impl;

import com.example.springbootvuedemo1.entity.Question;
import com.example.springbootvuedemo1.mapper.QuestionMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionImpl {
    @Autowired
    //定义Mapper
    private QuestionMapper questionMapper;
    //表名为examquestion包含字段eid、qid、qdescribe、answer
    //添加题目
    public void uploadQuestion(Integer eid, Integer qid, String qdescribe, String answer,Integer point,String qtype,String a,String b,String c,String d){
        questionMapper.uploadQuestion(eid,qid,qdescribe,answer,point,qtype,a,b,c,d);
    }
    //根据id删除题目
    public void deleteQuestionById(Integer qid){
        questionMapper.deleteQuestionById(qid);
    }
    //根据试卷id删除题目
    public void deleteQuestionByEid(Integer eid){
        questionMapper.deleteQuestionByEid(eid);
    }
    //根据id修改题目
        public void updateQuestionById(Integer eid, Integer qid, String qdescribe, String answer,Integer point,Integer qtype,String a,String b,String c,String d){
        questionMapper.updateQuestionById(eid,qid,qdescribe,answer,point,qtype,a,b,c,d);
    }
    //查询某一份试卷的所有题目
    public List<Question> selectQuestionByEid(Integer eid) {
        return questionMapper.selectQuestionByEid(eid);
    }
}
