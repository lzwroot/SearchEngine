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
    @Select("update user set username=#{username}, image_url=#{image_url}, password=#{password}, sex=#{sex}, institution=#{institution}, email=#{email} where username=#{oldusername}")
    Integer updateUserByUsername(@Param("oldusername")String oldusername,
                                 @Param("username") String newusername,
                                 @Param("password") String newpassword,
                                 @Param("sex")boolean newsex,
                                 @Param("institution")String newinstitution,
                                 @Param("email")String newemail,
                                 @Param("image_url") String image_url);

    @Select("select username from user where username=#{newusername}")
    String FindIfUsernameAlreadyExists(@Param("newusername")String username);

    //注册一个新的用户
    @Insert("insert into user(username, password, email) select #{username}, #{password}, #{email} from dual where not exists (select username from user where username=#{username})")
    Integer newUserSignup(@Param("email") String email,
                          @Param("username")String username,
                          @Param("password")String password);


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

    //删除相应的领域
    @Delete("delete from subscribefield where field=#{field} and username=#{username}")
    Integer deleteField(String username, String field);

    //插入订阅的作者
    @Insert("insert into subscribeauthor(username, author) select #{username}, #{author} from dual where not exists (select username,author from subscribeauthor where username=#{username} and author=#{author})")
    Integer insertAuthor(@Param("username")String username, @Param("author")String author);

    //删除相应的作者
    @Delete("delete from subscribeauthor where author=#{author} and username=#{username}")
    Integer deleteAuthor(String username, String author);

    //得到订阅期刊表
    @Select("select journal from subscribejournal where username=#{username}")
    List<Journal> findAllSubJournalsByUsername(String username);

    //根据期刊找该期刊下的文章
    @Select("select * from article where journal like '%${journal}%'")
    List<Article> findArticleByJournal(String journal);

    //根据领域找到该领域下的文章
    @Select("select * from article where field like '%${field}%'")
    List<Article> findArticleByField(String field);

    //根据作者找到该作者下的文章
    @Select("select * from article where author like '%${author}%'")
    List<Article> findArticleByAuthor(String author);

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

    //得到历史记录
    @Select("select art.*, his.username,his.access_time from article art join history his on art.doi = his.his_doi where username=#{username}")
    List<Article> getAllHistory(String username);

    //添加历史记录
    @Insert("insert into history(username, his_doi, access_time) select #{username}, #{his_doi}, #{access_time} from dual where not exists (select username,his_doi from history where username=#{username} and his_doi=#{his_doi})")
    Integer addHistory(String username, String his_doi, Date access_time);

    //更新历史记录
    @Update("update history set username=#{username}, his_doi=#{his_doi}, access_time=#{access_time} where username=#{username} and his_doi=#{his_doi}")
    Integer updateHistoryRecord(String username, String his_doi, Date access_time);

    //删除历史记录
    @Delete("delete from history where username=#{username} and his_doi=#{his_doi}")
    Integer deleteHistory(String username, String his_doi);

    //添加反馈
    @Insert("insert into feedback(username, feedback, feedback_time) values (#{username}, #{feedback}, #{feedback_time})")
    Integer postFeedback(String username, String feedback, Date feedback_time);

    //删除反馈
    @Delete("delete from feedback where feedback_id=#{feedback_id}")
    Integer deleteFeedbackRecord(Integer feedback_id);

    //得到用户反馈历史
    @Select("select * from feedback where username=#{username}")
    List<Feedback> getFeedbackHistory(String username);
}
