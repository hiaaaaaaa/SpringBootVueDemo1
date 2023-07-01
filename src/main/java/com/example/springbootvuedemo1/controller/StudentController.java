package com.example.springbootvuedemo1.controller;

import cn.hutool.core.codec.Base64Encoder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootvuedemo1.entity.SC;
import com.example.springbootvuedemo1.entity.Score;
import com.example.springbootvuedemo1.entity.Student;
import com.example.springbootvuedemo1.service.Impl.LoginService;
import com.example.springbootvuedemo1.service.Impl.StudentServiceImpl;
import com.example.springbootvuedemo1.service.StudentService;
import com.example.springbootvuedemo1.util.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    /*
     * 作者：梁伟静
     * */
    @Resource
    //定义service
    private StudentServiceImpl studentServiceImpl;
    //学生申请加入班级
    @ResponseBody
    @RequestMapping("/insertStudentClass")
    public R insertStudentClass(Integer sid, Integer cid){
        System.out.println("sid:"+sid);
        System.out.println("cid:"+cid);
        //调用service层,学生申请加入班级
        studentServiceImpl.insertStudentClass(sid,cid,0);
        return R.ok("申请成功！");
    }

    //学生取消申请或退出班级
    @ResponseBody
    @RequestMapping("/deleteStudentClass")
    public R deleteStudentClass(Integer sid, Integer cid){
        System.out.println("sid:"+sid);
        System.out.println("cid:"+cid);
        //调用service层,学生取消申请或退出班级
        studentServiceImpl.deleteStudentClass(sid,cid);
        return R.ok("取消申请或退出班级成功！");
    }

    //前端处理学生答案与正确答案比对，返回成绩
    @ResponseBody
    @RequestMapping("/studentAnswer")
    public R studentAnswer(Integer sid, Integer eid,Integer score){
        //插入成绩表
        studentServiceImpl.insertScore(sid,eid,score);
        return R.ok("提交成功！");
    }

    //学生查看自己的成绩以及试卷信息
    @ResponseBody
    @RequestMapping("/selectScore")
    public R selectScore(Integer sid,Integer page,Integer limit){
        //调用service层,学生查看自己的成绩以及试卷信息
        List<Score> list = studentServiceImpl.selectScore(sid,page,limit);
        PageInfo<Score> pageInfo = new PageInfo<>(list);
        return R.ok().setData(pageInfo);
    }

    /*
    * 作者：周慧
    * */

    @Autowired
    private StudentService studentService;

    //管理员查看所有学生列表数据
    @GetMapping("/student/listall")
    public List<Student> listall(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return studentService.listAllStudent(pageNum, pageSize);
    }


    //管理员根据学生姓名（sname）进行查询
    @GetMapping("/student/lists")
    @ResponseBody
    public R getList(Student student){
        System.out.println("学生姓名："+student.getSname());
        List<Student> list = this.studentService.listOneStudent(student);
        return R.ok().setData(list);
    }

    //管理员向学生表中添加数据
    @PostMapping("/student/save")
    @ResponseBody
    public R insert(@RequestBody Student student){
        int result = this.studentService.addStudent(student);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"管理员添加学生数据失败");
        }
    }

    //管理员根据学生id（sid）删除学生数据
    @DeleteMapping ("/student/delete")
    @ResponseBody
    public R delete(@RequestBody Student student){
        int result = this.studentService.delStudent(student.getSid());
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"管理员删除学生数据失败");
        }
    }

    //管理员根据学生id（sid）修改学生表中数据
    @PutMapping("/student/mod")
    @ResponseBody
    public R mod(@RequestBody Student student){
        int result = this.studentService.modStudent(student);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"管理员修改学生数据失败");
        }
    }

    //教师根据cid查看班级中成员列表信息
    @GetMapping("/student/tlistall")
    @ResponseBody
    public R getOwnStuList(Integer cid, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = this.studentService.listStudentByCid(cid);
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        return R.ok().setData(pageInfo);
    }

