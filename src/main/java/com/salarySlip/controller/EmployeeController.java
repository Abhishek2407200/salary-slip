package com.salarySlip.controller;

import com.salarySlip.payload.AllEmployeesList;
import com.salarySlip.payload.EmployeeDto;
import com.salarySlip.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;


@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createNewEmployee(@Valid @RequestBody EmployeeDto employee, BindingResult result){
        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        EmployeeDto dto = service.createEmployee(employee);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDto employee,@RequestParam long id,BindingResult result){

        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        EmployeeDto dto = service.updateEmployee(id,employee);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(@RequestParam long id){
        service.deleteEmployee(id);
        return new ResponseEntity<>("Employee Deleted successfully with id: " + id, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long id){
        EmployeeDto employee = service.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AllEmployeesList> getAllEmployees(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2",required = false)int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "empName",required = false)String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        AllEmployeesList allEmployees = service.getAllEmployees(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/salary-slip/{id}")
    public ResponseEntity<InputStreamResource> getSalarySlip(@PathVariable long id){
        ByteArrayInputStream salarySlip = service.generateSalarySlip(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline; filename=salary-slip.pdf");
        headers.add("Content-Type","application/pdf");
        return new ResponseEntity<>(new InputStreamResource(salarySlip), headers, HttpStatus.OK);
    }
}
