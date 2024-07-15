package com.salarySlip.payload;

import lombok.Data;

import java.util.List;

@Data
public class AllEmployeesList {
    private List<EmployeeDto> employees;
    private int pageNo;
    private long totalPages;
    private long totalElements;
    private boolean isLastPage;
}
