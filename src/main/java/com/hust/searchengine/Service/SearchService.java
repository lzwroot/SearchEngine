package com.hust.searchengine.Service;

import com.hust.searchengine.Entity.*;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<Journal> findAllSubJournalsByUsernameNotPaged(String username);

    List<Article> findArticleByJournalNotPaged(String journal);

    Integer InsertBookmark(String username, String doi);

    Bookmark findBookmark();

    Integer deleteBookMark(String username, String doi);

    //此处以下的代码可忽略，但不要删除！
    PageInfo<Student> findAllStudent(Integer pageIndex, Integer pageSize);

    Integer addStudentInfo(Student student);

    PageInfo<Student> findStudentByClsIDStuName(Integer pageIndex, Integer pageSize, Integer clsid, String stu_name);

    PageInfo<Student> findStudentByStuName(Integer pageIndex, Integer pageSize, String stu_name);

    Student findStudentByID(Integer stuid);

    Integer updateStudentByID(Student student);

    Integer deleteStudentByID(Integer stuid);
}
