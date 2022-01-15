package com.ikechukwu.springschoolmanagement.enums;

public enum Position {
    BURSAR(65000, "Finance management"),
    SECRETARY(50000, "Communications management between public and school"),
    TYPIST(30000, "Preparation of school documents"),
    LIBRARIAN(30000, "Organise library books for school community"),
    DRIVER(40000, "Driving school bus"),
    CONDUCTRESS(37000, "Accompanying a driver to pick up and drop off students"),
    CLEANER(35000, "Sanitation of the school premises"),
    SECURITY(40000, "Ensuring the safety of persons and resources within the premises"),
    PRINCIPAL(85000, "School Management"),
    VICE_PRINCIPAL(75000, "Staff and student organisation"),
    TEACHER(55000, "Subject teaching");

    private final int salary;
    private final String jobDescriptor;

    Position(int salary, String jobDescriptor) {
        this.salary = salary;
        this.jobDescriptor = jobDescriptor;
    }

    public int getSalary() {
        return salary;
    }

    public String getJobDescriptor() {
        return jobDescriptor;
    }
}
