package com.example.hr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.hr.dto.request.LeaveRequestDto;
import com.example.hr.dto.response.LeaveResponse;
import com.example.hr.enums.LeaveStatus;

public interface LeaveRequestService {

    LeaveResponse applyForLeave(LeaveRequestDto request);

    LeaveResponse getLeaveRequestById(Long id);

    Page<LeaveResponse> getAllLeaveRequests(Pageable pageable);

    Page<LeaveResponse> getLeaveRequestsByEmployee(
            Long employeeId, Pageable pageable);

    Page<LeaveResponse> getLeaveRequestsByStatus(
            LeaveStatus status, Pageable pageable);

    LeaveResponse updateLeaveRequest(Long id, LeaveRequestDto request);

    LeaveResponse reviewLeaveRequest(
            Long id, LeaveStatus status, String reviewComments);

    void deleteLeaveRequest(Long id);
}
