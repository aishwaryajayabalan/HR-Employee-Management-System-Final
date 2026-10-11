package com.example.hr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import com.example.hr.dto.request.EmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.entity.Department;
import com.example.hr.entity.Employee;
import com.example.hr.enums.EmployeeStatus;
import com.example.hr.exception.BusinessRuleException;
import com.example.hr.exception.DuplicateResourceException;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.mapper.EmployeeMapper;
import com.example.hr.repository.DepartmentRepository;
import com.example.hr.repository.EmployeeRepository;
import com.example.hr.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmployeeMapper employeeMapper) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        logger.info("Creating employee with number: {}",
                request.employeeNumber());

        checkEmployeeNumber(request.employeeNumber(), null);
        checkEmployeeEmail(request.email(), null);

        Department department = findDepartment(request.departmentId());

        Employee employee = employeeMapper.toEntity(request);
        employee.setDepartment(department);
        employee.setStatus(EmployeeStatus.ACTIVE);

        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {}",
                savedEmployee.getId());

        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    @Cacheable(value = "employees", key = "#id")
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
    	logger.info("Loading employee from database for ID: {}", id);
        return employeeMapper.toResponse(findEmployee(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> searchEmployees(
            String keyword, Pageable pageable) {

        if (!StringUtils.hasText(keyword)) {
            throw new BusinessRuleException(
                    "Employee search keyword must not be blank.");
        }

        return employeeRepository.searchEmployees(
                        keyword.trim(), pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployeesByStatus(
            EmployeeStatus status, Pageable pageable) {

        if (status == null) {
            throw new BusinessRuleException(
                    "Employee status must not be null.");
        }

        return employeeRepository.findByStatus(status, pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployeesByDepartment(
            Long departmentId, Pageable pageable) {

        findDepartment(departmentId);

        return employeeRepository.findByDepartmentId(
                        departmentId, pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    @CacheEvict(value = "employees", key = "#id")
    public EmployeeResponse updateEmployee(
            Long id, EmployeeRequest request) {

        Employee employee = findEmployee(id);

        checkEmployeeNumber(request.employeeNumber(), id);
        checkEmployeeEmail(request.email(), id);

        Department department = findDepartment(request.departmentId());

        employeeMapper.updateEntity(employee, request);
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);

        logger.info("Employee updated successfully with ID: {}", id);

        return employeeMapper.toResponse(updatedEmployee);
    }

    @Override
    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id) {

        Employee employee = findEmployee(id);

        employeeRepository.delete(employee);

        logger.info("Employee deleted successfully with ID: {}", id);
    }

    private Employee findEmployee(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "Employee ID must be a positive number.");
        }

        return employeeRepository.findByIdWithDepartment(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + id));
    }

    private Department findDepartment(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "Department ID must be a positive number.");
        }

        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with ID: " + id));
    }

    private void checkEmployeeNumber(String employeeNumber, Long currentId) {

        employeeRepository.findByEmployeeNumberIgnoreCase(employeeNumber)
                .ifPresent(existing -> {
                    if (currentId == null
                            || !existing.getId().equals(currentId)) {

                        throw new DuplicateResourceException(
                                "Employee number already exists: "
                                        + employeeNumber);
                    }
                });
    }

    private void checkEmployeeEmail(String email, Long currentId) {

        employeeRepository.findByEmailIgnoreCase(email)
                .ifPresent(existing -> {
                    if (currentId == null
                            || !existing.getId().equals(currentId)) {

                        throw new DuplicateResourceException(
                                "Employee email already exists: " + email);
                    }
                });
    }
}

