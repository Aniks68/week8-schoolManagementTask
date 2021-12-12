package com.ikechukwu.springschoolmanagement.repository;

import com.ikechukwu.springschoolmanagement.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmailAndPassword(String email, String password);

    Optional<Staff> findFirstByEmail(String email);
}
