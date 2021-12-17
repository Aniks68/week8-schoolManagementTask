package com.ikechukwu.springschoolmanagement.services.serviceImpl;

import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;
    private Student student;
    @BeforeEach
    void setUp() {
        student = new Student();
        student.setEmail("phakocy@decagon.dev");
        student.setFirstname("Warith");
        student.setLastname("Omojola");
        student.setGender("Male");
        student.setDob("2008-11-06");
        student.setPassword("1234");
    }

    @Test
    @DisplayName("Should check applicant registration validation")
    void shouldGetUserByEmail() {

        //mock userRepository
        when(studentRepository.findFirstByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(student));
        //Call the method to be tested
        Student getStudent = studentService.regAuth("phakocy@decagon.dev");
        //assertions
        assertNotNull(getStudent);
        assertEquals("phakocy@decagon.dev" , getStudent.getEmail());
        verify(studentRepository, times(1)).findFirstByEmail(anyString());
    }

    @Test
    @DisplayName("Should test if new applicant is created")
    void shouldCreateApplicant() {

        //mock userRepository
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        //Call the method to be tested
        studentService.saveStudent(student);
        //Assertions
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Should return a student after searching")
    void shouldGetUserByID() {

        //mock userRepository
        when(studentRepository.getById(anyLong())).thenReturn(student);
        //Call the method to be tested
        Student getStudent = studentService.getStudent(1L);
        //assertions
        assertNotNull(getStudent);
        assertEquals(student, getStudent);
        verify(studentRepository, times(1)).getById(anyLong());
    }

    @Test
    @DisplayName("Should check login details match")
    void shouldGetUserByEmailAndPassword() {

        //mock userRepository
        when(studentRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(java.util.Optional.ofNullable(student));
        //Call the method to be tested
        Student getStudent = studentService.authenticate("phakocy@decagon.dev", "1234");
        //assertions
        assertNotNull(getStudent);
        assertEquals("phakocy@decagon.dev" , getStudent.getEmail());
        assertEquals("1234" , getStudent.getPassword());
        verify(studentRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }
}
