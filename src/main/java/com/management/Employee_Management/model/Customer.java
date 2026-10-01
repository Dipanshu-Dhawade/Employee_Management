package com.management.Employee_Management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users usersId;

    private String address;
}