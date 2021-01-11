package com.hust.searchengine.ServiceImpl;

import com.hust.searchengine.Entity.ClassInfo;
import com.hust.searchengine.Mapper.ClassMapper;
import com.hust.searchengine.Service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public List<ClassInfo> findAllClassInfo() {
        return classMapper.findAllClassInfo();
    }

    @Override
    public Integer addClassInfo(ClassInfo classInfo) {
        return classMapper.addClassInfo(classInfo);
    }

    @Override
    public ClassInfo findClassByID(Integer clsid) {
        return classMapper.findClassByID(clsid);
    }

    @Override
    public Integer updateClassByID(ClassInfo classInfo) {
        return classMapper.updateClassByID(classInfo);
    }

    @Override
    public Integer deleteClassByID(Integer clsid) {
        return classMapper.deleteClassByID(clsid);
    }
}
