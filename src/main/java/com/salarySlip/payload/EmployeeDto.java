package com.salarySlip.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDto {
    private Long empId;

    @NotEmpty
    @Size(min = 3,message = "The employee name should be more than 3 characters")
    private String empName;
    @NotNull
    private String empType;
    @NotNull
    private double basicSalary;
    @NotNull
    private double hra;
    @NotNull
    private double pa;
    @NotNull
    private double bonuses;
    @NotNull
    private double allowances;

    @NotNull
    private double tax;
    @NotNull
    private double pf;
    private double grossSalary;
}
