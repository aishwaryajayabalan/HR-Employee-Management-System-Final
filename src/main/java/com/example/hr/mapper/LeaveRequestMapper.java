
package com.example.hr.mapper;

import org.springframework.stereotype.Component;

import com.example.hr.dto.request.LeaveRequestDto;
import com.example.hr.dto.response.LeaveResponse;
import com.example.hr.entity.LeaveRequest;

@Component
public class LeaveRequestMapper {

    public LeaveRequest toEntity(LeaveRequestDto request) {
        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setLeaveType(request.leaveType());
        leaveRequest.setStartDate(request.startDate());
        leaveRequest.setEndDate(request.endDate());
        leaveRequest.setReason(request.reason());

        return leaveRequest;
    }

    public void updateEntity(
            LeaveRequest leaveRequest,
            LeaveRequestDto request) {

        leaveRequest.setLeaveType(request.leaveType());
        leaveRequest.setStartDate(request.startDate());
        leaveRequest.setEndDate(request.endDate());
        leaveRequest.setReason(request.reason());
    }

    public LeaveResponse toResponse(LeaveRequest leaveRequest) {
        return new LeaveResponse(
                leaveRequest.getId(),
                leaveRequest.getLeaveType(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus(),
                leaveRequest.getReviewComments(),
                leaveRequest.getEmployee().getId(),
                leaveRequest.getEmployee().getFirstName() + " "
                        + leaveRequest.getEmployee().getLastName(),
                leaveRequest.getCreatedAt(),
                leaveRequest.getUpdatedAt()
        );
    }
}
