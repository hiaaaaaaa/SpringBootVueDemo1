package com.example.springbootvuedemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootvuedemo1.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    //查询所有教师
    Page<Teacher> listAll(Page<Teacher> page);

    //根据姓名查询教师
    List<Teacher> listOne(Teacher teacher);

    //添加教师
    int addUpdate(Teacher teacher);

    //删除教师
    int delById(int id);

    //修改教师
    int modUpdate(Teacher teacher);

    //根据teacherid查询teacher
    @Select("select * from teacher where tid=#{tid}")
    Teacher selectByTeacherId(Integer tid);

    /*
    * 作者：万梓欣
    * */

    Teacher selectByTeacherName(@Param("userName") String userName);

    //查询是否有学生的信息
    @Select("select count(*) from teacher where userName=#{userName} and password=#{password}")
    int loginCheck(String userName,String password);

    //修改个人资料
    void updateTeacher(@Param("tname") String tname,
                       @Param("sex") String sex,
                       @Param("age") Integer age,
                       @Param("email") String email,
                       @Param("phoneNumber") String phoneNumber,
                       @Param("userName") String userName);
    //修改密码
    void updatePassword(@Param("password") String password,
                        @Param("userName") String userName);

    //注册功能
    void loginIn(@Param("userName") String userName,
                 @Param("password") String password,
                 @Param("image") byte[] image);

    //查询账号是否已经注册
    @Select("select * from student where userName=#{userName}")
    List<Teacher> registerCheck(String userName);

    @Select("SELECT * FROM teacher WHERE userName=#{userName}")
    Teacher getImagebase_Tea(String userName);

    //修改头像
    void updateImage_Tea(@Param("image") byte[] image,
                         @Param("userName") String userName);
}