//    @GetMapping("/student/tlistall")
//    @ResponseBody
//    public R getOwnStuList(Integer cid){
//        List<Student> list = this.studentService.listStudentByCid(cid);
//        return R.ok().setData(list);
//    }


    //教师根据学生姓名（sname）进行查询学生
    @GetMapping("/student/tlists")
    @ResponseBody
    public R tgetList(Student student){
        System.out.println("学生姓名："+student.getSname());
        List<Student> list = this.studentService.listOneStudent(student);
        return R.ok().setData(list);
    }

    //教师根据学生id（sid）删除sc表中学生信息
    @DeleteMapping ("/student/tdelete")
    @ResponseBody
    public R tdelete(@RequestBody Student student){
        int result = this.studentService.delStudentInSC(student.getSid(),student.getCid());
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"教师删除学生数据失败");
        }
    }

    //教师用户输入cid和cid增加sc表中班级学员
    @PostMapping("/student/addStuInSC")
    @ResponseBody
    public R addStuInSC(@RequestBody SC sc){
        int result = this.studentService.addStudentInSC(sc);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"教师添加学生失败");
        }
    }

    //教师审核学员申请
    //教师查看学生申请进入班级情况表
    @GetMapping("/student/listRequest")
    public List<Student> listRequest(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return studentService.listReqStudent(pageNum, pageSize);
    }

    //教师同意学生申请
    @PutMapping("/student/agreeRequest")
    @ResponseBody
    public R agreeRequest(@RequestBody SC sc){
        int result = this.studentService.agreeStudent(sc);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"教师处理申请操作失败");
        }
    }

    //教师不同意学生申请
    @DeleteMapping ("/student/disRequest")
    @ResponseBody
    public R disagreeRequest(@RequestBody SC sc){
        int result = this.studentService.disStudent(sc);
        if(result > 0){
            return R.ok();
        }else {
            return R.error(100,"教师处理申请操作失败");
        }
    }

    /*
    * 作者：万梓欣
    * */


    @Autowired
    private LoginService loginService;
    private String login_userName;

    @RequestMapping("/Stu")
    public R loginStu(String userName, String pwd, String role, HttpServletRequest request){
        Map rs=loginService.check(userName,pwd,role);
        login_userName=userName;
        if (rs.get("user")!=null){
            request.getSession().setAttribute("user",rs.get("user"));
            request.getSession().setAttribute("role",role);
            return R.ok().setData(rs.get("student")).setRole(role);
        }else {
            request.setAttribute("msg",rs.get("msg"));
            return R.error(100,"登录失败");
        }
    }

    //显示个人资料（学生）
    @RequestMapping("/selStuByName")
    public Student selStu(HttpServletRequest request){
        Student student=studentServiceImpl.selectByStudentName(login_userName);
        return student;
    }

    //修改个人资料（学生）
    @RequestMapping("/updateStu")
    public Student updateStu(String sname,
                            String sex,
                            Integer age,
                            String email,
                            String phoneNumber,
                            HttpServletRequest request){
        studentServiceImpl.updateStudent(sname,sex,age,email,phoneNumber,login_userName);
        Student student=studentServiceImpl.selectByStudentName(login_userName);
        return student;
    }

    //修改密码（学生）
    @RequestMapping("/updatePwd")
    public String updatePwd(String password){

        studentServiceImpl.updatePassword(password,login_userName);
        Student student=studentServiceImpl.selectByStudentName(login_userName);
        return student.getPassword();//返回修改后的密码
    }

    //注册接口
    @RequestMapping("/loginInStu")
    public R loginInStu(String userName, String pwd, String role, MultipartFile multipartFile, HttpServletRequest request){
        Map rm = loginService.regcheck(userName,pwd,role);
        byte[] image=null;
        //获取文件二进制格式
        try {
            image = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (rm.get("user")==null){//账号不存在
            request.getSession().setAttribute("user",rm.get("user"));
            request.getSession().setAttribute("role",role);
            studentServiceImpl.loginIn(userName,pwd,image);
            System.out.println("学生账号注册成功");
            return R.ok();
        }
        else {//账号存在
            request.setAttribute("msg",rm.get("msg"));
            System.out.println("学生账号已存在");
            return R.error(100,"该学生账号已存在");
        }
    }

    //显示头像
    @GetMapping("/getStuImage")
    public Student gteImage(){
        Student student = studentServiceImpl.getImagebaseService_Stu(login_userName);
        //将图片转换为base64编码
        String imageBase64= Base64Encoder.encode(student.getImage());
        student.setImageBase64(imageBase64);

        return student;//前端获取imageBase64字段用于显示头像
    }

    //修改头像
    @RequestMapping("/updateStuImageData")
    public Student updateImageData( MultipartFile multipartFile){
        byte[] image=null;
        //获取文件二进制格式
        try {
            image = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        studentServiceImpl.updateImage_Stu(image,login_userName);
        Student student = studentServiceImpl.getImagebaseService_Stu(login_userName);
        //将图片转换为base64编码
        String imageBase64= Base64Encoder.encode(student.getImage());
        student.setImageBase64(imageBase64);

        return student;

    }
}
