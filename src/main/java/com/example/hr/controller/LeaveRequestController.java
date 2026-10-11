package com.example.hr.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hr.dto.request.LeaveRequestDto;
import com.example.hr.dto.response.LeaveResponse;
import com.example.hr.enums.LeaveStatus;
import com.example.hr.service.LeaveRequestService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping
    public ResponseEntity<LeaveResponse> applyForLeave(
            @Valid @RequestBody LeaveRequestDto request) {

        LeaveResponse response =
                leaveRequestService.applyForLeave(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponse> getLeaveRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveRequestService.getLeaveRequestById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LeaveResponse>> getAllLeaveRequests(
            @PageableDefault(size = 10, sort = "startDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                leaveRequestService.getAllLeaveRequests(pageable));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<LeaveResponse>> getLeaveRequestsByEmployee(
            @PathVariable Long employeeId,
            @PageableDefault(size = 10, sort = "startDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                leaveRequestService.getLeaveRequestsByEmployee(
                        employeeId, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<LeaveResponse>> getLeaveRequestsByStatus(
            @PathVariable LeaveStatus status,
            @PageableDefault(size = 10, sort = "startDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                leaveRequestService.getLeaveRequestsByStatus(
                        status, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveResponse> updateLeaveRequest(
            @PathVariable Long id,
            @Valid @RequestBody LeaveRequestDto request) {

        return ResponseEntity.ok(
                leaveRequestService.updateLeaveRequest(id, request));
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<LeaveResponse> reviewLeaveRequest(
            @PathVariable Long id,
            @RequestParam LeaveStatus status,
            @RequestParam(required = false) String reviewComments) {

        return ResponseEntity.ok(
                leaveRequestService.reviewLeaveRequest(
                        id, status, reviewComments));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(
            @PathVariable Long id) {

        leaveRequestService.deleteLeaveRequest(id);

        return ResponseEntity.noContent().build();
    }
}

