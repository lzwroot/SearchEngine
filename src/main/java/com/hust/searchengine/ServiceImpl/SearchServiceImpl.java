package com.hust.searchengine.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.hust.searchengine.Entity.*;
import com.hust.searchengine.Mapper.SearchMapper;
import com.hust.searchengine.Service.SearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;


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
        boolean isInserted = searchMapper.newUserSignup(email, username, password) != 0;
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
        //List<Article> lists = searchMapper.findArticleByKeywords(keywords);
        List<Article> lists = senate(keywords);
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
        String orderByDate = "time"+" desc";
        PageHelper.startPage(pageIndex, pageSize, orderByDate);
        List<Article> lists = searchMapper.findArticleByJournal(journal);
        PageInfo<Article> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Article> findArticleByField(Integer pageIndex, Integer pageSize,String field) {
        String orderByDate = "time"+" desc";
        PageHelper.startPage(pageIndex, pageSize, orderByDate);
        List<Article> lists = searchMapper.findArticleByField(field);
        PageInfo<Article> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Article> findArticleByAuthor(Integer pageIndex, Integer pageSize,String author) {
        String orderByDate = "time"+" desc";
        PageHelper.startPage(pageIndex, pageSize, orderByDate);
        List<Article> lists = searchMapper.findArticleByAuthor(author);
        PageInfo<Article> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public Integer insertField(String username, String field) {
        return searchMapper.insertField(username, field);
    }

    @Override
    public Integer insertAuthor(String username, String author) {
        return searchMapper.insertAuthor(username, author);
    }

    @Override
    public Integer deleteField(String username, String field) {
        return searchMapper.deleteField(username, field);
    }

    @Override
    public Integer deleteAuthor(String username, String author) {
        return searchMapper.deleteAuthor(username, author);
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

    @Override
    public PageInfo<Article> advancedSearchByVariableConditions(String type1, String keyword1, String selector1, String type2, String keyword2, String selector2, String type3, String keyword3, String start_date, String end_date, Integer pageIndex, Integer pageSize) {
        String orderByDate = "time"+" desc";
        PageHelper.startPage(pageIndex, pageSize, orderByDate);
        List<Article> lists = searchMapper.advancedSearchByVariableConditions(type1, keyword1, selector1, type2, keyword2, selector2, type3, keyword3, start_date, end_date);
        return new PageInfo<>(lists);
    }

    @Override
    public PageInfo<Article> deepSearchByKeywords(Integer pageIndex, Integer pageSize, String keywords) throws IOException {
        String orderByDate = "time"+" desc"; //按照数据库的time字段排序，desc代表倒序

        PageHelper.startPage(pageIndex, pageSize, orderByDate);

        //Json接收数据模式
        //接收关键词给后台search.py


        //List<Article> lists = senate(keywords);
        List<Article> lists = searchMapper.findArticleByKeywords(keywords);

        return new PageInfo<>(lists);
    }

    public List<Article> senate(String data) {
        String ip = "localhost";
        int port = 9999;
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader inRead = new BufferedReader(new InputStreamReader(in));

            out.write(data.getBytes(StandardCharsets.UTF_8));

            String result = inRead.readLine();
            List<Article> articles = new ArrayList<>();
            for(String i:result.split("\\!",-1)){

                String []articleArray = i.split("\\|",-1);
                Article article = new Article();

                //title+'|'+author+'|'+abstract+'|'+str(time)+'|'+field+'|'+link+'|'+journal
                article.setArticlename(articleArray[0]);
                article.setAuthor(articleArray[1]);
                article.setArticle_abstract(articleArray[2]);
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    article.setTime(sdf.parse(articleArray[3]));
                }catch (Exception ignored){}
                article.setField(articleArray[4]);
                article.setLink(articleArray[5]);
                article.setJournal(articleArray[6]);
                articles.add(article);
            }
            return articles;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
