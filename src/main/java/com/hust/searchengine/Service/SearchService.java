package com.hust.searchengine.Service;

import com.hust.searchengine.Entity.*;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface SearchService {
    User UserLogin(String username, String password);

    List<Article> findAllBookmarks(String username);

    User findUserByUsername(String username);

    Integer updateUserByUsername(String oldusername, String newusername, String newpassword, boolean newsex, String newinstitution, String newemail);

    String FindIfUsernameAlreadyExists(@Param("newusername")String username);

    User newUserSignup(String email, String username, String password);

    List<Trends> getAllTrends();

    PageInfo<Article> findArticleByKeywords(Integer pageIndex, Integer pageSize, String keywords);

    PageInfo<Author> findAllSubAuthorsByUsername(Integer pageIndex, Integer pageSize, String username);

    PageInfo<Field> findAllSubFieldsByUsername(Integer pageIndex, Integer pageSize, String username);

    PageInfo<Journal> findAllSubJournalsByUsername(Integer pageIndex, Integer pageSize, String username);

    PageInfo<Article> findArticleByJournal(Integer pageIndex, Integer pageSize, String journal);

    PageInfo<Article> findArticleByField(Integer pageIndex, Integer pageSize, String field);

    Integer insertField(String username, String field);

    Article findArticleByDoi(String doi);

    List<Journal> findAllSubJournalsByUsernameNotPaged(String username);

    List<Article> findArticleByJournalNotPaged(String journal);

    Integer InsertBookmark(String username, String doi);

    Bookmark findBookmark();

    Integer deleteBookMark(String username, String doi);

    PageInfo<Article> advancedSearchByVariableConditions(String type1, String keyword1, String selector1, String type2, String keyword2, String selector2, String type3, String keyword3, String start_date, String end_date, Integer pageIndex, Integer pageSize);

    PageInfo<Article> deepSearchByKeywords(Integer pageIndex, Integer pageSize, String keywords) throws IOException;
}
