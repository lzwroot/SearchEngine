package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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

    //查询总反馈数
    @Select("select count(*) from feedback")
    Integer totalNumberOfFeedbacks();

    //查询所有用户
    @Select("select * from user")
    List<User> findAllUsers();

    //根据用户名删除用户
    @Delete("delete from user where username=#{username}")
    Integer deleteUserByUsername(String username);

    //添加新的用户
    @Insert("insert into user(username,password,sex,institution,email) values(#{username},#{password},#{sex},#{institution},#{email})")
    Integer addNewUser(User user);

    //查询所有用户反馈
    @Select("select * from feedback")
    List<Feedback> getAllFeedbacks();

    //管理员回应
    @Update("update feedback set managerid=#{managerid}, result=#{result}, result_time=#{result_time} where feedback_id=#{feedback_id}")
    Integer sendResponses(Integer managerid, String result, Integer feedback_id, Date result_time);

    @Select("SELECT journal, COUNT(journal) as count FROM article GROUP BY journal")
    List<JournalCount> getTopJournals();

    @Select("SELECT field, COUNT(field) as count FROM article GROUP BY field order by COUNT(field) desc limit 6")
    List<FieldCount> getTopFields();
}
