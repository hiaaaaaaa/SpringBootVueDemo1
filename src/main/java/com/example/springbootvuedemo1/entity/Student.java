package com.example.springbootvuedemo1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student")
public class Student {
    @TableId("sid")
    private int sid;
    @TableField("userName")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("sname")
    private String sname;
    @TableField("sex")
    private String sex;
    @TableField("age")
    private int age;
    @TableField("email")
    private String email;
    @TableField("phoneNumber")
    private String phoneNumber;
    @Transient
    @TableField(exist = false)
    private int cid;
    @Transient
    @TableField(exist = false)
    private String cname;
}
