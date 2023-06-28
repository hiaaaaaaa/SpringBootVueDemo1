package com.example.springbootvuedemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Examination {
    //包含字段eid、ename、edescribe、tid
    private Integer eid;
    private String ename;
    private String edescribe;
    private Teacher teacher;
    private Integer tid;
    private byte[] image;
    private String imageBase64;

}
