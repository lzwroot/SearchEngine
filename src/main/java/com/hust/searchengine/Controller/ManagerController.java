package com.hust.searchengine.Controller;

import com.hust.searchengine.Entity.Manager;
import com.hust.searchengine.Entity.User;
import com.hust.searchengine.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("login")
    public String ManagerLoginPage(HttpSession session, Model model){
        Manager manager = (Manager) session.getAttribute("manager");
        if(manager!=null) {
            return "redirect:/manager/dashboard";
        }
        String message = "";
        model.addAttribute("msg", message);
        return "managerlogin";
    }

    @PostMapping("login")
    public String ManagerLoginInfo(@RequestParam("managerid") Integer managerid,
                                   @RequestParam("password") String password,
                                   HttpSession session, Model model){
        Manager manager = managerService.managerLogin(managerid, password);
        session.setMaxInactiveInterval(3600); //括号内数字单位是秒，表示登录的持续时间
        if(manager!=null){
            session.setAttribute("manager", manager);
            return "redirect:/manager/dashboard";
        }else{
            String message = "管理员号或者密码错误！";
            model.addAttribute("msg", message);
            return "managerlogin";
        }
    }

    //登出
    @GetMapping("logout")
    public String LogoutPage(HttpSession session){
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager!=null){
            session.removeAttribute("manager");
        }
        return "redirect:/manager/login";
    }

    @GetMapping("dashboard")
    public String DashboardPage(HttpSession session, Model model){
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager!=null)
            return "manager_dashboard";
        else
            return "redirect:/manager/login";
    }


//    @Autowired
//    private ClassInfoService classInfoService;
//
//    // 班级管理系统
//    @GetMapping("list")
//    public String getAllClasses(Model model){
//
//        List<ClassInfo> classes = classInfoService.findAllClassInfo();
//        model.addAttribute("cls", classes);
//        return "classsystem";
//    }
//
//    // 添加班级信息页面
//    @GetMapping("add")
//    public String addClassInfo(Model model){
//        List<ClassInfo> classes = classInfoService.findAllClassInfo();
//        model.addAttribute("cls", classes);
//        return "addclassinfo"; // addclassinfo.html
//    }
//
//    // 添加班级信息提交
//    @PostMapping("add")
//    public String addClassInfo(ClassInfo classInfo){
//        // 保存到数据库里
//        classInfoService.addClassInfo(classInfo);
//        return "redirect:/cls/list";
//    }
//
//    // 修改班级信息页面
//    @GetMapping("update/{id}")
//    public String updateClass(@PathVariable("id") Integer clsid, Model model){
//        ClassInfo classInfo = classInfoService.findClassByID(clsid);
//        model.addAttribute("cls", classInfo);
//        return "updateclassinfo";
//    }
//
//    // 修改班级信息提交
//    @PostMapping("update")
//    public String updateClass(ClassInfo classInfo){
//        // 保存在数据库里
//        classInfoService.updateClassByID(classInfo);
//        return "redirect:/cls/list";
//    }
//
//    // 删除学生信息
//    @GetMapping("list/delete/{id}")
//    public String deleteClass(@PathVariable("id") Integer clsid, Model model){
//        classInfoService.deleteClassByID(clsid);
//        List<ClassInfo> clsLists = classInfoService.findAllClassInfo();
//        model.addAttribute("cls",clsLists);
//        return "redirect:/cls/list";
//    }
}
