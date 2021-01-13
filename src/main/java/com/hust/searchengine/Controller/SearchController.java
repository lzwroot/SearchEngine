package com.hust.searchengine.Controller;

import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Entity.Student;
import com.hust.searchengine.Entity.User;
import com.hust.searchengine.Service.ClassInfoService;
import com.hust.searchengine.Service.SearchService;
import com.hust.searchengine.Utils.FileUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {
// http://localhost:8080/search
    @Autowired
    private SearchService searchService;

    @Autowired
    private ClassInfoService classInfoService;

    @RequestMapping("main")
    public String MainPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "main";
        else
            return "redirect:/search/login";
    }

    @RequestMapping("advancedsearch")
    public String AdvancedSearchPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "advancedsearch";
        else
            return "redirect:/search/login";
    }

    @RequestMapping("bookmark")
    public String BookmarkPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "bookmark";
        else
            return "redirect:/search/login";
    }

    //登出
    @RequestMapping("logout")
    public String LogoutPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            session.removeAttribute("user");
        }
        return "redirect:/search/login";
    }

    //我的
    @RequestMapping("mine")
    public String MinePage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "mine";
        else
            return "redirect:/search/login";
    }

    //个人主页
    @RequestMapping("profile")
    public String ProfilePage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "profile";
        else
            return "redirect:/search/login";
    }

    //搜索
    @RequestMapping("search")
    public String SearchPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "search";
        else
            return "redirect:/search/login";
    }

    //设置
    @RequestMapping("settings")
    public String SettingsPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "settings";
        else
            return "redirect:/search/login";
    }

    //订阅
    @RequestMapping("subscription")
    public String SubscriptionPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "subscription";
        else
            return "redirect:/search/login";
    }

    //热搜
    @RequestMapping("trends")
    public String TrendsPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null)
            return "trends";
        else
            return "redirect:/search/login";
    }

    //登录界面
    @RequestMapping("login")
    public String loginPage(HttpSession session){
        User user = (User)session.getAttribute("user");

        if(user!=null)
            return "redirect:/search/main";
        return "login";
    }

    //登录界面提交信息
    @PostMapping("login")
    public String loginInfo(@RequestParam("username") String uName,
                            @RequestParam("password") String password,
                            HttpSession session){
        User user = searchService.UserLogin(uName, password);
        session.setMaxInactiveInterval(3600); //括号内数字单位是秒，表示登录的持续时间
        if(user!=null){
            session.setAttribute("user", user );
            return "redirect:/search/main";
        }else{
            return "redirect:/search/login";
        }
    }

    // 学生管理系统
    @RequestMapping("list")
    public String getStudentByIDStuid(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                      @RequestParam(value="clsid", defaultValue = "0") Integer clsid,
                                      @RequestParam(value="stu_name", defaultValue = "") String stu_name,
                                      Model model){
        List<Student> studentList = null;
        PageInfo<Student> studentPageInfo = null;

        if(clsid == 0 && stu_name.isEmpty()){
            studentPageInfo = searchService.findAllStudent(pageIndex, pageSize);
        }
        else if(clsid == 0 && !stu_name.isEmpty()){
            studentPageInfo = searchService.findStudentByStuName(pageIndex, pageSize, stu_name);
        }
        else{
            studentPageInfo = searchService.findStudentByClsIDStuName(pageIndex, pageSize, clsid, stu_name);
        }

        List<ClassInfo> classes = classInfoService.findAllClassInfo();

        model.addAttribute("cls", classes);
        model.addAttribute("stus", studentPageInfo);

        model.addAttribute("clsid", clsid);
        model.addAttribute("stu_name", stu_name);
        return "system";
    }

    // 添加学生信息页面
    @GetMapping("add")
    public String addStudentInfo(Model model){
        List<ClassInfo> classes = classInfoService.findAllClassInfo();
        model.addAttribute("cls", classes);
        return "addinfo"; // addinfo.html
    }

    // 添加学生信息提交
    @PostMapping("add")
    public String addStudentInfo(Student student, @RequestParam("filepic") MultipartFile file){
        // 1.保存文件到硬盘上
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath();
        fileName = System.currentTimeMillis()+fileName;

        try {
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2.保存文件名到数据库里
        student.setStu_image_url(fileName);
        searchService.addStudentInfo(student);
        return "redirect:/stu/list";
    }

    // 修改学生信息页面
    @GetMapping("update/{id}")
    public String updateStudent(@PathVariable("id") Integer stuid, Model model){
        Student student = searchService.findStudentByID(stuid);
        List<ClassInfo> classes = classInfoService.findAllClassInfo();
        model.addAttribute("cls", classes);
        model.addAttribute("stu",student);
        return "updateinfo";
    }

    // 修改学生信息提交
    @PostMapping("update")
    public String updateStudent(Student student, @RequestParam("filepic") MultipartFile file){
        // 1.保存文件到硬盘上
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath();
        fileName = System.currentTimeMillis()+fileName;

        try {
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2.保存文件名到数据库里
        student.setStu_image_url(fileName);
        searchService.updateStudentByID(student);
        return "redirect:/stu/list";
    }

    // 删除学生信息
    @GetMapping("list/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer stuid,
                                @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                Model model){
        searchService.deleteStudentByID(stuid);
        PageInfo<Student> stuLists = searchService.findAllStudent(pageIndex, pageSize);
        model.addAttribute("stus",stuLists);
        return "redirect:/stu/list";
    }
}