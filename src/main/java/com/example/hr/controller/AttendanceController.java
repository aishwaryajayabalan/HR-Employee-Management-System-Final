package com.example.hr.controller;

import java.time.LocalDate;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.example.hr.dto.request.AttendanceRequest;
import com.example.hr.dto.response.AttendanceResponse;
import com.example.hr.enums.AttendanceStatus;
import com.example.hr.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> markAttendance(
            @Valid @RequestBody AttendanceRequest request) {

        AttendanceResponse response =
                attendanceService.markAttendance(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getAttendanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AttendanceResponse>> getAllAttendance(
            @PageableDefault(size = 10, sort = "attendanceDate",
                    direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                attendanceService.getAllAttendance(pageable));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<AttendanceResponse>> getAttendanceByEmployee(
            @PathVariable Long employeeId,
            @PageableDefault(size = 10, sort = "attendanceDate",
                    direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByEmployee(
                        employeeId, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<AttendanceResponse>> getAttendanceByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,
            @PageableDefault(size = 10, sort = "attendanceDate",
                    direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByDateRange(
                        startDate, endDate, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<AttendanceResponse>> getAttendanceByStatus(
            @PathVariable AttendanceStatus status,
            @PageableDefault(size = 10, sort = "attendanceDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByStatus(status, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequest request) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return ResponseEntity.noContent().build();
    }
}

