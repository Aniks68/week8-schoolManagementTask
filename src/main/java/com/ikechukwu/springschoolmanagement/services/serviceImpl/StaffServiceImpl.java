package com.ikechukwu.springschoolmanagement.services.serviceImpl;

import com.ikechukwu.springschoolmanagement.models.Staff;
import com.ikechukwu.springschoolmanagement.repository.StaffRepository;
import com.ikechukwu.springschoolmanagement.services.StaffService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff saveUser(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff authenticate(String email, String password) {
        return staffRepository.findByEmailAndPassword(email, password)
                .orElse(null);
    }

    @Override
    public Staff regAuthenticate(String email) {
        return staffRepository.findFirstByEmail(email)
                .orElse(null);
    }

    @Override
    public Staff getStaff(Long id) {
        return staffRepository.getById(id);
    }

    @Override
    public void deleteStaff(Staff staff) {
        staff.setStatus("INACTIVE");
        staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
}
