package com.hust.searchengine.Controller;

import com.github.pagehelper.PageInfo;
import com.hust.searchengine.Entity.Manager;
import com.hust.searchengine.Entity.User;
import com.hust.searchengine.Service.ManagerService;
import com.hust.searchengine.Service.SearchService;
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

    @Autowired
    private SearchService searchService;

    //登陆页面
    @GetMapping("login")
    public String ManagerLoginPage(HttpSession session, Model model){
        Manager manager = (Manager) session.getAttribute("manager");
        if(manager!=null) {
            return "redirect:/manager/manager_dashboard";
        }
        String message = "";
        model.addAttribute("msg", message);
        return "managerlogin";
    }

    //登录提交
    @PostMapping("login")
    public String ManagerLoginInfo(@RequestParam("managerid") Integer managerid,
                                   @RequestParam("password") String password,
                                   HttpSession session, Model model){
        Manager manager = managerService.managerLogin(managerid, password);
        session.setMaxInactiveInterval(3600); //括号内数字单位是秒，表示登录的持续时间
        if(manager!=null){
            session.setAttribute("manager", manager);
            return "redirect:/manager/manager_dashboard";
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

    //查看仪表板
    @GetMapping("manager_dashboard")
    public String DashboardPage(HttpSession session, Model model){
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager!=null) {
            Integer totalArticleNum = managerService.totalNumberOfArticles();
            Integer totalUserNum = managerService.totalNumberOfUsers();
            Integer totalOnlineUserNum = (int)(totalUserNum * 0.8);
            model.addAttribute("total_article_number", totalArticleNum);
            model.addAttribute("total_user_number", totalUserNum);
            model.addAttribute("total_online_user_number", totalOnlineUserNum);
            return "manager_dashboard";
        }
        else
            return "redirect:/manager/login";
    }

    //管理用户
    @RequestMapping("manage_user")
    public String UserManagementPage(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                     HttpSession session, Model model){
        Manager manager = (Manager)session.getAttribute("manager");
        String message = "";
        String color = "green";
        if(manager!=null) {
            PageInfo<User> users = managerService.findAllUsers(pageIndex, pageSize);
            model.addAttribute("users", users);
            model.addAttribute("msg", message);
            model.addAttribute("color", color);
            return "manage_user";
        }
        else
            return "redirect:/manager/login";
    }

    //查看用户详细信息
    @RequestMapping("detail/{username}")
    public String DetailedInformationPage(@PathVariable("username") String username,
                                          HttpSession session, Model model){
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager!=null) {
            //判断用户名是否存在
            if(!username.equals(searchService.FindIfUsernameAlreadyExists(username))){
                return "manage_user_not_found";
            }
            User user = searchService.findUserByUsername(username);
            model.addAttribute("user", user);
            return "manage_user_detail";
        }
        else
            return "redirect:/manager/login";
    }

    //修改用户详细信息
    @GetMapping("update/{username}")
    public String UpdatePage(@PathVariable("username") String username,
                                          HttpSession session, Model model){
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager!=null) {
            //判断用户名是否存在
            if(!username.equals(searchService.FindIfUsernameAlreadyExists(username))){
                return "manage_user_not_found";
            }
            User user = searchService.findUserByUsername(username);
            String message = "";
            String former_username = username;
            model.addAttribute("former_username", former_username);
            model.addAttribute("user", user);
            model.addAttribute("msg", message);
            return "manage_user_update";
        }
        else
            return "redirect:/manager/login";
    }

    //修改用户信息提交
    @PostMapping("update/{former_username}")
    public String UpdatePost(@PathVariable("former_username") String oldUsername,
                             @RequestParam("confirming_password") String cPassword,
                             User userUpdated,
                             HttpSession session, Model model){
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager!=null) {
            User oldUser = searchService.findUserByUsername(oldUsername);
            //检查密码对不对
            if(cPassword.equals(oldUser.getPassword())) {
                String newUsername = userUpdated.getUsername();
                String message = "";
                //判断数据库内是否已存在相应用户名
                if (!oldUsername.equals(newUsername) && newUsername.equals(searchService.FindIfUsernameAlreadyExists(newUsername))) {
                    message = "修改用户名失败，已存在此用户名！";
                    model.addAttribute("msg", message);
                    return "manage_user_update";
                }
                //修改除了用户名以外的其他数据
                String newPassword = userUpdated.getPassword();
                boolean newSex = userUpdated.isSex();
                String newInstitution = userUpdated.getInstitution();
                String newEmail = userUpdated.getEmail();
                // 保存到数据库里
                searchService.updateUserByUsername(oldUsername, newUsername, newPassword, newSex, newInstitution, newEmail);
                model.addAttribute("msg", message);
                return "redirect:/manager/detail/" + newUsername;
            }
            else{
                String message = "密码错误，请重新输入！";
                model.addAttribute("msg", message);
                return "manage_user_update";
            }
        }
        else
            return "redirect:/manager/login";
    }

    //插入新的用户页面
    @GetMapping("new_user")
    public String NewUserPage(HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        if(manager!=null){
            return "manage_new_user";
        }else
            return "redirect:/manager/login";
    }

    //插入新的用户提交
    @PostMapping("new_user")
    public String NewUserSubmit(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                User newUser, HttpSession session, Model model){
        Manager manager = (Manager) session.getAttribute("manager");
        if(manager!=null){
            String username = newUser.getUsername();
            //判断用户名是否存在
            if(username.equals(searchService.FindIfUsernameAlreadyExists(username))){
                model.addAttribute("msg", "插入新的用户失败，用户名已存在！");
                model.addAttribute("color", "red");
            }
            else{
                int added = managerService.addNewUser(newUser);
                if (added == 1) {
                    model.addAttribute("msg", "成功插入新的用户！");
                    model.addAttribute("color", "green");
                } else {
                    model.addAttribute("msg", "插入新的用户失败！");
                    model.addAttribute("color", "red");
                }
            }
            PageInfo<User> users = managerService.findAllUsers(pageIndex, pageSize);
            model.addAttribute("users", users);
            return "manage_user";
        }else
            return "redirect:/manager/login";
    }

    //删除用户
    @RequestMapping("delete/{username}")
    public String DeleteUser(@PathVariable("username") String username,
                             HttpSession session, Model model){
        Manager manager = (Manager) session.getAttribute("manager");
        if(manager!=null){
            //判断用户名是否存在
            if(!username.equals(searchService.FindIfUsernameAlreadyExists(username))){
                return "manage_user_not_found";
            }
            managerService.deleteUserByUsername(username);
            model.addAttribute("msg", "删除用户成功！");
            model.addAttribute("color", "green");
            return "redirect:/manager/manage_user";
        }else
            return "redirect:/manager/login";
    }

    //404页面
    @RequestMapping("*")
    public String Page_404(HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        if(manager!=null){
            return "manager_404_page";
        }else
            return "redirect:/manager/login";
    }
}
