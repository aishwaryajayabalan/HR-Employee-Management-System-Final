
package com.example.hr.mapper;

import org.springframework.stereotype.Component;

import com.example.hr.dto.request.EmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.entity.Employee;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {
        Employee employee = new Employee();

        employee.setEmployeeNumber(request.employeeNumber());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setJobTitle(request.jobTitle());
        employee.setJoiningDate(request.joiningDate());
        employee.setSalary(request.salary());

        return employee;
    }

    public void updateEntity(
            Employee employee,
            EmployeeRequest request) {

        employee.setEmployeeNumber(request.employeeNumber());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setJobTitle(request.jobTitle());
        employee.setJoiningDate(request.joiningDate());
        employee.setSalary(request.salary());
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeNumber(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getJobTitle(),
                employee.getJoiningDate(),
                employee.getSalary(),
                employee.getStatus(),
                employee.getDepartment().getId(),
                employee.getDepartment().getDepartmentName(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}

