package com.example.springbootvuedemo1.mapper;

import com.example.springbootvuedemo1.entity.Examination;
import com.example.springbootvuedemo1.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExaminationMapper {
    //包含字段eid、ename、edescribe、tid
    //创建试卷
    @Insert("insert into examination(ename,edescribe,tid,image) values(#{ename},#{edescribe},#{tid},#{image})")
    void createExamination(String ename, String edescribe, Integer tid,byte[] image);
    //查询examination表中最后一次插入的数据的eid
    @Select("select eid from examination order by eid desc limit 1")
    Integer selectLastEid();
    //根据id删除试卷
    @Delete("delete from examination where eid=#{eid}")
    void deleteExaminationById(Integer eid);
    //根据id修改试卷以及更新的字段来更新值，函数中的字段可以为空，sql语句根据字段是否为空来判断是否更新
    @Update("<script>" +
            "update examination set eid=#{eid}" +
            "<if test='ename != \"\" and ename != null '>" +
            ",ename=#{ename}" +
            "</if>" +
            "<if test='edescribe != \"\" and edescribe != null '>" +
            ",edescribe=#{edescribe}" +
            "</if>" +
            "where eid=#{eid}" +
            "</script>")
    void updateExaminationById(String ename, String edescribe,Integer eid);
    //查询所有试卷
    @Select("select * from examination")
    List<Examination> selectAllExamination();
    //根据老师姓名、试卷名关键词、试卷描述关键词查询试卷，其中需要根据表中的tid返回teacher类型
    @Select("<script>" +
            "select * from examination e,teacher t where e.tid=t.tid and e.eid!=0" +
            "<if test='keyword != \"\" and keyword != null '>" +
            "and e.ename like CONCAT('%',#{keyword},'%')" +
            "</if>" +
            "<if test='tname != \"\" and tname != null '>" +
            "and t.tname like CONCAT('%',#{tname},'%')" +
            "</if>" +
            "<if test='keydescribe != \"\" and keydescribe != null '>" +
            "and e.edescribe like CONCAT('%',#{keydescribe},'%')" +
            "</if>" +
            "</script>")
    @Results(value = {
            @Result(property = "teacher",column = "tid",
                    javaType= Teacher.class,
                    one=@One(select = "com.example.springbootvuedemo1.mapper.TeacherMapper.selectByTeacherId"))
    })
    List<Examination> selectExamination(String keyword,String tname,String keydescribe);

    //根据老师姓名、试卷名关键词、试卷描述关键词查询试卷总数，老师姓名、试卷名关键词、试卷描述可以为空
    @Select("<script>" +
            "select count(*) from examination e,teacher t where e.tid=t.tid and e.eid!=0" +
            "<if test='keyword != \"\" and keyword != null '>" +
            "and e.ename like CONCAT('%',#{keyword},'%')" +
            "</if>" +
            "<if test='tname != \"\" and tname != null '>" +
            "and t.tname like CONCAT('%',#{tname},'%')" +
            "</if>" +
            "<if test='keydescribe != \"\" and keydescribe != null '>" +
            "and e.edescribe like CONCAT('%',#{keydescribe},'%')" +
            "</if>" +
            "</script>")
    Integer selectExaminationCount(String keyword,String tname,String keydescribe);

    //根据试卷id查询试卷
    @Select("select * from examination e,teacher t where e.tid=t.tid and e.eid=#{eid}")
    @Results(value = {
            @Result(property = "teacher",column = "tid",
                    javaType= Teacher.class,
                    one=@One(select = "com.example.springbootvuedemo1.mapper.TeacherMapper.selectByTeacherId"))
    })
    Examination selectExaminationById(Integer eid);
}
