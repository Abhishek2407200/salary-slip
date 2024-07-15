package com.salarySlip.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "emp_salary")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "emp_type")
    private String empType;
    @Column(name = "basic_salary")
    private double basicSalary;
    private double hra;
    private double pa;
    private double bonuses;
    private double allowances;
    private double tax;
    private double pf;
    @Column(name = "gross_salary")
    private double grossSalary;

    @PrePersist
    @PreUpdate
    private void updateGrossSalary() {
        this.grossSalary = basicSalary + hra + pa + bonuses + allowances + tax + pf;
    }

}
