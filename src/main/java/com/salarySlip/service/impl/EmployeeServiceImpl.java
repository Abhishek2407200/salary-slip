package com.salarySlip.service.impl;

import com.salarySlip.entity.Employee;
import com.salarySlip.exception.ResourceNotFoundException;
import com.salarySlip.payload.AllEmployeesList;
import com.salarySlip.payload.EmployeeDto;
import com.salarySlip.repository.EmployeeRepository;
import com.salarySlip.service.EmployeeService;
import com.salarySlip.utilities.PdfGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private ModelMapper mapper;
    private EmployeeRepository repository;
    private PdfGenerator pdfGenerator;

    public EmployeeServiceImpl(EmployeeRepository repository,ModelMapper mapper,PdfGenerator pdfGenerator) {
        this.mapper = mapper;
        this.repository = repository;
        this.pdfGenerator = pdfGenerator;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee savedEmployee = repository.save(mapToEntity(employeeDto));
        return mapToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
        employee.setEmpName(employeeDto.getEmpName());
        employee.setEmpType(employeeDto.getEmpType());
        employee.setBasicSalary(employeeDto.getBasicSalary());
        employee.setHra(employeeDto.getHra());
        employee.setPa(employeeDto.getPa());
        employee.setBonuses(employeeDto.getBonuses());
        employee.setAllowances(employeeDto.getAllowances());
        employee.setTax(employeeDto.getTax());
        employee.setPf(employeeDto.getPf());

        Employee savedEmployee = repository.save(employee);

        return mapToDto(savedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
        repository.delete(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
        return mapToDto(employee);
    }

    @Override
    public AllEmployeesList getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC,sortBy) : Sort.by(Sort.Direction.DESC,sortBy);
        Pageable p = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> all = repository.findAll(p);

        List<Employee> employees = all.getContent();

        List<EmployeeDto> dtos = employees.stream().map(emp -> mapToDto(emp)).collect(Collectors.toList());
        AllEmployeesList employeeList = new AllEmployeesList();
        employeeList.setEmployees(dtos);
        employeeList.setTotalPages(all.getTotalPages());
        employeeList.setTotalElements(all.getTotalElements());
        employeeList.setPageNo(p.getPageNumber());
        employeeList.setLastPage(all.isLast());
        return employeeList;
    }

    @Override
    public ByteArrayInputStream generateSalarySlip(Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
        ByteArrayInputStream byteArrayInputStream = pdfGenerator.generateSalarySlip(employee);
        return byteArrayInputStream;
    }

    Employee mapToEntity(EmployeeDto dto){
        Employee employee = mapper.map(dto, Employee.class);
        return employee;
    }

    EmployeeDto mapToDto(Employee employee){
        EmployeeDto dto  = mapper.map(employee, EmployeeDto.class);
        return dto;
    }
}
