package com.example.springbootvuedemo1.mapper;

import com.example.springbootvuedemo1.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //表名为examquestion包含字段eid、qid、qdescribe、answer、point、qtype、a、b、c、d
    //上传题目
    @Insert("insert into examquestion(eid,qid,qdescribe,answer,point,qtype,a,b,c,d) values(#{eid},#{qid},#{qdescribe},#{answer},#{point},#{qtype},#{a},#{b},#{c},#{d})")
    void uploadQuestion(Integer eid, Integer qid, String qdescribe, String answer,Integer point,String qtype,String a,String b,String c,String d);
    //根据id删除题目
    @Delete("delete from examquestion where qid=#{qid}")
    void deleteQuestionById(Integer qid);
    //根据id修改题目，并且更新的字段可以为空，可使用if标签来判断字段是否为空
    @Update("<script>"+
            "update examquestion set eid=#{eid}," +
            "<if test='qdescribe!=null'>qdescribe=#{qdescribe},</if>" +
            "<if test='answer!=null'>answer=#{answer},</if>" +
            "<if test='point!=null'>point=#{point},</if>" +
            "<if test='qtype!=null'>qtype=#{qtype},</if>" +
            "<if test='a!=null'>a=#{a},</if>" +
            "<if test='b!=null'>b=#{b},</if>" +
            "<if test='c!=null'>c=#{c},</if>" +
            "<if test='d!=null'>d=#{d},</if>" +
            "qid=#{qid} where eid=#{eid} and qid=#{qid}"+
            "</script>")
    void updateQuestionById(@Param("eid") Integer eid, @Param("qid") Integer qid, @Param("qdescribe") String qdescribe, @Param("answer") String answer, @Param("point") Integer point);

    //查询某一份试卷的所有题目
    @Select("select * from examquestion where eid=#{eid}")
    List<Question> selectQuestionByEid(Integer eid);

    //根据试卷id删除题目
    @Delete("delete from examquestion where eid=#{eid}")
    void deleteQuestionByEid(Integer eid);

    //将成绩插入数据库
    @Insert("insert into es(sid,eid,score) values(#{sid},#{eid},#{score})")
    void insertStudentScore(Integer sid, Integer eid, Integer score);

}
