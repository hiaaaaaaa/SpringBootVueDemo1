package com.example.springbootvuedemo1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sc")
public class SC {
    @TableId("sid")
    private int sid;
    @TableId("cid")
    private int cid;
    @TableField("state")
    private int state;

}
