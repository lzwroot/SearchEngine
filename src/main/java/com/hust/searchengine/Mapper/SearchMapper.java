package com.hust.searchengine.Mapper;

import com.hust.searchengine.Entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Select("select * from article where articlename like '%${keywords}%' or field like '%${keywords}%' or journal like '%${keywords}%' or author like '%${keywords}%'")
    List<Article> findArticleByKeywords(@Param("keywords") String keywords);

    //得到订阅作者表
    @Select("select author from subscribeauthor where username=#{username}")
    List<Author> findAllSubAuthorsByUsername(String username);

    //得到订阅领域表
    @Select("select field from subscribefield where username=#{username}")
    List<Field> findAllSubFieldsByUsername(String username);

    //插入订阅的领域
    @Insert("insert into subscribefield(username, field) select #{username}, #{field} from dual where not exists (select username,field from subscribefield where username=#{username} and field=#{field})")
    Integer insertField(@Param("username")String username, @Param("field")String field);

    //得到订阅期刊表
    @Select("select journal from subscribejournal where username=#{username}")
    List<Journal> findAllSubJournalsByUsername(String username);

    //根据期刊找该期刊下的文章
    @Select("select * from article where journal=#{journal}")
    List<Article> findArticleByJournal(String journal);

    //根据领域找到该领域下的文章
    @Select("select * from article where field=#{field}")
    List<Article> findArticleByField(String field);

    //根据doi找到文章
    @Select("select * from article where doi=#{doi}")
    Article findArticleByDoi(String doi);

    //添加书签
    @Insert("insert into bookmark(username, doi, insert_time) select #{username}, #{doi}, #{insert_time} from dual where not exists (select username,doi from bookmark where username=#{username} and doi=#{doi})")
    Integer InsertBookmark(String username, String doi, Date insert_time);

    //查找书签是否已添加
    @Select("select * from bookmark where doi=#{doi} and username=#{username}")
    Bookmark findBookmark();

    //删除相应的书签
    @Delete("delete from bookmark where doi=#{doi} and username=#{username}")
    Integer deleteBookMark(String username, String doi);

    //根据时间查询
    List<Article> findArticleByKeywordsAndTimeRange(@Param("keywords")String keywords, @Param("start_date")String start_date, @Param("end_date")String end_date);

    //高级搜索
    List<Article> advancedSearchByVariableConditions(@Param("type1") String type1,
                                                     @Param("keyword1") String keyword1,
                                                     @Param("selector1") String selector1,
                                                     @Param("type2") String type2,
                                                     @Param("keyword2") String keyword2,
                                                     @Param("selector2") String selector2,
                                                     @Param("type3") String type3,
                                                     @Param("keyword3") String keyword3,
                                                     @Param("start_date") String start_date,
                                                     @Param("end_date") String end_date);

}
