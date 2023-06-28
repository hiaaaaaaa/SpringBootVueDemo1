package com.example.springbootvuedemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootvuedemo1.entity.SC;
import com.example.springbootvuedemo1.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Page<Student> listAll(Page<Student> page);

    List<Student> listOne(Student student);

    int addUpdate(Student student);

    int deleteById(int id);

    int modUpdate(Student student);

    int aggUpdate(SC sc);

    int disUpdate(SC sc);
    Page<Student> listReq(Page<Student> page);

    /*
    * 作者：梁伟静
    * */

    @Insert("insert into sc(sid,cid,state) values(#{sid},#{cid},#{state})")
    void insertStudentClass(Integer sid, Integer cid, Integer state);

    @Delete("delete from sc where sid=#{sid} and cid=#{cid}")
    void deleteStudentClass(Integer sid, Integer cid);


    /*
    * 作者：万梓欣
    * */

    //登录验证
    Student selectByStudentName(@Param("userName") String userName);

    //查询是否有学生的信息（登录验证）
    @Select("select count(*) from student where userName=#{userName} and password=#{password}")
    int loginCheck(String userName,String password);


    //修改个人资料
    void updateStudent(@Param("sname") String sname,
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
    List<Student> registerCheck(String userName);

    @Select("SELECT * FROM student WHERE userName=#{userName}")
    Student getImagebase(String userName);

    //修改头像
    void updateImage_Stu(@Param("image") byte[] image,
                         @Param("userName") String userName);
}
