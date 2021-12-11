package com.ikechukwu.springschoolmanagement.enums;

public enum Grade {
    GRADE1(1, 72500), GRADE2(2, 72500),
    GRADE3(3, 72500), GRADE4(4, 77500),
    GRADE5(5, 77500), GRADE6(6, 82500);

    private int grade;
    private final int gradeFee;

    Grade(int grade, int gradeFee) {
        this.grade = grade;
        this.gradeFee = gradeFee;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGradeFee() {
        return gradeFee;
    }
}
