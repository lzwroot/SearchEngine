package com.hust.searchengine.Service;


import com.github.pagehelper.PageInfo;
import com.hust.searchengine.Entity.*;

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
    List<JournalCount> getTopJournals();
    List<FieldCount> getTopFields();
}
