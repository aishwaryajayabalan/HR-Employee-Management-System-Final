package com.example.hr.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.hr.dto.request.AttendanceRequest;
import com.example.hr.dto.response.AttendanceResponse;
import com.example.hr.enums.AttendanceStatus;

public interface AttendanceService {

    AttendanceResponse markAttendance(AttendanceRequest request);

    AttendanceResponse getAttendanceById(Long id);

    Page<AttendanceResponse> getAllAttendance(Pageable pageable);

    Page<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId, Pageable pageable);

    Page<AttendanceResponse> getAttendanceByDateRange(
            LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<AttendanceResponse> getAttendanceByStatus(
            AttendanceStatus status, Pageable pageable);

    AttendanceResponse updateAttendance(Long id, AttendanceRequest request);

    void deleteAttendance(Long id);
}

