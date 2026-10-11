package com.example.hr.service.impl;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.dto.request.LeaveRequestDto;
import com.example.hr.dto.response.LeaveResponse;
import com.example.hr.entity.Employee;
import com.example.hr.entity.LeaveRequest;
import com.example.hr.enums.LeaveStatus;
import com.example.hr.exception.BusinessRuleException;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.mapper.LeaveRequestMapper;
import com.example.hr.repository.EmployeeRepository;
import com.example.hr.repository.LeaveRequestRepository;
import com.example.hr.service.LeaveRequestService;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private static final Logger logger =
            LoggerFactory.getLogger(LeaveRequestServiceImpl.class);

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestMapper leaveRequestMapper;

    public LeaveRequestServiceImpl(
            LeaveRequestRepository leaveRequestRepository,
            EmployeeRepository employeeRepository,
            LeaveRequestMapper leaveRequestMapper) {

        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.leaveRequestMapper = leaveRequestMapper;
    }

    @Override
    public LeaveResponse applyForLeave(LeaveRequestDto request) {

        validateDateRange(request.startDate(), request.endDate());

        Employee employee = findEmployee(request.employeeId());

        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(request);
        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING);

        LeaveRequest savedRequest =
                leaveRequestRepository.save(leaveRequest);

        logger.info("Leave request created with ID: {}",
                savedRequest.getId());

        return leaveRequestMapper.toResponse(savedRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveResponse getLeaveRequestById(Long id) {

        return leaveRequestMapper.toResponse(findLeaveRequest(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeaveResponse> getAllLeaveRequests(Pageable pageable) {

        return leaveRequestRepository.findAll(pageable)
                .map(leaveRequestMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeaveResponse> getLeaveRequestsByEmployee(
            Long employeeId, Pageable pageable) {

        findEmployee(employeeId);

        return leaveRequestRepository.findByEmployeeId(
                        employeeId, pageable)
                .map(leaveRequestMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeaveResponse> getLeaveRequestsByStatus(
            LeaveStatus status, Pageable pageable) {

        if (status == null) {
            throw new BusinessRuleException(
                    "Leave status must not be null.");
        }

        return leaveRequestRepository.findByStatus(status, pageable)
                .map(leaveRequestMapper::toResponse);
    }

    @Override
    public LeaveResponse updateLeaveRequest(
            Long id, LeaveRequestDto request) {

        LeaveRequest leaveRequest = findLeaveRequest(id);

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new BusinessRuleException(
                    "Only pending leave requests can be updated.");
        }

        validateDateRange(request.startDate(), request.endDate());

        Employee employee = findEmployee(request.employeeId());

        leaveRequestMapper.updateEntity(leaveRequest, request);
        leaveRequest.setEmployee(employee);

        LeaveRequest updatedRequest =
                leaveRequestRepository.save(leaveRequest);

        logger.info("Leave request updated with ID: {}", id);

        return leaveRequestMapper.toResponse(updatedRequest);
    }

    @Override
    public LeaveResponse reviewLeaveRequest(
            Long id, LeaveStatus status, String reviewComments) {

        LeaveRequest leaveRequest = findLeaveRequest(id);

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new BusinessRuleException(
                    "Only pending leave requests can be reviewed.");
        }

        if (status != LeaveStatus.APPROVED
                && status != LeaveStatus.REJECTED) {
            throw new BusinessRuleException(
                    "Review status must be APPROVED or REJECTED.");
        }

        leaveRequest.setStatus(status);
        leaveRequest.setReviewComments(reviewComments);

        LeaveRequest reviewedRequest =
                leaveRequestRepository.save(leaveRequest);

        logger.info("Leave request {} reviewed with status {}",
                id, status);

        return leaveRequestMapper.toResponse(reviewedRequest);
    }

    @Override
    public void deleteLeaveRequest(Long id) {

        LeaveRequest leaveRequest = findLeaveRequest(id);

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new BusinessRuleException(
                    "Only pending leave requests can be deleted.");
        }

        leaveRequestRepository.delete(leaveRequest);

        logger.info("Leave request deleted with ID: {}", id);
    }

    private LeaveRequest findLeaveRequest(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "Leave request ID must be a positive number.");
        }

        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leave request not found with ID: " + id));
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

    private void validateDateRange(
            LocalDate startDate, LocalDate endDate) {

        if (startDate == null || endDate == null) {
            throw new BusinessRuleException(
                    "Leave start date and end date are required.");
        }

        if (startDate.isAfter(endDate)) {
            throw new BusinessRuleException(
                    "Leave start date cannot be after end date.");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new BusinessRuleException(
                    "Leave start date cannot be in the past.");
        }
    }
}

