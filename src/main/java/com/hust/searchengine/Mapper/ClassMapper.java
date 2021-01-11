package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.ClassInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {
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
