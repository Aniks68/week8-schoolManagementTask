package com.ikechukwu.springschoolmanagement.controller;

import com.ikechukwu.springschoolmanagement.enums.Position;
import com.ikechukwu.springschoolmanagement.models.Staff;
import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StaffServiceImpl;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StaffController {
    private final StaffServiceImpl staffServiceImpl;
    private final StudentServiceImpl studentServiceImpl;

    public StaffController(StaffServiceImpl staffServiceImpl, StudentServiceImpl studentServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "staff_login";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin_page";
    }

    @GetMapping("/createStaff")
    public String getStaffRegPage() {
        return "register/staff_register";
    }

    @PostMapping("/staffLogin")
    public String getStaffDashboard(@ModelAttribute Staff user) {
        System.out.println("Login request: " + user);
        Staff staff = staffServiceImpl.authenticate(user.getEmail(), user.getPassword());
        System.out.println("Logging Staff: " + staff);
        if(staff.getJobDescription().equals("Staff and student organisation")) {
            return "admin_page";
        } else if (staff == null) {
            return "redirect:/login";
        } else {
            return "staff_page";
        }
    }

    @PostMapping("/registerStaff")
    public String getStaffPage(@ModelAttribute Staff user, Model model) {
        System.out.println("Registration request: " + user);
        if(staffServiceImpl.regAuthenticate(user.getEmail()) == null) {
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
        } else {
            System.out.println("This staff already exist");
        }

        return "redirect:/admin";
    }

    @GetMapping("/createStudent")
    public String getStudentRegPage() {
        return "register/student_register";
    }

    @PostMapping("/registerStudent")
    public String getStudentPage(@ModelAttribute Student user) {
        System.out.println("Registration request: " + user);
        Student student = new Student();

        if(studentServiceImpl.regAuth(user.getEmail()) == null) {
            student.setFirstname(user.formatString(user.getFirstname()));
            student.setLastname(user.formatString(user.getLastname()));
            student.setAddress(user.getAddress());
            student.setEmail(user.getEmail());
            student.setGender(user.getGender());
            student.setPassword(user.getPassword());
            student.setGrade(user.getGrade());
            student.setDob(user.getDob());
            student.setGradeFee(student.getGrade().getGradeFee());

            studentServiceImpl.saveStudent(student);
            System.out.println("Student of id: " + student.getId() + ", has been registered.");
        }
        return "redirect:/admin";
    }
}
