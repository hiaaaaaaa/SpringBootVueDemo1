package com.example.springbootvuedemo1.mapper;

import com.example.springbootvuedemo1.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //表名为examquestion包含字段eid、qid、qdescribe、answer、point
    //上传题目
    @Insert("insert into examquestion(eid,qid,qdescribe,answer,point) values(#{eid},#{qid},#{qdescribe},#{answer},#{point})")
    void uploadQuestion(Integer eid, Integer qid, String qdescribe, String answer, Integer point);
    //根据id删除题目
    @Delete("delete from examquestion where qid=#{qid}")
    void deleteQuestionById(Integer qid);
    //根据id修改题目，并且更新的字段可以为空，sql语句根据字段是否为空来判断是否更新
    @Update("<script>" +
            "update examquestion set qid=#{qid}" +
            "<if test='qdescribe != \"\" and qdescribe != null '>" +
            ",qdescribe=#{qdescribe}" +
            "</if>" +
            "<if test='answer != \"\" and answer != null '>" +
            ",answer=#{answer}" +
            "</if>" +
            "<if test='point != \"\" and point != null '>" +
            ",point=#{point}" +
            "</if>" +
            "where qid=#{qid}" +
            "</script>")
    void updateQuestionById(Integer eid, Integer qid, String qdescribe, String answer,Integer point);

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
