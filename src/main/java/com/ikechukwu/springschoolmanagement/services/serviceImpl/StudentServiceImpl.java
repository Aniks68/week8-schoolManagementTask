package com.ikechukwu.springschoolmanagement.services.serviceImpl;

import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.repository.StudentRepository;
import com.ikechukwu.springschoolmanagement.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student regAuth(String email) {
        return studentRepository.findFirstByEmail(email)
                .orElse(null);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public void deleteStudent(Student student) {
        student.setStatus("Inactive");
        studentRepository.save(student);
    }

    public Student authenticate(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password)
                .orElse(null);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}
