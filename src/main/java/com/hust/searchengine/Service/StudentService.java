package com.hust.searchengine.Service;

import com.hust.searchengine.Entity.Student;
import com.github.pagehelper.PageInfo;

public interface StudentService {
    PageInfo<Student> findAllStudent(Integer pageIndex, Integer pageSize);

    Integer addStudentInfo(Student student);

    PageInfo<Student> findStudentByClsIDStuName(Integer pageIndex, Integer pageSize, Integer clsid, String stu_name);

    PageInfo<Student> findStudentByStuName(Integer pageIndex, Integer pageSize, String stu_name);

    Student findStudentByID(Integer stuid);

    Integer updateStudentByID(Student student);

    Integer deleteStudentByID(Integer stuid);
}
