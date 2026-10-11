package com.example.hr.service.impl;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.dto.request.AttendanceRequest;
import com.example.hr.dto.response.AttendanceResponse;
import com.example.hr.entity.Attendance;
import com.example.hr.entity.Employee;
import com.example.hr.enums.AttendanceStatus;
import com.example.hr.exception.BusinessRuleException;
import com.example.hr.exception.DuplicateResourceException;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.mapper.AttendanceMapper;
import com.example.hr.repository.AttendanceRepository;
import com.example.hr.repository.EmployeeRepository;
import com.example.hr.service.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger logger =
            LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository,
            AttendanceMapper attendanceMapper) {

        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public AttendanceResponse markAttendance(AttendanceRequest request) {

        validateAttendanceDate(request.attendanceDate());

        Employee employee = findEmployee(request.employeeId());

        if (attendanceRepository.existsByEmployeeIdAndAttendanceDate(
                request.employeeId(), request.attendanceDate())) {
            throw new DuplicateResourceException(
                    "Attendance already exists for this employee on "
                            + request.attendanceDate());
        }

        Attendance attendance = attendanceMapper.toEntity(request);
        attendance.setEmployee(employee);

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        logger.info("Attendance created with ID: {}",
                savedAttendance.getId());

        return attendanceMapper.toResponse(savedAttendance);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponse getAttendanceById(Long id) {

        return attendanceMapper.toResponse(findAttendance(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponse> getAllAttendance(Pageable pageable) {

        return attendanceRepository.findAll(pageable)
                .map(attendanceMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId, Pageable pageable) {

        findEmployee(employeeId);

        return attendanceRepository.findByEmployeeId(
                        employeeId, pageable)
                .map(attendanceMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponse> getAttendanceByDateRange(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        validateDateRange(startDate, endDate);

        return attendanceRepository.findByAttendanceDateBetween(
                        startDate, endDate, pageable)
                .map(attendanceMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponse> getAttendanceByStatus(
            AttendanceStatus status, Pageable pageable) {

        if (status == null) {
            throw new BusinessRuleException(
                    "Attendance status must not be null.");
        }

        return attendanceRepository.findByStatus(status, pageable)
                .map(attendanceMapper::toResponse);
    }

    @Override
    public AttendanceResponse updateAttendance(
            Long id, AttendanceRequest request) {

        Attendance attendance = findAttendance(id);

        validateAttendanceDate(request.attendanceDate());

        Employee employee = findEmployee(request.employeeId());

        attendanceRepository.findByEmployeeIdAndAttendanceDate(
                request.employeeId(), request.attendanceDate())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "Attendance already exists for this employee "
                                        + "on " + request.attendanceDate());
                    }
                });

        attendanceMapper.updateEntity(attendance, request);
        attendance.setEmployee(employee);

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        logger.info("Attendance updated with ID: {}", id);

        return attendanceMapper.toResponse(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Long id) {

        Attendance attendance = findAttendance(id);

        attendanceRepository.delete(attendance);

        logger.info("Attendance deleted with ID: {}", id);
    }

    private Attendance findAttendance(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "Attendance ID must be a positive number.");
        }

        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance not found with ID: " + id));
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

    private void validateAttendanceDate(LocalDate date) {

        if (date == null) {
            throw new BusinessRuleException(
                    "Attendance date must not be null.");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new BusinessRuleException(
                    "Attendance date cannot be in the future.");
        }
    }

    private void validateDateRange(
            LocalDate startDate, LocalDate endDate) {

        if (startDate == null || endDate == null) {
            throw new BusinessRuleException(
                    "Start date and end date are required.");
        }

        if (startDate.isAfter(endDate)) {
            throw new BusinessRuleException(
                    "Start date cannot be after end date.");
        }
    }
}

