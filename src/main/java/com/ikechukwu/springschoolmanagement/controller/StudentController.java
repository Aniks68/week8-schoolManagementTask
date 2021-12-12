package com.ikechukwu.springschoolmanagement.controller;

import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StudentServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping("/studentLogin")
    public String getStudentLoginPage() {
        return "login/student_login";
    }

    @PostMapping("/studentLogin")
    public String getStudentDash(@ModelAttribute Student user, Model model) {
        Student student = studentServiceImpl.authenticate(user.getEmail(), user.getPassword());
        if((student != null) && (student.getApplyStatus().equals("Student"))) {
            return "student/student_page";
        }
        model.addAttribute("errorMessage", "Sorry, you're not a student");
        model.addAttribute("errorNotice", "RETURN TO APPLICANT PAGE");
        model.addAttribute("errorLink", "/appLogin");
        return "error";
    }

    @GetMapping("/appLogin")
    public String getAppLoginPage() {
        return "login/applicant_login";
    }

    @PostMapping("/registerApplicant")
    public String getAppDash(@ModelAttribute Student user) {
        System.out.println("Registration request: " + user);
        Student student = new Student();

        if(studentServiceImpl.regAuth(user.getEmail()) == null) {
            student.setFirstname(user.formatString(user.getFirstname()));
            student.setLastname(user.formatString(user.getLastname()));
            student.setAddress(user.getAddress());
            student.setEmail(user.getEmail());
            student.setGender(user.getGender());
            student.setPassword(user.getPassword());
            student.setDob(user.getDob());
            student.setGrade(user.getGrade());
            student.setGradeFee(student.getGrade().getGradeFee());
            student.setApplyStatus("Student");

            studentServiceImpl.saveStudent(student);
            System.out.println("Student of id: " + student.getId() + ", has been registered.");
        }
        return "redirect:/admin";
    }

    @RequestMapping("/error")
    public String getDefaultError(Model model) {
        String message = "You have entered a wrong URL";
        model.addAttribute("errorMessage", message);
        model.addAttribute("errorNotice", "RETURN TO DASHBOARD PAGE");
        model.addAttribute("errorLink", "/dashboard");
        return "error";
    }
}
