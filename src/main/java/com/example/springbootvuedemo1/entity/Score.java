package com.example.springbootvuedemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Integer sid;
    private Integer eid;
    private Integer score;
    private Examination examination;
}
