package com.hust.searchengine.ServiceImpl;

import com.hust.searchengine.Entity.Student;
import com.hust.searchengine.Mapper.StudentMapper;
import com.hust.searchengine.Service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageInfo<Student> findAllStudent(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = studentMapper.findAllStudent();
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public PageInfo<Student> findStudentByClsIDStuName(Integer pageIndex, Integer pageSize, Integer clsid, String stu_name) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = studentMapper.findStudentByClsIDStuName(clsid, stu_name);
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }

    @Override
    public Integer addStudentInfo(Student student) {
        return studentMapper.addStudentInfo(student);
    }

    @Override
    public Student findStudentByID(Integer stuid) {
        return studentMapper.findStudentByID(stuid);
    }

    @Override
    public Integer updateStudentByID(Student student) {
        return studentMapper.updateStudentByID(student);
    }

    @Override
    public Integer deleteStudentByID(Integer stuid) {
        return studentMapper.deleteStudentByID(stuid);
    }

    @Override
    public PageInfo<Student> findStudentByStuName(Integer pageIndex, Integer pageSize, String stu_name) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> lists = studentMapper.findStudentByStuName(stu_name);
        PageInfo<Student> info = new PageInfo<>(lists);
        return info;
    }


}
