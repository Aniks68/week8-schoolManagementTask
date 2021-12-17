package com.ikechukwu.springschoolmanagement.controller;

import com.ikechukwu.springschoolmanagement.models.Staff;
import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.services.StaffService;
import com.ikechukwu.springschoolmanagement.services.StudentService;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StaffServiceImpl;
import com.ikechukwu.springschoolmanagement.services.serviceImpl.StudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class StaffController {
    private final StudentService studentService;
    private final StaffService staffService;

    public StaffController(StudentService studentService, StaffService staffService) {
        this.studentService = studentService;
        this.staffService = staffService;
    }

    @GetMapping("/staffLogin")
    public String getStaffLoginPage() {
//        Staff admin = new Staff();
//        admin.setFirstname("Samuel"); admin.setLastname("Eldridge"); admin.setEmail("sam@el.com"); admin.setPosition(Position.VICE_PRINCIPAL);
//        admin.setSalary(admin.getPosition().getSalary()); admin.setJobDescription(admin.getPosition().getJobDescriptor());
//        admin.setAddress("Phase IV, Behind Blessed Chapel, Kubwa"); admin.setGender("Male"); admin.setPassword("12345");
//        admin.setDob("1991-05-10"); staffServiceImpl.saveUser(admin);
//        System.out.println("Admin: " + admin.getLastname() + " created successfully");
        return "login/staff_login";
    }

    @PostMapping("/staffLogin")
    public String getStaffDashboard(@ModelAttribute Staff user, HttpSession session) {
        System.out.println("Login request: " + user);
        Staff staff = staffService.authenticate(user.getEmail(), user.getPassword());
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
        model.addAttribute("appList", studentService.getAll());
        model.addAttribute("applicant", applicant);
        return "student/staff_applicant";
    }

    @GetMapping("/updateScore/{id}")
    public String getScorePage(@PathVariable (value = "id") Long id, Model model) {
        Student applicant = studentService.getStudent(id);
        if(applicant != null) {
            model.addAttribute("applicant", applicant);
            return "edit/app_edit";
        }
        return "redirect:/staffPage";
    }

    @PostMapping("/updateScore/{id}")
    public String getScoreUpdate(@PathVariable (value = "id") Long id, @RequestParam (value = "applyScore") int score) {
        Student applicant = studentService.getStudent(id);

        if (applicant != null && applicant.getApplyStatus().equals("Applicant")) {
            applicant.setApplyScore(score);
            studentService.saveStudent(applicant);
            System.out.println("Score of " + applicant.getApplyScore() + " was added.");
        }
        return "redirect:/staffPage";
    }

    @GetMapping("/viewStudents")
    public String getStaffStudents(Model model) {
        Student student = new Student();
        model.addAttribute("students", studentService.getAll());
        model.addAttribute("student", student);
        return "student/staff_student";
    }

    @GetMapping("/updateStudentDetails/{id}")
    public String getDetailsUpdate(@PathVariable (value = "id") Long id, Model model) {
        Student student = (Student) studentService.getStudent(id);
        if(student != null) {
            model.addAttribute("student", student);
            return "edit/student_edit";
        }
        return "redirect:/staffPage";
    }

    @PostMapping("/updateStudentDetails/{id}")
    public String seeUpdates(@PathVariable (value = "id") Long id, @RequestParam (value = "sessionAverage") double average, @RequestParam (value = "behaviour") String behaviour) {
        Student student = studentService.getStudent(id);
        if (student != null) {
            student.setBehaviour(behaviour);
            student.setSessionAverage(average);
            studentService.saveStudent(student);
        }
        return "redirect:/viewStudents";
    }

    @GetMapping("/staffLogout")
    public String logoutStaff(HttpSession session) {
        session.invalidate();
        return "login/staff_login";
    }

    private boolean isAdmin(Staff staff) {
        return staff.getJobDescription().equals("Staff and student organisation") || staff.getJobDescription().equals("School Management");
    }
}
