package com.ikechukwu.springschoolmanagement.controller;

import com.ikechukwu.springschoolmanagement.enums.Position;
import com.ikechukwu.springschoolmanagement.models.Staff;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StaffController {
    private final StaffServiceImpl staffServiceImpl;

    public StaffController(StaffServiceImpl staffServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/adminLogin")
    public String getAdminLoginPage() {
        Staff staff = staffServiceImpl.getStaff(3L);
//        Staff staff1 = staffServiceImpl.getStaff(2L);

        staffServiceImpl.deleteStaff(staff);

        return "admin_login";
    }

    @PostMapping("/registerStaff")
    public String getStaffPage(@ModelAttribute Staff user, Model model) {
        System.out.println("Registration request: " + user);
        Staff staff = new Staff();
        staff.setFirstname(user.formatString(user.getFirstname()));
        staff.setLastname(user.formatString(user.getLastname()));
        staff.setAddress(user.getAddress());
        staff.setEmail(user.getEmail());
        staff.setGender(user.getGender());
        staff.setDob(user.getDob());
        staff.setPassword(user.getPassword());
        staff.setPosition(Position.valueOf(user.getPosition().toString()));
        staff.setSalary(staff.getPosition().getSalary());
        staff.setJobDescription(staff.getPosition().getJobDescriptor());

        staffServiceImpl.saveUser(staff);
        System.out.println("Staff of id: " + staff.getId() + ", has been registered.");

        return "redirect:/login";
    }

}
