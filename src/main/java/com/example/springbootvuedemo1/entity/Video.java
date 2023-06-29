package com.example.springbootvuedemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private Integer vid;
    private String vname;
    private String vlink;
    private Integer cid;
}
