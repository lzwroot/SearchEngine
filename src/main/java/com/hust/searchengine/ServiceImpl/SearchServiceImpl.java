package com.hust.searchengine.ServiceImpl;

import com.hust.searchengine.Entity.*;
import com.hust.searchengine.Mapper.SearchMapper;
import com.hust.searchengine.Service.SearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;


    //
    @Override
    public User UserLogin(String username, String password) {
        return searchMapper.UserLogin(username,password);
    }

    @Override
    public List<Article> findAllBookmarks(String username) {
        return searchMapper.findAllBookmarks(username);
    }

    @Override
    public User findUserByUsername(String username) {
        return searchMapper.findUserByUsername(username);
    }

    @Override
    public Integer updateUserByUsername(String oldusername,
                                        String newusername,
                                        String newpassword,
                                        boolean newsex,
                                        String newinstitution,
                                        String newemail) {
        return searchMapper.updateUserByUsername(oldusername, newusername, newpassword, newsex, newinstitution, newemail);
    }

    @Override
    public String FindIfUsernameAlreadyExists(String username) {
        return searchMapper.FindIfUsernameAlreadyExists(username);
    }

    @Override
    public User newUserSignup(String email, String username, String password) {
        boolean isInserted = searchMapper.newUserSignup(email, username, password)==0?false:true;
        if(isInserted)return new User(username, password, email);
        return null;
    }

    @Override
    public List<Trends> getAllTrends() {
        return searchMapper.getAllTrends();
    }

    @Override
    public PageInfo<Article> findArticleByKeywords(Integer pageIndex, Integer pageSize, String keywords) {

        //按照时间排序，最新的文章出现在最前面
        String orderByDate = "time"+" desc"; //按照数据库的time字段排序，desc代表倒序

        PageHelper.startPage(pageIndex, pageSize, orderByDate);
        List<Article> lists = searchMapper.findArticleByKeywords(keywords);
        PageInfo<Article> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Author> findAllSubAuthorsByUsername(Integer pageIndex, Integer pageSize, String username) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Author> lists = searchMapper.findAllSubAuthorsByUsername(username);
        PageInfo<Author> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Field> findAllSubFieldsByUsername(Integer pageIndex, Integer pageSize, String username) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Field> lists = searchMapper.findAllSubFieldsByUsername(username);
        PageInfo<Field> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Journal> findAllSubJournalsByUsername(Integer pageIndex, Integer pageSize, String username) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Journal> lists = searchMapper.findAllSubJournalsByUsername(username);
        PageInfo<Journal> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Article> findArticleByJournal(Integer pageIndex, Integer pageSize, String journal) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Article> lists = searchMapper.findArticleByJournal(journal);
        PageInfo<Article> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public Article findArticleByDoi(String doi) {
        return searchMapper.findArticleByDoi(doi);
    }

    @Override
    public List<Journal> findAllSubJournalsByUsernameNotPaged(String username) {
        return searchMapper.findAllSubJournalsByUsername(username);
    }

    @Override
    public List<Article> findArticleByJournalNotPaged(String journal) {
        return searchMapper.findArticleByJournal(journal);
    }

    @Override
    public Integer InsertBookmark(String username, String doi) {
        Date insert_time = new Date();
        return searchMapper.InsertBookmark(username, doi, insert_time);
    }

    @Override
    public Bookmark findBookmark() {
        return searchMapper.findBookmark();
    }

    @Override
    public Integer deleteBookMark(String username, String doi) {
        return searchMapper.deleteBookMark(username, doi);
    }

    //此处以下的代码可忽略，但不要删除！
    @Override
    public PageInfo<Student> findAllStudent(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = searchMapper.findAllStudent();
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Student> findStudentByClsIDStuName(Integer pageIndex, Integer pageSize, Integer clsid, String stu_name) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = searchMapper.findStudentByClsIDStuName(clsid, stu_name);
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public Integer addStudentInfo(Student student) {
        return searchMapper.addStudentInfo(student);
    }

    @Override
    public Student findStudentByID(Integer stuid) {
        return searchMapper.findStudentByID(stuid);
    }

    @Override
    public Integer updateStudentByID(Student student) {
        return searchMapper.updateStudentByID(student);
    }

    @Override
    public Integer deleteStudentByID(Integer stuid) {
        return searchMapper.deleteStudentByID(stuid);
    }

    @Override
    public PageInfo<Student> findStudentByStuName(Integer pageIndex, Integer pageSize, String stu_name) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = searchMapper.findStudentByStuName(stu_name);
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }
}
