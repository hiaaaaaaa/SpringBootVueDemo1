package com.example.springbootvuedemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootvuedemo1.entity.Examination;
import com.example.springbootvuedemo1.entity.SC;
import com.example.springbootvuedemo1.entity.Score;
import com.example.springbootvuedemo1.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    //查询所有学生
    Page<Student> listAll(Page<Student> page);

    //根据姓名查询学生
    List<Student> listOne(Student student);

    //管理员添加学生
    int addUpdate(Student student);

    //管理员删除学生
    int deleteById(int id);

    //管理员修改学生
    int modUpdate(Student student);
    //教师删除学生
    int delByIdInSC(int sid,int cid);

    //教师查看班级学员
    List<Student> selectStudentByCid(Integer cid);

    //教师添加班级学员
    int addStuInSC(SC sc);


    //同意学生申请
    int aggUpdate(SC sc);

    //不同意学生申请
    int disUpdate(SC sc);

    //查询学生申请表
    Page<Student> listReq(Page<Student> page,Integer tid);

    /*
    * 作者：梁伟静
    * */

    @Insert("insert into sc(sid,cid,state) values(#{sid},#{cid},#{state})")
    void insertStudentClass(Integer sid, Integer cid, Integer state);

    @Delete("delete from sc where sid=#{sid} and cid=#{cid}")
    void deleteStudentClass(Integer sid, Integer cid);

    //查询学生成绩以及对应试卷信息，返回值为Score类，其中根据eid返回的examination对象
    @Select("select * from es where sid=#{sid}")
    @Results(
            @Result(property = "examination",column = "eid",
                    javaType= Examination.class,
                    one=@One(select = "com.example.springbootvuedemo1.mapper.ExaminationMapper.selectExaminationById"))
    )
    List<Score> selectScoreBySid(Integer sid);

    /*
    * 作者：万梓欣
    * */

    //登录验证
    Student selectByStudentName(@Param("userName") String userName);

    //查询是否有学生的信息（登录验证）
    @Select("select count(*) from student where userName=#{userName} and password=#{password}")
    int loginCheck(String userName,String password);


    //修改个人资料
    void updateStudent(@Param("sname") String sname,
                       @Param("sex") String sex,
                       @Param("age") Integer age,
                       @Param("email") String email,
                       @Param("phoneNumber") String phoneNumber,
                       @Param("userName") String userName);
    //修改密码
    void updatePassword(@Param("password") String password,
                        @Param("userName") String userName);

    //注册功能
    void loginIn(@Param("userName") String userName,
                 @Param("password") String password,
                 @Param("image") byte[] image);

    //查询账号是否已经注册
    @Select("select * from student where userName=#{userName}")
    List<Student> registerCheck(String userName);

    @Select("SELECT * FROM student WHERE userName=#{userName}")
    Student getImagebase(String userName);

    //修改头像
    void updateImage_Stu(@Param("image") byte[] image,
                         @Param("userName") String userName);
}
