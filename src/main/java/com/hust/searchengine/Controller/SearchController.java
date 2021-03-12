package com.hust.searchengine.Controller;

import com.hust.searchengine.Entity.*;
import com.hust.searchengine.Service.ManagerService;
import com.hust.searchengine.Service.SearchService;
import com.hust.searchengine.Utils.FileUtil;
import com.github.pagehelper.PageInfo;
import org.python.antlr.op.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("search")
@CrossOrigin
public class SearchController {
// http://localhost:8080/search
    @Autowired
    private SearchService searchService;

    @Autowired
    private ManagerService managerService;

    //主页
    @RequestMapping("main")
    public String MainPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            return "main";
        }
        else {
            return "main_beforelogin";
           //return "redirect:/search/login";
        }
    }

    //404页面
    @RequestMapping("**")
    public String Page_404(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            return "404_page";
        }
        else {
            return "404_page_beforelogin";
        }
    }

    //401页面
    @RequestMapping("401_page")
    public String Page_401(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            return "401_page";
        }
        else {
            return "redirect:/search/login";
        }
    }

    //403页面
    @RequestMapping("403_page")
    public String Page_403(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            return "403_page";
        }
        else {
            return "redirect:/search/login";
        }
    }

    //个人反馈页
    @GetMapping("feedback")
    public String FeedbackPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            return "feedback";
        }
        return "redirect:/search/login";
    }

    //提交个人反馈
    @PostMapping("feedback")
    public String FeedbackPost(@RequestParam(value = "feedback",defaultValue = "")String feedback,
                               HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            if(feedback==null || feedback.equals("")) {
               return "redirect:/search/feedback";
            }
            String username = user.getUsername();
            searchService.postFeedback(username, feedback);
            return "redirect:/search/feedback_history";
        }
        return "redirect:/search/login";
    }

    //查看个人反馈历史
    @RequestMapping("feedback_history")
    public String FeedbackHistoryPage(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            String username = user.getUsername();
            List<Feedback> feedbacks = searchService.getFeedbackHistory(username);
            Comparator<Feedback> feedbackComparator = (f1, f2) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return sdf.parse(f2.getFeedback_time()).compareTo(sdf.parse(f1.getFeedback_time()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 1;
            };
            model.addAttribute("comparator", feedbackComparator);
            model.addAttribute("feedbacks", feedbacks);
            return "feedback_history";
        }
        return "redirect:/search/login";
    }

    @RequestMapping("delete_feedback")
    public String DeleteFeedbackRecord(@RequestParam("feedback_id") Integer feedback_id,
                                       HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user!=null){
            searchService.deleteFeedbackRecord(feedback_id);
            return "redirect:/search/feedback_history";
        }
        return "redirect:/search/login";
    }

    //高级搜索
    @GetMapping("advancedsearch")
    public String AdvancedSearchPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "advancedsearch";
        else
            return "advancedsearch_beforelogin";
    }


    @RequestMapping("advancedsearch_result")
    public String AdvancedSearchResultPage(@RequestParam(value = "type1", defaultValue = "articlename") String type1,
                                           @RequestParam(value = "keyword1", defaultValue = "") String keyword1,
                                           @RequestParam(value = "selector1", defaultValue = "and") String selector1,
                                           @RequestParam(value = "type2", defaultValue = "author") String type2,
                                           @RequestParam(value = "keyword2", defaultValue = "") String keyword2,
                                           @RequestParam(value = "selector2", defaultValue = "and") String selector2,
                                           @RequestParam(value = "type3", defaultValue = "journal") String type3,
                                           @RequestParam(value = "keyword3", defaultValue = "") String keyword3,
                                           @RequestParam(value = "start_date", defaultValue = "") String start_date,
                                           @RequestParam(value = "end_date", defaultValue = "") String end_date,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                           @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                                           HttpSession session, Model model){
        if(keyword1.equals("")&&keyword2.equals("")&&keyword3.equals("")&&file==null)
            return "redirect:/search/advancedsearch";

        if(file==null || file.isEmpty()){
            PageInfo<Article> advancedList = searchService.advancedSearchByVariableConditions(type1,keyword1,
                    selector1,type2,keyword2,selector2,type3,keyword3,start_date,end_date, pageIndex, pageSize);

            model.addAttribute("results", advancedList);

            Map<String, String> attrs = Map.of("type1",type1,"keyword1",keyword1,"selector1",selector1,"type2",type2,"keyword2", keyword2, "selector2", selector2, "type3", type3, "keyword3", keyword3, "start_date", start_date, "end_date", end_date);
            model.addAllAttributes(attrs);
        }

        User user = (User)session.getAttribute("user");
        if(user!=null) {
            if(!file.isEmpty()){// 1.保存文件到硬盘上
                String fileName = file.getOriginalFilename();
                String filePath;
                if(fileName!=null&&!fileName.isEmpty()){
                    if(FileUtil.isPicture(fileName))
                        filePath = FileUtil.getUpLoadPicPath();
                    else
                        filePath = FileUtil.getUpLoadFilePath();

                    fileName = System.currentTimeMillis() + fileName; //文件名为fileName

                    try {
                        FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 2.根据文件名找到相应文件
                // 然后使用python脚本对文件进行分析操作
            }

            return "advancedsearch_result";
        }
        else
            return "advancedsearch_result_beforelogin";
    }

    @RequestMapping("article/{doi}")
    public String ArticlePage(@PathVariable("doi") String doi,
                              HttpSession session, Model model){
        doi = doi.replace('_','/');
        User user = (User)session.getAttribute("user");
        if (doi!=null && !doi.equals("null") && !doi.equals("")) {
            Article article = searchService.findArticleByDoi(doi);
            model.addAttribute("article", article);
            if(user!=null){
                String username = user.getUsername();
                if(searchService.addHistory(username, doi)==0)
                    searchService.updateHistoryRecord(username, doi);
                return "article";
            }else{
                return "article_beforelogin";
            }
        }else return "redirect:/search/Article_404_not_found";
    }

    //书签
    @GetMapping("bookmark")
    public String BookmarkPage(Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            List<Article> bookmarks = searchService.findAllBookmarks(user.getUsername());
            model.addAttribute("bookmarks", bookmarks);
            model.addAttribute("msg", "");
            model.addAttribute("color", "red");
            return "bookmark";
        }
        else
            return "redirect:/search/login";

    }

    //插入书签
    @PostMapping("bookmark")
    public String InsertBookmark(@RequestParam("doi") String doi,
                                 /*@RequestParam("keywords") String keywords,*/
                                 HttpServletRequest request,
                                 HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            String username = user.getUsername();
            String reqUrl = request.getHeader("Referer");
            String host = request.getRequestURL().toString().replaceAll(request.getRequestURI().toString(),"");
            String resourceUrl = reqUrl.replaceAll(host, "");
            String message, color;
            boolean already_added = searchService.InsertBookmark(username, doi)==0;
            if(already_added) {
                message = "您已添加过该书签，添加书签失败！";
                color = "red";
            }else{
                message = "添加书签成功！";
                color = "green";
            }
            List<Article> bookmarks = searchService.findAllBookmarks(user.getUsername());
            model.addAttribute("bookmarks", bookmarks);
            model.addAttribute("color", color);
            model.addAttribute("msg", message);
            return "bookmark";
        }else
            return "redirect:/search/login";
    }

    //删除书签
    @GetMapping("bookmark/delete/{doi}")
    public String DeleteBookmark(@PathVariable("doi")String doi,
                                 HttpSession session){
        doi = doi.replace('_','/');
        User user = (User)session.getAttribute("user");
        if(user!=null){
            String username = user.getUsername();
            searchService.deleteBookMark(username, doi);
            return "redirect:/search/bookmark";
        }
        return "redirect:/search/login";
    }

    //查找该领域下所有文章
    @GetMapping("field/{field}")
    public String FieldPage(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                 @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                                 @PathVariable("field")String field,
                                 HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message = "", color = "";
        PageInfo<Article> articles = searchService.findArticleByField(pageIndex, pageSize, field);
        model.addAttribute("field", field);
        model.addAttribute("results", articles);
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        if(user!=null){
            return "field";
        }else{
            return "field_beforelogin";
        }
    }

    //添加订阅领域
    @PostMapping("field/{field}")
    public String AddFieldPage(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                            @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                            @PathVariable("field")String field,
                            HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message, color;

        PageInfo<Article> articles = searchService.findArticleByField(pageIndex, pageSize, field);

        model.addAttribute("field", field);
        model.addAttribute("results", articles);
        if(user!=null){
            String username = user.getUsername();
            if(searchService.insertField(username, field) > 0){
                message = "成功订阅该领域！";
                color = "green";
            }else{
                message = "添加订阅领域失败，已添加过订阅！";
                color = "red";
            }
            model.addAttribute("msg", message);
            model.addAttribute("color", color);
            return "field";
        }else {
            return "redirect:/search/login";
        }
    }

    //删除订阅领域
    @PostMapping("delete/field/{field}")
    public String DeleteField(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                               @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                               @PathVariable("field")String field,
                               HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message, color;

        PageInfo<Article> articles = searchService.findArticleByField(pageIndex, pageSize, field);

        model.addAttribute("field", field);
        model.addAttribute("results", articles);
        if(user!=null){
            String username = user.getUsername();
            if(searchService.deleteField(username, field) > 0){
                message = "成功取消订阅该领域！";
                color = "green";
            }else{
                message = "您未订阅过该领域！";
                color = "red";
            }
            model.addAttribute("msg", message);
            model.addAttribute("color", color);
            return "field";
        }else {
            return "redirect:/search/login";
        }
    }

    //查找该作者的所有文章
    @GetMapping("author/{author}")
    public String AuthorPage(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                            @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                            @PathVariable("author")String author,
                            HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message = "", color = "";
        PageInfo<Article> articles = searchService.findArticleByAuthor(pageIndex, pageSize, author);
        model.addAttribute("author", author);
        model.addAttribute("results", articles);
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        if(user!=null){
            return "author";
        }else{
            return "author_beforelogin";
        }
    }

    @PostMapping("author/{author}")
    public String AddAuthorPage(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                               @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                               @PathVariable("author")String author,
                               HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message, color;

        PageInfo<Article> articles = searchService.findArticleByAuthor(pageIndex, pageSize, author);

        model.addAttribute("author", author);
        model.addAttribute("results", articles);
        if(user!=null){
            String username = user.getUsername();
            if(searchService.insertAuthor(username, author) > 0){
                message = "成功订阅该作者！";
                color = "green";
            }else{
                message = "添加订阅作者失败，已添加过订阅！";
                color = "red";
            }
            model.addAttribute("msg", message);
            model.addAttribute("color", color);
            return "author";
        }else {
            return "redirect:/search/login";
        }
    }

    //删除订阅作者
    @PostMapping("delete/author/{author}")
    public String DeleteAuthor(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                              @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                              @PathVariable("author")String author,
                              HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message, color;

        PageInfo<Article> articles = searchService.findArticleByAuthor(pageIndex, pageSize, author);

        model.addAttribute("author", author);
        model.addAttribute("results", articles);
        if(user!=null){
            String username = user.getUsername();
            if(searchService.deleteAuthor(username, author) > 0){
                message = "成功取消订阅该作者！";
                color = "green";
            }else{
                message = "您未订阅过该作者！";
                color = "red";
            }
            model.addAttribute("msg", message);
            model.addAttribute("color", color);
            return "author";
        }else {
            return "redirect:/search/login";
        }
    }

    //登出
    @GetMapping("logout")
    public String LogoutPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            session.removeAttribute("user");
        }
        return "redirect:/search/main";
    }

    //我的(包括订阅的作者、领域、期刊)
    @RequestMapping("mine")
    public String MinePage(@RequestParam(value = "pageIndex1",defaultValue = "1") Integer pageIndex1,
                           @RequestParam(value = "pageSize1",defaultValue = "5") Integer pageSize1,
                           @RequestParam(value = "pageIndex2",defaultValue = "1") Integer pageIndex2,
                           @RequestParam(value = "pageSize2",defaultValue = "5") Integer pageSize2,
                           @RequestParam(value = "pageIndex3",defaultValue = "1") Integer pageIndex3,
                           @RequestParam(value = "pageSize3",defaultValue = "5") Integer pageSize3,
                           HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            String username = user.getUsername();
            PageInfo<Author> authorPageInfo = searchService.findAllSubAuthorsByUsername(pageIndex1,pageSize1,username);
            PageInfo<Field> fieldPageInfo = searchService.findAllSubFieldsByUsername(pageIndex2,pageSize2,username);
            PageInfo<Journal> journalPageInfo = searchService.findAllSubJournalsByUsername(pageIndex3,pageSize3,username);
            model.addAttribute("authors",authorPageInfo);
            model.addAttribute("fields",fieldPageInfo);
            model.addAttribute("journals",journalPageInfo);
            return "mine";
        }
        else
            return "redirect:/search/login";
    }

    //个人主页
    @RequestMapping("profile")
    public String ProfilePage(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            model.addAttribute("user",user);
            return "profile";
        }
        else
            return "redirect:/search/login";
    }

    //搜索结果
    @RequestMapping("search")
    public String SearchPage(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                             @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                             @RequestParam(value = "keywords",defaultValue = "") String keywords,
                             HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        String message = "";
        String color = "red";
        if(!keywords.isEmpty()) {
            PageInfo<Article> results = searchService.findArticleByKeywords(pageIndex, pageSize, keywords);

            model.addAttribute("results", results);
            model.addAttribute("keywords", keywords);
            model.addAttribute("msg", message);
            model.addAttribute("color", color);

            if(user!=null) {
                return "search";
            }else{
                return "search_beforelogin";
            }
        }else{
            return "redirect:/search/main";
        }
    }

    //修改用户信息页面
    @GetMapping("settings")
    public String SettingsPage(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            String message = "";
            model.addAttribute("user", user);
            model.addAttribute("msg", message);
            return "settings";
        }
        else
            return "redirect:/search/login";
    }

    // 修改用户信息提交
    @PostMapping("settings")
    public String updateUserinfo(HttpSession session,
                                 @RequestParam("passwordconfirmed") String password,
                                 @RequestParam("filepic") MultipartFile file,
                                 User userupdated, Model model){

        User user = (User)session.getAttribute("user");
        if(user!=null) {
            //判断输入的密码是否正确
            if(user.getPassword().equals(password)){
                String oldusername = user.getUsername();
                String old_url = user.getImage_url();
                String newu = userupdated.getUsername();
                String newp = userupdated.getPassword();
                boolean news = userupdated.isSex();
                String newi = userupdated.getInstitution();
                String newe = userupdated.getEmail();
                String new_url = null;
                if(!file.isEmpty()){// 1.保存文件到硬盘上
                    String fileName = file.getOriginalFilename();
                    String filePath;
                    if(fileName!=null&&!fileName.isEmpty()){
                        if(FileUtil.isPicture(fileName))
                            filePath = FileUtil.getUpLoadPicPath();
                        else
                            filePath = FileUtil.getUpLoadFilePath();

                        fileName = System.currentTimeMillis() + fileName; //文件名为fileName

                        try {
                            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new_url = fileName;
                    }
                }else{
                    new_url = old_url;
                }
                userupdated.setImage_url(new_url);

                String message = "";
                //判断数据库内是否已存在相应用户名
                if(!oldusername.equals(newu)&&newu.equals(searchService.FindIfUsernameAlreadyExists(newu))){
                    message = "修改用户名失败，已存在此用户名！";
                    model.addAttribute("msg", message);
                    return "settings";
                }
                // 保存到数据库里
                searchService.updateUserByUsername(oldusername, newu, newp, news, newi, newe, new_url);
                //重新设置用户session，以新修改的username显示在右上角
                session.removeAttribute("user");
                session.setAttribute("user", userupdated);

                model.addAttribute("msg", message);
                return "redirect:/search/profile";
            }else{
                String message = "密码错误，请重新输入！";
                model.addAttribute("msg", message);
                return "settings";
            }
        }
        return "redirect:/search/login";
    }

    //订阅
    @RequestMapping("subscription")
    public String SubscriptionPage(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                   @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "activejournal", defaultValue = "Nature") String activeJournal,
                                   HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            String username = user.getUsername();
            List<Journal> subs = searchService.findAllSubJournalsByUsernameNotPaged(username);
            Map<Journal, PageInfo<Article>> subs_arts = new HashMap<>();
            for (Journal sub : subs) {
                String journal = sub.getJournal();
                PageInfo<Article> articlesOnSub = searchService.findArticleByJournal(pageIndex, pageSize, journal);
                subs_arts.put(sub, articlesOnSub);
            }
            model.addAttribute("subs", subs);
            model.addAttribute("activejournal", activeJournal);
            model.addAttribute("subs_arts", subs_arts);
            return "subscription";
        }
        else
            return "redirect:/search/login";
    }

    //热搜
    @GetMapping("history")
    public String HistoryPage(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            String username = user.getUsername();
            List<Article> histories = searchService.getAllHistory(username);
            model.addAttribute("histories", histories);
            return "history";
        }
        else
            return "redirect:/search/login";
    }

    //热搜
    @GetMapping("history/delete/{doi}")
    public String DeleteHistory(@PathVariable("doi")String doi,
                                HttpSession session){
        User user = (User)session.getAttribute("user");
        doi = doi.replace('_','/');
        if(user!=null){
            String username = user.getUsername();
            searchService.deleteHistory(username, doi);
            return "redirect:/search/history";
        }
        return "redirect:/search/login";
    }

    //登录界面
    @RequestMapping("login")
    public String loginPage(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            return "redirect:/search/main";
        }
        String message = "";
        model.addAttribute("msg", message);
        return "login";
    }

    //登录界面提交
    @PostMapping("login")
    public String loginInfo(@RequestParam("username") String uName,
                            @RequestParam("password") String password,
                            HttpSession session, Model model){
        User user = searchService.UserLogin(uName, password);
        session.setMaxInactiveInterval(3600); //括号内数字单位是秒，表示登录的持续时间
        if(user!=null){
            session.setAttribute("user", user);
            return "redirect:/search/main";
        }else{
            String message = "用户名或者密码错误！";
            model.addAttribute("msg", message);
            return "login";
        }
    }

    //注册界面
    @RequestMapping("signup")
    public String signupPage(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            return "redirect:/search/main";
        }
        model.getAttribute("msg");
        model.getAttribute("color");
        return "signup";
    }

    //注册界面提交
    @PostMapping("signup")
    public String signupInfo(@RequestParam("email") String email,
                             @RequestParam("username") String uName,
                             @RequestParam("password") String password,
                             @RequestParam("password2") String password2,
                             HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            return "redirect:/search/main";
        }else{
            String color = "red";
            String message = "";
            //判断两次密码是否相同
            if(!password.equals(password2)){
                //密码不相同
                message = "两次密码不匹配！";//注册失败
            }
            else{
                //密码相同
                User newuser = searchService.newUserSignup(email, uName, password);
                boolean userExists = newuser == null;
                //判断用户名是否存在
                if(userExists){
                    message = "用户名已存在，请返回登录界面登录！";//注册失败
                }else{
                     //新用户信息写入数据库
                    session.setAttribute("user", newuser);
                    return "redirect:/search/main";//离开本页面
                }
            }
            model.addAttribute("msg", message);
            model.addAttribute("color", color);
            return "signup"; //带着消息回到注册页面
        }
    }

    // 学生管理系统
