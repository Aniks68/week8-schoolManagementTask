package com.ikechukwu.springschoolmanagement.repository;

import com.ikechukwu.springschoolmanagement.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findFirstByEmail(String email);
}
