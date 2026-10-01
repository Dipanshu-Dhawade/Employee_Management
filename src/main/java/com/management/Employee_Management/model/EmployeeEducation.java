package com.management.Employee_Management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educationId;

    @Column(nullable = false)
    private String qualification;

    private String specialization;

    private String institutionName;

    private String universityName;

    private LocalDate passingYear;

    private Double percentage;

    private String grade;

    @Column(nullable = false)
    private boolean deleted =false;

    //foreign Key
    @ManyToOne
    @JoinColumn(name = "employeeid")
    private  Employee employee;

}