package com.example.springbootvuedemo1.service.Impl;

import com.example.springbootvuedemo1.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoImpl {
    @Autowired
    private VideoMapper videoMapper;

    //添加视频
    public void uploadVideo(Integer vid,String vname, String vlink, Integer cid){
        videoMapper.uploadVideo(vid, vname, vlink, cid);
    }

    //根据vid删除视频
    public void delectVideoByVid(Integer vid){
        videoMapper.deleteVideoByVid(vid);
    }

    //根据vid修改视频
    public void updateVideoByVid(Integer vid, String vname){
        videoMapper.updateVideoById(vid,vname);
    }

    //查询课程的所有题目
    public Object selectVideoByCid(Integer cid) {
        return videoMapper.selectVideoByCid(cid);
    }

}
