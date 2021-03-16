package com.hust.searchengine.Service;


import com.github.pagehelper.PageInfo;
import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Entity.Feedback;
import com.hust.searchengine.Entity.Manager;
import com.hust.searchengine.Entity.User;

import java.util.Date;
import java.util.List;

public interface ManagerService {
    Manager managerLogin(Integer managerid, String password);
    Integer totalNumberOfArticles();
    Integer totalNumberOfUsers();
    Integer totalNumberOfFeedbacks();
    PageInfo<User> findAllUsers(Integer pageIndex, Integer pageSize);
    List<User> findAllUsers();
    Integer deleteUserByUsername(String username);
    Integer addNewUser(User user);
    List<Feedback> getAllFeedbacks();
    Integer sendResponses(Integer managerid, String result, Integer feedback_id);
}
