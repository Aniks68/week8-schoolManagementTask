package com.ikechukwu.springschoolmanagement.services;

import com.ikechukwu.springschoolmanagement.models.Student;

public interface StudentService {
    void saveStudent(Student student);

    Student regAuth(String email);

    Student getStudent(Long id);

    void deleteStudent(Student student);
}
