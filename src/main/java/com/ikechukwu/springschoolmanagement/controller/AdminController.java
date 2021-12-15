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
public class AdminController {
    private final StaffServiceImpl staffServiceImpl;
    private final StudentServiceImpl studentServiceImpl;

    public AdminController(StaffServiceImpl staffServiceImpl, StudentServiceImpl studentServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
        this.studentServiceImpl = studentServiceImpl;
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
        if(staffServiceImpl.regAuthenticate(user.getEmail()) == null) {
            Staff staff = new Staff();
            registerStaff(user, staff);
            staff.setGender(user.getGender());
            staff.setDob(user.getDob());
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

    @GetMapping("/registerStudent/{id}")
    public String getStudentPage(@PathVariable(value = "id") Long id) {
        Student student = studentServiceImpl.getStudent(id);
         if (student != null && student.getApplyScore() >= 55) {
             student.setGradeFee(student.getGrade().getGradeFee());
             student.setApplyStatus("Student");
             studentServiceImpl.saveStudent(student);
             System.out.println(student.getFirstname() + " is now a student.");
         }
        return "redirect:/appList";
    }

    @GetMapping("/staffList")
    public String viewStaffBody(Model model, HttpSession session) {
        Staff admin = (Staff) session.getAttribute("user");
        if (isAdmin(admin)) {
            Staff staff = new Staff();
            model.addAttribute("staffList", staffServiceImpl.getAllStaff());
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
        Staff staff = staffServiceImpl.getStaff(id);
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

        Staff staff = staffServiceImpl.getStaff(id);
        if (staff != null) {
            staff.setFirstname(firstname);
            staff.setLastname(lastname);
            staff.setAddress(address);
            staff.setEmail(email);
            staff.setPassword(password);
            staff.setPosition(Position.valueOf(position));
            staff.setSalary(staff.getPosition().getSalary());
            staff.setJobDescription(staff.getPosition().getJobDescriptor());
            staffServiceImpl.saveUser(staff);
            System.out.println("Staff of id: " + staff.getId() + ", has been updated.");
        }
        return "redirect:/staffList";
    }

    @GetMapping("/deleteStaff/{id}")
    public String showDelete(@PathVariable (value = "id") Long id, @ModelAttribute Staff user, HttpSession session) {
        Staff admin = (Staff) session.getAttribute("user");
        Staff staff = staffServiceImpl.getStaff(id);
        if(isAdmin(admin) && staff != null) {
            staffServiceImpl.deleteStaff(staff);
        }
        return "list/staff_list";
    }

    @GetMapping("/appList")
    public String viewApplicants(Model model) {
        getStudents(model);
        return "list/applicant_list";
    }

    @GetMapping("/studentList")
    public String viewStudents(Model model) {
        getStudents(model);
        return "list/applicant_list";
    }



    private void getStudents(Model model) {
        Student applicant = new Student();
        model.addAttribute("appList", studentServiceImpl.getAll());
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
