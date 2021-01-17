package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.Student;
import com.hust.searchengine.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("select * from user where username=#{username} and password=#{password}")
    User UserLogin(String username, String password);


    @Select("select stu.*, cls.clsName from classinfo cls join student_info stu on cls.clsid=stu.clsid")
    List<Student> findAllStudent();

    @Select("select stu.*, cls.clsName from classinfo cls join student_info stu on cls.clsid=stu.clsid where cls.clsid=#{clsid} and stu.stu_name like '%${stu_name}%'")
    List<Student> findStudentByClsIDStuName(@Param("clsid") Integer clsid, @Param("stu_name") String stu_name);

    @Select("select stu.*, cls.clsName from classinfo cls join student_info stu on cls.clsid=stu.clsid where stu.stu_name like '%${stu_name}%'")
    List<Student> findStudentByStuName(@Param("stu_name") String stu_name);

    @Insert("insert into student_info(stu_name,stu_image_url,stu_sex,password,stu_phone,stu_address,stu_birthday,stu_email,stu_education,stu_interest,my_color,lucky_number,re_mark,create_date,clsid) values(#{stu_name},#{stu_image_url},#{stu_sex},#{password},#{stu_phone},#{stu_address},#{stu_birthday},#{stu_email},#{stu_education},#{stu_interest},#{my_color},#{lucky_number},#{re_mark},#{create_date}, #{clsid})")
    Integer addStudentInfo(Student student);

    @Select("select * from student_info where stuid=#{stuid}")
    Student findStudentByID(Integer stuid);

    @Update("update student_info set stu_name=#{stu_name}, stu_image_url=#{stu_image_url}, stu_sex=#{stu_sex},password=#{password},stu_phone=#{stu_phone}, stu_address=#{stu_address}, stu_birthday=#{stu_birthday}, stu_email=#{stu_email}, stu_education=#{stu_education}, stu_interest=#{stu_interest}, my_color=#{my_color}, lucky_number=#{lucky_number}, re_mark=#{re_mark}, create_date=#{create_date}, clsid=#{clsid} where stuid=#{stuid}")
    Integer updateStudentByID(Student student);

    @Delete("delete from student_info where stuid=#{stuid}")
    Integer deleteStudentByID(Integer stuid);
}
