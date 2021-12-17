package com.ikechukwu.springschoolmanagement.models;

import com.ikechukwu.springschoolmanagement.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String dob;
    @Column(nullable = false)
    private String behaviour = "Fair";
    private double sessionAverage = 00.00;
    private Grade grade;
    private int gradeFee;
    private int applyScore = 0;
    private String applyStatus = "Applicant";
    private String status = "ACTIVE";

    public String formatString(String name) {
        String raw = name;
        String[] rawArr = raw.split("");
        String finAns = "";

        for (int i = 0; i < 1; i++) {
            StringBuilder sb = new StringBuilder(rawArr[i]);
            rawArr[i] = String.valueOf(sb).toUpperCase();
        }

        for(String el : rawArr) {
            finAns+=el;
        }
        return finAns.trim();
    }
}
