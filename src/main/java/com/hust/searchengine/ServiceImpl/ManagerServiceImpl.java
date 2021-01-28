package com.hust.searchengine.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.searchengine.Entity.Article;
import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Entity.Manager;
import com.hust.searchengine.Entity.User;
import com.hust.searchengine.Mapper.ManagerMapper;
import com.hust.searchengine.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager managerLogin(Integer managerid, String password) {
        return managerMapper.managerLogin(managerid, password);
    }

    @Override
    public Integer totalNumberOfArticles() {
        return managerMapper.totalNumberOfArticles();
    }

    @Override
    public Integer totalNumberOfUsers() {
        return managerMapper.totalNumberOfUsers();
    }

    @Override
    public PageInfo<User> findAllUsers(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> lists = managerMapper.findAllUsers();
        PageInfo<User> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public Integer deleteUserByUsername(String username) {
        return managerMapper.deleteUserByUsername(username);
    }

    @Override
    public Integer addNewUser(User user) {
        return managerMapper.addNewUser(user);
    }

    //忽略以下代码
//    @Override
//    public List<ClassInfo> findAllClassInfo() {
//        return managerMapper.findAllClassInfo();
//    }
//
//    @Override
//    public Integer addClassInfo(ClassInfo classInfo) {
//        return managerMapper.addClassInfo(classInfo);
//    }
//
//    @Override
//    public ClassInfo findClassByID(Integer clsid) {
//        return managerMapper.findClassByID(clsid);
//    }
//
//    @Override
//    public Integer updateClassByID(ClassInfo classInfo) {
//        return managerMapper.updateClassByID(classInfo);
//    }
//
//    @Override
//    public Integer deleteClassByID(Integer clsid) {
//        return managerMapper.deleteClassByID(clsid);
//    }
}
