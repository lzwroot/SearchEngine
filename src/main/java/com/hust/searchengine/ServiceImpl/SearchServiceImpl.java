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
import java.util.Date;
import java.util.List;
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
        String command = "cd search/search\npython test.py";
        Process pythonProcess = Runtime.getRuntime().exec(command);
        ProcessIOTransport piot1 = new ProcessIOTransport(command,200,keywords);
        //将后台返回的json数据转化成list
        String jsonString = "[{\"name\":\"zhangsan\",\"password\":\"zhangsan123\",\"email\":\"10371443@qq.com\"}"
             + ",{\"name\":\"lisi\",\"password\":\"lisi123\",\"email\":\"1435123@qq.com\"}]";
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Article> list = JSONObject.parseArray(jsonString, Article.class);
        //JSONObject json = JSONObject.fromObject(str);

        //开启python后台进程
        //List<Article> lists = BKGroundThread.GetArticleByPythonScriptThread(keywords);

        //List<Article> lists = senate(keywords);
        List<Article> lists = searchMapper.findArticleByKeywords(keywords);

        return new PageInfo<>(lists);
    }

//    public List<Article> senate(String data) {
//        String ip = "localhost";
//        int port = 9999;
//        Socket socket = null;
//        try {
//            socket = new Socket(ip, port);
//            InputStream in = socket.getInputStream();
//            OutputStream out = socket.getOutputStream();
//            BufferedReader inRead = new BufferedReader(new InputStreamReader(in));
//
//            out.write(data.getBytes(StandardCharsets.UTF_8));
//
//            String result = inRead.readLine();
//            for(String i:result.split("\\!",-1)){
//                for(String j : i.split("\\|",-1)){
//                    System.out.println(j);
//                }
//            }
//            return result;
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
//        return "defeat";
//    }
}



@Async
class BKGroundThread{
    @PostConstruct
    public static List<Article> GetArticleByPythonScriptThread(String keywords){
        PyList articleList = new PyList();
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("src\\main\\java\\myDemo\\1.py");
        PyFunction pyFunction = interpreter.get("mine", PyFunction.class);
        PyObject pyobj = pyFunction.__call__(new PyInteger(1), new PyInteger(2));
        boolean isPyList = pyobj instanceof PyList;
        if(isPyList) {
            articleList = (PyList)pyobj;
            boolean isSubPyList = true;
            for (Object o : articleList) {
                PyList articleInfo = (PyList) o;
                for (int j = 0; j < articleInfo.size(); j++) {

                }
            }
        }
        return articleList;
    }
}


class StreamThreadRead extends Thread{
    //存储读取的字符串
    private final StringBuffer sBuffer;
    //对应的输入流
    private final InputStream iStream;
    public StreamThreadRead(InputStream iStream){
        super();
        sBuffer = new StringBuffer();
        this.iStream = iStream;
    }
    @Override
    public void run() {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
        String str;
        try {
            while ((str = bReader.readLine()) != null) {
                sBuffer.append(str).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public StringBuffer getsBuffer() {
        return sBuffer;
    }
    public InputStream getiStream() {
        return iStream;
    }
}

/**
 * 进程数据传输对象
 */
class ProcessIOTransport {
    private final BufferedWriter stout;
    private final StreamThreadRead stin;
    private final StreamThreadRead errin;
    private final Process process;

    /**
     * 构造一个ProcessIOTransport
     * @param command
     * @throws IOException
     */
    public ProcessIOTransport(String command) throws IOException{
        process = Runtime.getRuntime().exec(command);
        stout = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        stin = new StreamThreadRead(process.getInputStream());
        errin = new StreamThreadRead(process.getErrorStream());
        //设为后台线程
        stin.setDaemon(true);
        errin.setDaemon(true);
        //启动线程
        stin.start();
        errin.start();
        //监控进程
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(stin.isAlive()||errin.isAlive()){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                process.destroy();
            }
        });
    }
    /**
     * 构造一个ProcessIOTransport,其中向进程中输入参数args
     * @param command
     * @param args
     * @throws IOException
     */
    public ProcessIOTransport(String command,String...args) throws IOException{
        this(command);
        write(args);
    }
    /**
     * 构造一个ProcessIOTransport,当前java线程等待进程运行结束
     * @param command
     * @param timeOut 最迟等待时间(ms)
     * @throws IOException
     */
    public ProcessIOTransport(String command,long timeOut) throws IOException{
        this(command,timeOut,new String[]{null});
    }
    /**
     * 构造一个ProcessIOTransport,当前java线程等待进程运行结束
     * @param command
     * @param timeOut 最迟等待时间(ms)
     * @param args 向进程中传入的参数
     * @throws IOException
     */
    public ProcessIOTransport(String command,long timeOut,String...args) throws IOException{
        this(command,args);
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while(now-start<timeOut&&(stin.isAlive()||errin.isAlive())){
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            now = System.currentTimeMillis();
        }
    }

    /**
     * 向process写数据
     * @param args
     * @throws IOException
     */
    public void write(String...args) throws IOException {
        for(int i = 0;i<args.length;i++){
            if(args[i]!=null){
                stout.write(args[i]);
                stout.flush();
            }
        }
    }
    /**
     * 读取process的标准输出
     * @return
     */
    public String readStandard(){
        if(stin.isAlive()){
            return null;
        }else{
            return stin.getsBuffer().toString();
        }
    }
    /**
     * 读取process的错误输出
     * @return
     */
    public String readError(){
        if(stin.isAlive()){
            return null;
        }else{
            return stin.getsBuffer().toString();
        }
    }
}