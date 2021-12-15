package com.ikechukwu.springschoolmanagement.controller;

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
        return "login/staff_login";
    }

    @PostMapping("/staffLogin")
    public String getStaffDashboard(@ModelAttribute Staff user, HttpSession session) {
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
            return "redirect:/staffPage";
        }
    }

    @GetMapping("/staffPage")
    public String getStaffDash() {
        return "staff_dash";
    }

    @GetMapping("/viewApplicants")
    public String getStaffApp(Model model) {
        Student applicant = new Student();
        model.addAttribute("appList", studentServiceImpl.getAll());
        model.addAttribute("applicant", applicant);
        return "student/staff_applicant";
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

    @GetMapping("/viewStudents")
    public String getStaffStudents(Model model) {
        Student student = new Student();
        model.addAttribute("students", studentServiceImpl.getAll());
        model.addAttribute("student", student);
        return "student/staff_student";
    }

    @GetMapping("/updateStudentDetails/{id}")
    public String getDetailsUpdate(@PathVariable (value = "id") Long id, Model model) {
        Student student = (Student) studentServiceImpl.getStudent(id);
        if(student != null) {
            model.addAttribute("student", student);
            return "edit/student_edit";
        }
        return "redirect:/staffPage";
    }

    @PostMapping("/updateStudentDetails/{id}")
    public String seeUpdates(@PathVariable (value = "id") Long id, @RequestParam (value = "sessionAverage") double average, @RequestParam (value = "behaviour") String behaviour) {
        Student student = studentServiceImpl.getStudent(id);
        if (student != null) {
            student.setBehaviour(behaviour);
            student.setSessionAverage(average);
            studentServiceImpl.saveStudent(student);
        }
        return "redirect:/viewStudents";
    }

    private boolean isAdmin(Staff staff) {
        return staff.getJobDescription().equals("Staff and student organisation") || staff.getJobDescription().equals("School Management");
    }
}
