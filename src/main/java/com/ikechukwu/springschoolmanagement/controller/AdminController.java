package com.ikechukwu.springschoolmanagement.controller;

import com.ikechukwu.springschoolmanagement.enums.Grade;
import com.ikechukwu.springschoolmanagement.enums.Position;
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
public class AdminController {
    private final StaffService staffService;
    private final StudentService studentService;

    public AdminController(StaffService staffService, StudentService studentService) {
        this.staffService = staffService;
        this.studentService = studentService;
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin_page";
    }

    @GetMapping("/createStaff")
    public String getStaffRegPage() {
        return "register/staff_register";
    }

    @PostMapping("/registerStaff")
    public String getStaffPage(@ModelAttribute Staff user, Model model) {
        System.out.println("Registration request: " + user);
        if(staffService.regAuthenticate(user.getEmail()) == null) {
            Staff staff = new Staff();
            registerStaff(user, staff);
            staff.setGender(user.getGender());
            staff.setDob(user.getDob());
            staffService.saveUser(staff);
            System.out.println("Staff of id: " + staff.getId() + ", has been registered.");
        } else {
            System.out.println("This staff already exist");
        }

        return "redirect:/admin";
    }

    @GetMapping("/staffList")
    public String viewStaffBody(Model model, HttpSession session) {
        Staff admin = (Staff) session.getAttribute("user");
        if (isAdmin(admin)) {
            Staff staff = new Staff();
            model.addAttribute("staffList", staffService.getAllStaff());
            model.addAttribute("staff", staff);
            return "list/staff_list";
        }
        model.addAttribute("errorMessage", "Sorry, you're not an admin");
        model.addAttribute("errorNotice", "RETURN TO LOGIN PAGE");
        model.addAttribute("errorLink", "/staffLogin");
        return "error";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable (value = "id") Long id, Model model, HttpSession session) {
        Staff staff = staffService.getStaff(id);
        Staff admin = (Staff) session.getAttribute("user");
        if (staff != null && isAdmin(admin)) {
            model.addAttribute("staff", staff);

            return "edit/staff_edit";
        }
        model.addAttribute("errorMessage", "Wrong staff identity");
        model.addAttribute("errorNotice", "RETURN TO LIST PAGE");
        model.addAttribute("errorLink", "/staffList");
        return "error";
    }

    @PostMapping("/updateStaff/{id}")
    public String getStaffList(@PathVariable (value = "id") Long id, @RequestParam(value = "firstname") String firstname,
                               @RequestParam(value = "lastname") String lastname, @RequestParam(value = "address") String address,
                               @RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
                               @RequestParam(value = "position") String position) {

        Staff staff = staffService.getStaff(id);
        if (staff != null) {
            staff.setFirstname(firstname);
            staff.setLastname(lastname);
            staff.setAddress(address);
            staff.setEmail(email);
            staff.setPassword(password);
            staff.setPosition(Position.valueOf(position));
            staff.setSalary(staff.getPosition().getSalary());
            staff.setJobDescription(staff.getPosition().getJobDescriptor());
            staffService.saveUser(staff);
            System.out.println("Staff of id: " + staff.getId() + ", has been updated.");
        }
        return "redirect:/staffList";
    }

    @GetMapping("/deleteStaff/{id}")
    public String showDelete(@PathVariable (value = "id") Long id, @ModelAttribute Staff user, HttpSession session) {
        Staff admin = (Staff) session.getAttribute("user");
        Staff staff = staffService.getStaff(id);
        if(isAdmin(admin) && staff != null) {
            staffService.deleteStaff(staff);
        }
        return "redirect:/staffList";
    }

    /*  Controller settings for applicants and students */
    @GetMapping("/createStudent")
    public String getStudentRegPage() {
        return "register/student_register";
    }

    @GetMapping("/updateStudentClass/{id}")
    public String getStudentPage(@PathVariable(value = "id") Long id) {
        Student student = studentService.getStudent(id);
         if (student != null && student.getSessionAverage() >= 55.00) {
             System.out.println("Old Grade: " + student.getGrade());
             Grade grade = student.getGrade();
             Grade newGrade = null;

             if (grade.equals(Grade.GRADE6)) {
                 System.out.println("You're a graduate now!");
                 studentService.deleteStudent(student);
                 return "redirect:/studentList";
             }

             if(grade.equals(Grade.GRADE1)) {
                 newGrade = Grade.GRADE2;
             } else if (grade.equals(Grade.GRADE2)) {
                 newGrade = Grade.GRADE3;
             } else if (grade.equals(Grade.GRADE3)) {
                 newGrade = Grade.GRADE4;
             } else if (grade.equals(Grade.GRADE4)) {
                 newGrade = Grade.GRADE5;
             } else if (grade.equals(Grade.GRADE5)) {
                 newGrade = Grade.GRADE6;
             }
             student.setGrade(newGrade);
             student.setSessionAverage(0.00);

             System.out.println("New Grade: " + student.getGrade());

             student.setGradeFee(student.getGrade().getGradeFee());
             student.setApplyStatus("Student");
             studentService.saveStudent(student);
         }
        return "redirect:/studentList";
    }

    @GetMapping("/registerStudent/{id}")
    public String updateAppStatus(@PathVariable(value = "id") Long id) {
        Student applicant = studentService.getStudent(id);
        if(applicant != null && applicant.getApplyStatus().equals("Applicant") && applicant.getApplyScore() >= 55) {
            applicant.setApplyStatus("Student");
            studentService.saveStudent(applicant);
        }
        return "redirect:/appList";
    }

    @GetMapping("/deleteApplicant/{id}")
    public String punishStudent(@PathVariable(value = "id") Long id) {
        Student student = studentService.getStudent(id);
        if(student != null) {
            studentService.deleteStudent(student);
        }
        return "redirect:/studentList";
    }

    @GetMapping("/appList")
    public String viewApplicants(Model model, HttpSession session) {
        Staff admin = (Staff) session.getAttribute("user");
        if (isAdmin(admin)) {
            getStudents(model);
            return "list/applicant_list";
        }
        model.addAttribute("errorMessage", "Sorry, you're not an admin");
        model.addAttribute("errorNotice", "RETURN TO LOGIN PAGE");
        model.addAttribute("errorLink", "/staffLogin");
        return "error";
    }

    @GetMapping("/studentList")
    public String viewStudents(Model model, HttpSession session) {
        Staff admin = (Staff) session.getAttribute("user");
        if (isAdmin(admin)) {
            getStudents(model);
            return "list/student_list";
        }
        model.addAttribute("errorMessage", "Sorry, you're not an admin");
        model.addAttribute("errorNotice", "RETURN TO LOGIN PAGE");
        model.addAttribute("errorLink", "/staffLogin");
        return "error";
    }

    private void getStudents(Model model) {
        Student applicant = new Student();
        model.addAttribute("appList", studentService.getAll());
        model.addAttribute("applicant", applicant);
    }

    private boolean isAdmin(Staff staff) {
        return staff.getJobDescription().equals("Staff and student organisation") || staff.getJobDescription().equals("School Management");
    }

    private void registerStaff(@ModelAttribute Staff user, Staff staff) {
        staff.setFirstname(user.formatString(user.getFirstname()));
        staff.setLastname(user.formatString(user.getLastname()));
        staff.setAddress(user.getAddress());
        staff.setEmail(user.getEmail());
        staff.setPassword(user.getPassword());
        staff.setPosition(Position.valueOf(user.getPosition().toString()));
        staff.setSalary(staff.getPosition().getSalary());
        staff.setJobDescription(staff.getPosition().getJobDescriptor());
    }
}
