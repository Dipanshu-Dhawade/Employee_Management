package com.management.Employee_Management.model;

import com.management.Employee_Management.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long employeeId;

    @Column(unique = true,nullable = false)
    private  String employeeCode;

    private  String firstName;

    private String lastName;

    private String phone;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate dateOfJoining;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @Column(nullable = false)
    private boolean deleted =false;

    //foreign Key
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name ="designation_id")
    private Designation designation;




    //map by Employee
    @OneToMany(mappedBy ="employee" ,cascade = CascadeType.REMOVE)
    private List<EmployeeDocument> employeeDoc;
    @OneToMany(mappedBy ="employee" ,cascade = CascadeType.REMOVE)
    private  List<EmployeeEducation> employeeEdu;
    @OneToMany(mappedBy = "employee" ,cascade = CascadeType.REMOVE)
    private  List<EmployeeExperience> employeeExpe;
}
