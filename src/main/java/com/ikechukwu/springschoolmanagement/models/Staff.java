package com.ikechukwu.springschoolmanagement.models;

import com.ikechukwu.springschoolmanagement.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
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
    private String dob;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Position position;
    @Column(nullable = false)
    private String jobDescription;
    @Column(nullable = false)
    private int salary;
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
