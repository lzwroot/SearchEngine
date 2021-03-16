package com.hust.searchengine.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.searchengine.Entity.*;
import com.hust.searchengine.Mapper.ManagerMapper;
import com.hust.searchengine.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Integer totalNumberOfFeedbacks() {
        return managerMapper.totalNumberOfFeedbacks();
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

    @Override
    public List<Feedback> getAllFeedbacks() {
        return managerMapper.getAllFeedbacks();
    }

    @Override
    public Integer sendResponses(Integer managerid, String result, Integer feedback_id) {
        Date result_time = new Date();
        return managerMapper.sendResponses(managerid, result, feedback_id, result_time);
    }

    @Override
    public List<JournalCount> getTopJournals() {
        return managerMapper.getTopJournals();
    }

    @Override
    public List<FieldCount> getTopFields() {
        return managerMapper.getTopFields();
    }
}
