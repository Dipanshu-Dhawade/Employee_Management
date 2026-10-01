package com.management.Employee_Management.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long exprienceId;

    @Column(nullable = false)
    private  String companyName;
    private  LocalDate startDate;
    private  LocalDate endDate;
    private  String currentlyWorking;
    @Column(nullable = false)
    private boolean deleted =false;

    //foreign Key
    @ManyToOne
    @JoinColumn(name = "employeeid")
    private Employee employee;

}
