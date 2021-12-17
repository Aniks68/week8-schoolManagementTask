package com.ikechukwu.springschoolmanagement.services.serviceImpl;

import com.ikechukwu.springschoolmanagement.enums.Position;
import com.ikechukwu.springschoolmanagement.models.Staff;
import com.ikechukwu.springschoolmanagement.models.Student;
import com.ikechukwu.springschoolmanagement.repository.StaffRepository;
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
class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;
    @InjectMocks
    private StaffServiceImpl staffServiceImpl;
    private Staff staff;
    @BeforeEach
    void setUp() {
        staff = new Staff();
        staff.setFirstname("Samuel"); staff.setLastname("Eldridge"); staff.setEmail("sam@el.com"); staff.setPosition(Position.VICE_PRINCIPAL);
        staff.setSalary(staff.getPosition().getSalary()); staff.setJobDescription(staff.getPosition().getJobDescriptor());
        staff.setAddress("Phase IV, Behind Blessed Chapel, Kubwa"); staff.setGender("Male"); staff.setPassword("12345");
        staff.setDob("1991-05-10"); staffServiceImpl.saveUser(staff);
    }

    @Test
    @DisplayName("Should check staff registration validation")
    void shouldGetStaffByEmail() {

        //mock userRepository
        when(staffRepository.findFirstByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(staff));
        //Call the method to be tested
        Staff staff1 = staffServiceImpl.regAuthenticate("sam@el.com");
        //assertions
        assertNotNull(staff1);
        assertEquals("sam@el.com" , staff1.getEmail());
        verify(staffRepository, times(1)).findFirstByEmail(anyString());
    }

    @Test
    @DisplayName("Should test if new staff is created")
    void shouldCreateStaff() {

        //mock userRepository
        when(staffRepository.save(any(Staff.class))).thenReturn(staff);
        //Call the method to be tested
        staffServiceImpl.saveUser(staff);
        //Assertions
        verify(staffRepository, times(2)).save(any(Staff.class));
    }

    @Test
    @DisplayName("Should return a staff after searching")
    void shouldGetUserByID() {

        //mock userRepository
        when(staffRepository.getById(anyLong())).thenReturn(staff);
        //Call the method to be tested
        Staff staff1 = staffServiceImpl.getStaff(1L);
        //assertions
        assertNotNull(staff1);
        assertEquals(staff, staff1);
        verify(staffRepository, times(1)).getById(anyLong());
    }

    @Test
    @DisplayName("Should check that login details match")
    void shouldGetStaffByEmailAndPassword() {

        //mock userRepository
        when(staffRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(java.util.Optional.ofNullable(staff));
        //Call the method to be tested
        Staff staff1 = staffServiceImpl.authenticate("sam@el.com", "12345");
        //assertions
        assertNotNull(staff1);
        assertEquals("sam@el.com" , staff1.getEmail());
        assertEquals("12345" , staff1.getPassword());
        verify(staffRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }
}