package com.example.springbootvuedemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    //包含字段eid、qid、qdescribe、answer
    private Integer eid;
    private Integer qid;
    private String qdescribe;
    private String answer;

    private Integer point;

    //没有eid和qid的构造方法

    public Question(String qdescribe, String answer,Integer point) {
        this.qdescribe = qdescribe;
        this.answer = answer;
        this.point = point;
    }
}
