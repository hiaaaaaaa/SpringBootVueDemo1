package com.example.springbootvuedemo1.mapper;

import com.example.springbootvuedemo1.entity.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoMapper {
    /**
     * 作者：郭旭
     * */

    /**
     * vid	int
     * vname	varchar (50)
     * vlink	varchar（100）
     * cid	int
     * ******/

    // 上传视频的辅助功能
    // 查找传过来的cid，在video表中对应的所有vid的最大值
    @Select("select max(vid) from video where cid=#{cid}")
    Integer selectMaxVid(Integer cid);

    // 上传视频"insert into video(vid,vname,vlink,cid) values(#{vid},#{vname},#{vlink},#{cid})")
    @Insert("insert into video(vid,vname,vlink,cid) values(#{vid},#{vname},#{vlink},#{cid})")
    void uploadVideo(Integer vid, String vname, String vlink, Integer cid);

    //根据id删除视频
    @Delete("delete from video where vid=#{vid}")
    void deleteVideoByVid(Integer vid);


    // 根据id修改视频信息
    // 当传入的vname不为空时执行更新操作
    // 此处只能修改视频名和视频链接
    @Update("<script>"+
            "update video set vid=#{vid}"+
            "<if test='vname != \"\" and vname != null '>"+
            ",vname=#{vname}"+
            "</if>"+
            "where vid=#{vid}"+
            "</script>")
    void updateVideoById(Integer vid, String vname);
//    @Update("update video set cid=#{cid},vname=#{vname},vlink=#{vlink} where vid=#{vid}")
//    void updateQuestionById(Integer vid, String vname, String vlink, Integer cid);


    //查询某一课程的所有视频
    @Select("select * from video where cid=#{cid}")
    List<Video> selectVideoByCid(Integer cid);

}
