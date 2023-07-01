package com.example.springbootvuedemo1.controller;

import com.example.springbootvuedemo1.entity.Video;
import com.example.springbootvuedemo1.service.Impl.VideoImpl;
import com.example.springbootvuedemo1.util.R;
import com.example.springbootvuedemo1.util.ResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class VideoController {
    /**
     * 作者：郭旭
     * */


    @Resource
    private VideoImpl videoImpl;

    private static String accessKey = "MwagXfSsYvY4dgENK3gLtfqnlPeY2hirjZ4sVPed";
    private static String accessSecretKey = "5GayqbCLrS4N2WRbDhKCOCmf5Xn_54hAyoMmPWZu";
    private static String bucket = "zhiyue20230628";
    private static String url = "rwy0m0qoc.hn-bkt.clouddn.com";


    /*=================上传视频，这里使用了云存储=================*/
    @ResponseBody
    @RequestMapping("/uploadVideo")
   public R uploadVideo(String vname, MultipartFile videoFile, Integer cid) throws IOException{
        try {
            // 1.获取文件上传的字节流
            byte[] fileBytes = videoFile.getBytes();

            // 3.获取文件名
            String originalFilename = videoFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//分离后缀名
            String filename = UUID.randomUUID().toString().replace("-","")+suffix;

            // 4.构建一个带指定Region对象的配置类
            // Region.华南
            Configuration configuration = new Configuration(Region.huanan());
            UploadManager uploadManager = new UploadManager(configuration);

            // 5.获取七牛云提供的token
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            uploadManager.put(fileBytes, filename, upToken);

            // 将视频存储地址用vlink字段保存
            String vlink = url + "/" + filename;
            // 打印输出一下
            System.out.println("vname:"+vname);
            System.out.println("vlink:"+vlink);
            System.out.println("cid:"+cid);
            // 调用service层，上传视频
            videoImpl.uploadVideo(vname,vlink,cid);

            return R.ok("视频上传成功！");

        }catch (IOException e){
            e.printStackTrace();
        }
        return R.error(ResultCode.ERROR,"视频上传失败！");
    }


//    @ResponseBody
//    @RequestMapping("/uploadVideo")
//    public String uploadVideo(Integer vid, String vname, String vlink, Integer cid){
//        System.out.println("vid:"+vid);
//        System.out.println("vname:"+vname);
//        System.out.println("vlink:"+vlink);
//        System.out.println("cid:"+cid);
//
//        videoImpl.uploadVideo(vid,vname,vlink,cid);
//        return "success";
//    }


    /*=================删除视频=================*/
    @ResponseBody
    @RequestMapping("/delectVideoByVid")
    public String delectVideoByVd(Integer vid){
        System.out.println("vid:"+vid);
        videoImpl.delectVideoByVid(vid);
        return "success";
    }


    /*=================修改视频信息=================*/
    // 只能修改视频名
    @ResponseBody
    @RequestMapping("/updateVideoByVid")
    public String updateVideoByVd(Integer vid, String vname){
        System.out.println("vid:"+vid);
        System.out.println("vname:"+vname);
        videoImpl.updateVideoByVid(vid,vname);
        return "success";
    }


    /*=================根据课程id，查询所有视频=================*/
    @ResponseBody
    @RequestMapping("/selectVideoByCid")
    public R selectVideoByCid(Integer cid,Integer page,Integer limit){
        System.out.println("cid:"+cid);
//        String json = objectToJson(videoImpl.selectVideoByCid(cid));
//        System.out.println(json);
        PageHelper.startPage(page,limit);
        List<Video> videoList=videoImpl.selectVideoByCid(cid);
        PageInfo<Video> pageInfo=new PageInfo<>(videoList);
        return R.ok().setData(pageInfo);
    }



    public String objectToJson(Object object){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json= mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

}
