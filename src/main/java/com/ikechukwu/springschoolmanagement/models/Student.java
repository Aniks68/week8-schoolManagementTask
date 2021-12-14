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
