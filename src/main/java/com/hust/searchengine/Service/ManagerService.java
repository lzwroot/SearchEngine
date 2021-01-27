package com.hust.searchengine.Service;


import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Entity.Manager;

import java.util.List;

public interface ManagerService {
    Manager managerLogin(Integer managerid, String password);

    //忽略以下代码
    List<ClassInfo> findAllClassInfo();
    Integer addClassInfo(ClassInfo classInfo);
    ClassInfo findClassByID(Integer clsid);
    Integer updateClassByID(ClassInfo classInfo);
    Integer deleteClassByID(Integer clsid);
}
