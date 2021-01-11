package com.hust.searchengine.Service;


import com.hust.searchengine.Entity.ClassInfo;

import java.util.List;

public interface ClassInfoService {
    List<ClassInfo> findAllClassInfo();
    Integer addClassInfo(ClassInfo classInfo);
    ClassInfo findClassByID(Integer clsid);
    Integer updateClassByID(ClassInfo classInfo);
    Integer deleteClassByID(Integer clsid);
}
