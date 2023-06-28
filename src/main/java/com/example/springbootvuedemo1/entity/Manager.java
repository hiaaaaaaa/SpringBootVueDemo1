package com.example.springbootvuedemo1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("manager")
public class Manager {
    @TableId("mid")
    private int mid;
    @TableField("userName")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("mname")
    private String mname;
    @TableField("sex")
    private String sex;
    @TableField("age")
    private int age;
    @TableField("email")
    private String email;
    @TableField("phoneNumber")
    private String phoneNumber;

    private byte[] image;

    private String imageBase64;
}
