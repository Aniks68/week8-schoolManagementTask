package com.ikechukwu.springschoolmanagement.models;

import com.ikechukwu.springschoolmanagement.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private String address;
    private String email;
    private String password;
    private String dob;
    private static String behaviour = "Fair";
    private static double sessionAverage;
    private Grade grade;
    private int gradeFee;
    private String status = "Active";

}
