package com.example.springbootvuedemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootvuedemo1.entity.Class;
import com.example.springbootvuedemo1.entity.Student;
import com.example.springbootvuedemo1.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ClassMapper extends BaseMapper<Class>{

    //根据学生id查询已加入班级信息
    Page<Class> getYClassInfoBySid(@Param("sid") int sid, Page<Student> page);

    //根据学生id查询未加入班级信息
    Page<Class> getNClassInfoBySid(@Param("sid") int sid,Page<Student> page);

    //根据tid查询对应所教授的班级信息
    Page<Class> getOwnClass(@Param("tid") int tid, Page<Teacher> page);




    /**
     * 作者：郭旭
     * */
    /*
     * cid	int
     * cname	varchar(50)
     * tid	int
     * cdescribe	varchar(50)
     * */

    // 创建班级
    @Select("insert into class(cname, cdescribe, tid) value(#{cname}, #{cdescribe}, #{tid})")
    void createClass(String cname, String cdescribe, Integer tid);


    // 根据id删除班级
    @Select("delete from class where cid = #{cid}")
    void deleteClassById(Integer cid);


    // 根据id更新班级信息
    // 当传入的（cname和cdescribe）不为空时，执行更新操作
    @Update("<script>"+
            "update class set cid=#{cid}"+
            "<if test='cname != \"\" and cname != null '>"+
            ",cname=#{cname}"+
            "</if>"+
            "<if test='cdescribe != \"\" and cdescrib" +
            "e != null '>"+
            ",cdescribe=#{cdescribe}"+
            "</if>"+
            "where cid=#{cid}"+
            "</script>")
    void updateClassById(String cname, String cdescribe, Integer cid);


    // 查看所有班级
    @Select("select * from class")
    List<Class> selectAllClass();



    // 教师查询自己创建班级的某个班级
    @Select({"<script>",
            "select * from class c where c.cid != 0 and c.tid=#{tid}",
            "<if test = 'keyword != \"\" and keyword != null '>",
            "and c.cname like CONCAT('%',#{keyword},'%')",
            "</if>",
            "</script>"})
    List<Class> selectClassByTidAndKey(Integer tid, String keyword);
    // 学生在对应cid中，根据关键字查找某个班级
    @Select({"<script>",
            "select * from sc,class where sc.sid = #{sid} and sc.cid = class.cid",
            "<if test = 'keyword != \"\" and keyword != null '>",
            "and class.cname like CONCAT('%',#{keyword},'%')",
            "</if>",
            "</script>"})
    List<Class> selectClassBySidAndKey(Integer sid, String keyword);



    // 查询某个班级
    // 关键词模糊查询
    @Select({"<script>",
            "select * from class c where c.cid != 0",
            "<if test = 'keyword != \"\" and keyword != null '>",
            "and c.cname like CONCAT('%',#{keyword},'%')",
            "</if>",
            "</script>"})
    List<Class> selectClass(String keyword);

}
