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
    public List<User> findAllUsers() {
        return managerMapper.findAllUsers();
    }

    @Override
    public Integer deleteUserByUsername(String username) {
        return managerMapper.deleteUserByUsername(username);
    }

    @Override
    public Integer addNewUser(User user) {
        return managerMapper.addNewUser(user);
    }
}
