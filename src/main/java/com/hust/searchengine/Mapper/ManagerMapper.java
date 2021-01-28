package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Entity.Manager;
import com.hust.searchengine.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManagerMapper {
    //管理员登录
    @Select("select * from manager where managerid=#{managerid} and password=#{password}")
    Manager managerLogin(Integer managerid, String password);

    //查询论文总条数
    @Select("select count(*) from article")
    Integer totalNumberOfArticles();

    //查询注册总用户数
    @Select("select count(*) from user")
    Integer totalNumberOfUsers();

    //查询所有用户
    @Select("select * from user")
    List<User> findAllUsers();

    //根据用户名删除用户
    @Delete("delete from user where username=#{username}")
    Integer deleteUserByUsername(String username);

    //添加新的用户
    @Insert("insert into user(username,password,sex,institution,email) values(#{username},#{password},#{sex},#{institution},#{email})")
    Integer addNewUser(User user);

    //忽略以下代码
//    @Select("select * from classinfo")
//    List<ClassInfo> findAllClassInfo();
//
//    @Insert("insert into classinfo(clsName,clsStuNum,clsTeacher,createDate) values(#{clsName},#{clsStuNum},#{clsTeacher},#{createDate})")
//    Integer addClassInfo(ClassInfo classInfo);
//
//    @Select("select * from classinfo where clsid=#{clsid}")
//    ClassInfo findClassByID(Integer clsid);
//
//    @Update("update classinfo set clsName=#{clsName}, clsStuNum=#{clsStuNum}, clsTeacher=#{clsTeacher},createDate=#{createDate} where clsid=#{clsid}")
//    Integer updateClassByID(ClassInfo classInfo);
//
//    @Delete("delete from classinfo where clsid=#{clsid}")
//    Integer deleteClassByID(Integer clsid);
}
