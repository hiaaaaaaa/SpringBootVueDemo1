package com.example.springbootvuedemo1.controller;

import com.example.springbootvuedemo1.entity.Question;
import com.example.springbootvuedemo1.util.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import com.example.springbootvuedemo1.service.Impl.QuestionImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class QuestionController {
    @Resource
    //定义service
    private QuestionImpl questionImpl;
    /*
    * 功能描述：教师添加题目，返回添加成功的信息
    * eid: 试卷id
    * qid: 题目id
    * qdescribe: 题目描述
    * answer: 答案
    * */
//    @ResponseBody
//    @RequestMapping("/uploadQuestion")
//    public R uploadQuestion(Integer eid, Integer qid, String qdescribe, String answer, Integer point){
//        //调用service层,添加题目
//        questionImpl.uploadQuestion(eid,qid,qdescribe,answer,point);
//        return R.ok("添加成功！");
//    }
    /*
    * 功能描述：教师删除题目，返回删除成功的信息
    * */
    @ResponseBody
    @RequestMapping("/deleteQuestionById")
    public R deleteQuestionById(Integer qid){
        System.out.println("qid:"+qid);
        //调用service层,删除题目
        questionImpl.deleteQuestionById(qid);
        return R.ok("删除成功！");
    }
    /*
    * 功能描述：教师修改题目，返回修改成功的信息
     */
    @ResponseBody
    @RequestMapping("/updateQuestionById")
    public R updateQuestionById(Integer eid, Integer qid, String qdescribe, String answer,Integer point){
        System.out.println("eid:"+eid);
        System.out.println("qid:"+qid);
        System.out.println("qdescribe:"+qdescribe);
        System.out.println("answer:"+answer);
        //调用service层,修改题目
        questionImpl.updateQuestionById(eid,qid,qdescribe,answer,point);
        return R.ok("修改成功！");
    }
    /*
    * 功能描述：根据试卷id查询所有题目，返回查询成功的信息
    * */
    @ResponseBody
    @RequestMapping("/selectQuestionByEid")
    public R selectQuestionByEid(Integer eid){
        System.out.println("eid:"+eid);
        //调用service层,查询题目
        List<Question> list = questionImpl.selectQuestionByEid(eid);
        return R.ok().setData(list);
    }

}
