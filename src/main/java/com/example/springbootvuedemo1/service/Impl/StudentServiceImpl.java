package com.example.springbootvuedemo1.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootvuedemo1.entity.Question;
import com.example.springbootvuedemo1.entity.SC;
import com.example.springbootvuedemo1.entity.Student;
import com.example.springbootvuedemo1.mapper.QuestionMapper;
import com.example.springbootvuedemo1.mapper.StudentMapper;
import com.example.springbootvuedemo1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    @Resource
    private StudentMapper studentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Student> listAllStudent(int pageNum, int pageSize){
        //使用mybatis-plus的分页插件
        Page<Student> page = new Page<>(pageNum, pageSize);
        return studentMapper.listAll(page).getRecords();
    }

    @Override
    public List<Student> listOneStudent(Student student) {
        List<Student> list = this.studentMapper.listOne(student);
        return list;
    }

    @Override
    public List<Student> listReqStudent(int pageNum, int pageSize){
        Page<Student> page = new Page<>(pageNum, pageSize);
        return studentMapper.listReq(page).getRecords();
    }

    @Override
    public int addStudent(Student student) {
        int result = this.studentMapper.addUpdate(student);
        return result;
    }

    @Override
    public int delStudent(int id){
        int result = this.studentMapper.deleteById(id);
        return result;
    }

    @Override
    public int modStudent(Student student){
        int result = this.studentMapper.modUpdate(student);
        return result;
    }

    @Override
    public int agreeStudent(SC sc){
        int result = this.studentMapper.aggUpdate(sc);

        return result;
    }

    @Override
    public int disStudent(SC sc){
        int result = this.studentMapper.disUpdate(sc);
        return result;
    }


    //学生申请加入班级
    public void insertStudentClass(Integer sid, Integer cid, int i) {
        studentMapper.insertStudentClass(sid,cid,i);
    }

    //学生退出班级
    public void deleteStudentClass(Integer sid, Integer cid) {
        studentMapper.deleteStudentClass(sid,cid);
    }
    //学生答题
    public Map<Integer, String> studentAnswer(Integer sid, Integer eid, String answer) {
        //查出所有的题目
        List<Question> questionList = questionMapper.selectQuestionByEid(eid);
        //将学生答案拆分
        String[] split = answer.split(",");
        //记录比对结果
        Map<Integer, String> result = null;
        Integer score = 0;
        //for循环比对题目答案和学生答案
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getAnswer().equals(split[i])){
                //答对了
                score+=1;
                result.put(i,"true");
            }else {
                //答错了
                result.put(i,"false");
            }
        }
        //将学生测评成绩存入数据库
        questionMapper.insertStudentScore(sid,eid,score);
        return result;
    }

    public void insertScore(Integer sid, Integer eid, String score) {
        questionMapper.insertStudentScore(sid,eid,Integer.parseInt(score));
    }

    public boolean loginCheck(String userName,String password){
        int i = studentMapper.loginCheck(userName, password);
        return i>0;
    }

    //根据账号查询个人资料
    public Student selectByStudentName(String userName){
        return studentMapper.selectByStudentName(userName);
    }

    //修改个人资料
    public void updateStudent(String sname, String sex, Integer age, String email,String phoneNumber,String userName)
    {
        studentMapper.updateStudent(sname,sex,age,email,phoneNumber,userName);
    }

    //修改密码
    public void updatePassword(String password,String userName)
    {
        studentMapper.updatePassword(password,userName);
    }

    //注册功能
    public void loginIn(String userName,String password){
        studentMapper.loginIn(userName,password);
    }



}
