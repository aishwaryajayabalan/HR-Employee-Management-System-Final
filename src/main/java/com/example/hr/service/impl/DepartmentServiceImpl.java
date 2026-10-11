package com.example.hr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.hr.dto.request.DepartmentRequest;
import com.example.hr.dto.response.DepartmentResponse;
import com.example.hr.entity.Department;
import com.example.hr.exception.BusinessRuleException;
import com.example.hr.exception.DuplicateResourceException;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.mapper.DepartmentMapper;
import com.example.hr.repository.DepartmentRepository;
import com.example.hr.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper) {

        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        logger.info("Creating department with code: {}",
                request.departmentCode());

        checkDepartmentCode(request.departmentCode(), null);
        checkDepartmentName(request.departmentName(), null);

        Department department = departmentMapper.toEntity(request);

        Department savedDepartment =
                departmentRepository.save(department);

        logger.info("Department created successfully with ID: {}",
                savedDepartment.getId());

        return departmentMapper.toResponse(savedDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(Long id) {

        return departmentMapper.toResponse(findDepartment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponse> getAllDepartments(Pageable pageable) {

        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> searchDepartments(String name) {

        if (!StringUtils.hasText(name)) {
            throw new BusinessRuleException(
                    "Department search name must not be blank.");
        }

        return departmentRepository.searchByDepartmentName(name.trim())
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    public DepartmentResponse updateDepartment(
            Long id, DepartmentRequest request) {

        Department department = findDepartment(id);

        checkDepartmentCode(request.departmentCode(), id);
        checkDepartmentName(request.departmentName(), id);

        departmentMapper.updateEntity(department, request);

        Department updatedDepartment =
                departmentRepository.save(department);

        logger.info("Department updated successfully with ID: {}", id);

        return departmentMapper.toResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = findDepartment(id);

        if (department.getEmployees() != null
                && !department.getEmployees().isEmpty()) {

            throw new BusinessRuleException(
                    "Cannot delete a department that has employees.");
        }

        departmentRepository.delete(department);

        logger.info("Department deleted successfully with ID: {}", id);
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

    private void checkDepartmentCode(String code, Long currentId) {

        departmentRepository.findByDepartmentCodeIgnoreCase(code)
                .ifPresent(existing -> {
                    if (currentId == null
                            || !existing.getId().equals(currentId)) {

                        throw new DuplicateResourceException(
                                "Department code already exists: " + code);
                    }
                });
    }

    private void checkDepartmentName(String name, Long currentId) {

        departmentRepository.findByDepartmentNameIgnoreCase(name)
                .ifPresent(existing -> {
                    if (currentId == null
                            || !existing.getId().equals(currentId)) {

                        throw new DuplicateResourceException(
                                "Department name already exists: " + name);
                    }
                });
    }
}
