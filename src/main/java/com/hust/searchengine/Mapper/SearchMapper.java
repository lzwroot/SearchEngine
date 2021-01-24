package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SearchMapper {

    //登录模块
    @Select("select * from user where username=#{username} and password=#{password}")
    User UserLogin(String username, String password);

    //根据用户名找到该用户名下收藏的书签
    @Select("select art.*, bmk.username from article art join bookmark bmk on art.doi = bmk.doi where username=#{username}")
    List<Article> findAllBookmarks(String username);

    //根据用户名查找该用户，返回一个用户对象
    @Select("select * from user where username=#{username}")
    User findUserByUsername(String username);

    //根据用户名更新相应的用户
    @Select("update user set username=#{username}, password=#{password}, sex=#{sex}, institution=#{institution}, email=#{email} where username=#{oldusername}")
    Integer updateUserByUsername(@Param("oldusername")String oldusername,
                                 @Param("username") String newusername,
                                 @Param("password") String newpassword,
                                 @Param("sex")boolean newsex,
                                 @Param("institution")String newinstitution,
                                 @Param("email")String newemail);

    @Select("select username from user where username=#{newusername}")
    String FindIfUsernameAlreadyExists(@Param("newusername")String username);

    //注册一个新的用户
    @Insert("insert into user(username, password, email) select #{username}, #{password}, #{email} from dual where not exists (select username from user where username=#{username})")
    Integer newUserSignup(@Param("email") String email,
                          @Param("username")String username,
                          @Param("password")String password);

    //得到热搜榜
    @Select("select * from trends")
    List<Trends> getAllTrends();

    //根据关键词在论文数据库中查找相应的文章，并显示在搜索结果页面
    //！！划重点：此处是调用后台算法接口的地方！！
    @Select("select * from article where articlename like '%${keywords}%'")
    List<Article> findArticleByKeywords(@Param("keywords") String keywords);

    //得到订阅作者表
    @Select("select author from subscribeauthor where username=#{username}")
    List<Author> findAllSubAuthorsByUsername(String username);

    //得到订阅领域表
    @Select("select field from subscribefield where username=#{username}")
    List<Field> findAllSubFieldsByUsername(String username);

    //得到订阅期刊表
    @Select("select journal from subscribejournal where username=#{username}")
    List<Journal> findAllSubJournalsByUsername(String username);

    //根据期刊找该期刊下的文章
    @Select("select * from article where journal=#{journal}")
    List<Article> findArticleByJournal(String journal);

    //添加书签
    @Insert("insert into bookmark(username, doi, insert_time) select #{username}, #{doi}, #{insert_time} from dual where not exists (select username,doi from bookmark where username=#{username} and doi=#{doi})")
    Integer InsertBookmark(String username, String doi, Date insert_time);

    //查找书签是否已添加
    @Select("select * from bookmark where doi=#{doi} and username=#{username}")
    Bookmark findBookmark();

    //删除相应的书签
    @Delete("delete from bookmark where doi=#{doi} and username=#{username}")
    Integer deleteBookMark(String username, String doi);


    //以下代码可以忽略，请暂时不要删除！
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
