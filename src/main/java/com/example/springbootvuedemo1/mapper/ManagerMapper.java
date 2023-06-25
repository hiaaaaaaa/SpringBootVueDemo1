package com.example.springbootvuedemo1.mapper;

import com.example.springbootvuedemo1.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManagerMapper {

    Manager selectByManagerName(@Param("userName") String userName);

    //查询是否有学生的信息
    @Select("select count(*) from manager where userName=#{userName} and password=#{password}")
    int loginCheck(String userName,String password);

    //修改个人资料
    void updateManager(@Param("mname") String mname,
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
                 @Param("password") String password);

    //查询账号是否已经注册
    @Select("select * from manager where userName=#{userName}")
    List<Manager> registerCheck(String userName);

}
