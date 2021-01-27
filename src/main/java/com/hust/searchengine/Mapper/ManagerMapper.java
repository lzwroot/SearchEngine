package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Entity.Manager;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManagerMapper {
    @Select("select * from manager where managerid=#{managerid} and password=#{password}")
    Manager managerLogin(Integer managerid, String password);

    //忽略以下代码
    @Select("select * from classinfo")
    List<ClassInfo> findAllClassInfo();

    @Insert("insert into classinfo(clsName,clsStuNum,clsTeacher,createDate) values(#{clsName},#{clsStuNum},#{clsTeacher},#{createDate})")
    Integer addClassInfo(ClassInfo classInfo);

    @Select("select * from classinfo where clsid=#{clsid}")
    ClassInfo findClassByID(Integer clsid);

    @Update("update classinfo set clsName=#{clsName}, clsStuNum=#{clsStuNum}, clsTeacher=#{clsTeacher},createDate=#{createDate} where clsid=#{clsid}")
    Integer updateClassByID(ClassInfo classInfo);

    @Delete("delete from classinfo where clsid=#{clsid}")
    Integer deleteClassByID(Integer clsid);
}
