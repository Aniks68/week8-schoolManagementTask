package com.ikechukwu.springschoolmanagement.services;

import com.ikechukwu.springschoolmanagement.models.Student;

import java.util.List;

public interface StudentService {
    void saveStudent(Student student);

    Student regAuth(String email);

    Student getStudent(Long id);

    void deleteStudent(Student student);

    Student authenticate(String email, String password);

    List<Student> getAll();
}
