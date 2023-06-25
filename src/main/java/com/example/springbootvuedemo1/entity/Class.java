package com.example.springbootvuedemo1.entity;

/*
* 班级实体
* 班级 班主任 总人数
* */

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("class")
public class Class {
    @TableId("cid")
    private int cid;
    @TableField("cname")
    private String cname;
    @TableField("cdescribe")
    private String cdescribe;
    @TableField("tid")
    private int tid;
    @Transient
    @TableField(exist = false)
    private String tname;
}