//    @RequestMapping("list")
//    public String getStudentByIDStuid(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
//                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
//                                      @RequestParam(value="clsid", defaultValue = "0") Integer clsid,
//                                      @RequestParam(value="stu_name", defaultValue = "") String stu_name,
//                                      Model model){
//        List<Student> studentList = null;
//        PageInfo<Student> studentPageInfo = null;
//
//        if(clsid == 0 && stu_name.isEmpty()){
//            studentPageInfo = searchService.findAllStudent(pageIndex, pageSize);
//        }
//        else if(clsid == 0 && !stu_name.isEmpty()){
//            studentPageInfo = searchService.findStudentByStuName(pageIndex, pageSize, stu_name);
//        }
//        else{
//            studentPageInfo = searchService.findStudentByClsIDStuName(pageIndex, pageSize, clsid, stu_name);
//        }
//
//        List<ClassInfo> classes = managerService.findAllClassInfo();
//
//        model.addAttribute("cls", classes);
//        model.addAttribute("stus", studentPageInfo);
//
//        model.addAttribute("clsid", clsid);
//        model.addAttribute("stu_name", stu_name);
//        return "system";
//    }
//
//    // 添加学生信息提交
//    @PostMapping("add")
//    public String addStudentInfo(Student student, @RequestParam("filepic") MultipartFile file){
//        // 1.保存文件到硬盘上
//        String fileName = file.getOriginalFilename();
//        String filePath = FileUtil.getUpLoadFilePath();
//        fileName = System.currentTimeMillis()+fileName;
//
//        try {
//            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 2.保存文件名到数据库里
//        student.setStu_image_url(fileName);
//        searchService.addStudentInfo(student);
//        return "redirect:/stu/list";
//    }

//    // 修改学生信息页面
//    @GetMapping("update/{id}")
//    public String updateStudent(@PathVariable("id") Integer stuid, Model model){
//        Student student = searchService.findStudentByID(stuid);
//        List<ClassInfo> classes = classInfoService.findAllClassInfo();
//        model.addAttribute("cls", classes);
//        model.addAttribute("stu",student);
//        return "updateinfo";
//    }

//    // 修改学生信息提交
//    @PostMapping("update")
//    public String updateStudent(Student student, @RequestParam("filepic") MultipartFile file){
//        // 1.保存文件到硬盘上
//        String fileName = file.getOriginalFilename();
//        String filePath = FileUtil.getUpLoadFilePath();
//        fileName = System.currentTimeMillis()+fileName;
//
//        try {
//            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 2.保存文件名到数据库里
//        student.setStu_image_url(fileName);
//        searchService.updateStudentByID(student);
//        return "redirect:/stu/list";
//    }
}