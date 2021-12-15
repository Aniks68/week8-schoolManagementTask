package com.ikechukwu.springschoolmanagement.controller;

import com.ikechukwu.springschoolmanagement.enums.Position;
import com.ikechukwu.springschoolmanagement.models.Staff;
import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StaffServiceImpl;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class StaffController {
    private final StaffServiceImpl staffServiceImpl;
    private final StudentServiceImpl studentServiceImpl;

    public StaffController(StaffServiceImpl staffServiceImpl, StudentServiceImpl studentServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping("/staffLogin")
    public String getStaffLoginPage() {
        Staff admin = new Staff();
//        admin.setFirstname("Ikechukwu"); admin.setLastname("Anene"); admin.setEmail("aniks@king.com");
//        admin.setDob("28-11-1993"); admin.setPosition(Position.PRINCIPAL); admin.setJobDescription(Position.PRINCIPAL.getJobDescriptor());
//        admin.setGender("Male"); admin.setAddress("Gbazango Extension"); admin.setSalary(Position.PRINCIPAL.getSalary());
//        admin.setPassword("12345");
//        staffServiceImpl.saveUser(admin);
        return "login/staff_login";
    }

    @PostMapping("/staffLogin")
    public String getStaffDashboard(@ModelAttribute Staff user, HttpSession session, Model model) {
        System.out.println("Login request: " + user);
        Staff staff = staffServiceImpl.authenticate(user.getEmail(), user.getPassword());
        System.out.println("Logging Staff: " + staff);
        if (staff == null) {
            return "login/staff_login";
        }
        session.setAttribute("user", staff);
        if(isAdmin(staff)) {
            return "redirect:/admin";
        } else {
            Student applicant = new Student();
            model.addAttribute("appList", studentServiceImpl.getAll());
            model.addAttribute("applicant", applicant);
            return "staff_page";
        }
    }

    @GetMapping("/staffPage")
    public String getStaffPage(Model model) {
        Student applicant = new Student();
        model.addAttribute("appList", studentServiceImpl.getAll());
        model.addAttribute("applicant", applicant);
        return "staff_page";
    }

    @GetMapping("/updateScore/{id}")
    public String getScorePage(@PathVariable (value = "id") Long id, Model model) {
        Student applicant = studentServiceImpl.getStudent(id);
        if(applicant != null) {
            model.addAttribute("applicant", applicant);
            return "edit/app_edit";
        }
        return "redirect:/staffPage";
    }

    @PostMapping("/updateScore/{id}")
    public String getScoreUpdate(@PathVariable (value = "id") Long id, @RequestParam (value = "applyScore") int score) {
        Student applicant = studentServiceImpl.getStudent(id);

        if (applicant != null && applicant.getApplyStatus().equals("Applicant")) {
            applicant.setApplyScore(score);
            studentServiceImpl.saveStudent(applicant);
            System.out.println("Score of " + applicant.getApplyScore() + " was added.");
        }
        return "redirect:/staffPage";
    }

    private boolean isAdmin(Staff staff) {
        return staff.getJobDescription().equals("Staff and student organisation") || staff.getJobDescription().equals("School Management");
    }
}
