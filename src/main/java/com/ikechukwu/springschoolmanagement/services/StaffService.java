package com.ikechukwu.springschoolmanagement.services;

import com.ikechukwu.springschoolmanagement.models.Staff;

public interface StaffService {
    Staff saveUser(Staff staff);

    Staff authenticate(String email, String password);

    Staff regAuthenticate(String email);

    Staff getStaff(Long id);

    void deleteStaff(Staff staff);
}
