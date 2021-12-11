package com.ikechukwu.springschoolmanagement.models;

import com.ikechukwu.springschoolmanagement.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private String dob;
    private String address;
    private String email;
    private String password;
    private Position position;
    private int salary;

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
