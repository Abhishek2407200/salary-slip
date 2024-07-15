package com.salarySlip.service;

import com.salarySlip.payload.AllEmployeesList;
import com.salarySlip.payload.EmployeeDto;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface EmployeeService {

    //create Employee
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    //update Employee
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    //delete Employee
    void deleteEmployee(Long id);

    //get Employee by id
    EmployeeDto getEmployeeById(Long id);

    //get all Employees
    AllEmployeesList getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir);

    //generate pdf salarySlip for employee
    ByteArrayInputStream generateSalarySlip(Long id);
}
