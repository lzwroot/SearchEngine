package com.hust.searchengine.ServiceImpl;

import com.hust.searchengine.Entity.Student;
import com.hust.searchengine.Entity.User;
import com.hust.searchengine.Mapper.SearchMapper;
import com.hust.searchengine.Service.SearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public PageInfo<Student> findAllStudent(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = searchMapper.findAllStudent();
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Student> findStudentByClsIDStuName(Integer pageIndex, Integer pageSize, Integer clsid, String stu_name) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = searchMapper.findStudentByClsIDStuName(clsid, stu_name);
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public Integer addStudentInfo(Student student) {
        return searchMapper.addStudentInfo(student);
    }

    @Override
    public Student findStudentByID(Integer stuid) {
        return searchMapper.findStudentByID(stuid);
    }

    @Override
    public Integer updateStudentByID(Student student) {
        return searchMapper.updateStudentByID(student);
    }

    @Override
    public Integer deleteStudentByID(Integer stuid) {
        return searchMapper.deleteStudentByID(stuid);
    }

    @Override
    public PageInfo<Student> findStudentByStuName(Integer pageIndex, Integer pageSize, String stu_name) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = searchMapper.findStudentByStuName(stu_name);
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public User UserLogin(String username, String password) {
        return searchMapper.UserLogin(username,password);
    }
}
