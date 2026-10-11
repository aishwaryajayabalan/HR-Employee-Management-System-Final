
package com.example.hr.mapper;

import org.springframework.stereotype.Component;

import com.example.hr.dto.request.AttendanceRequest;
import com.example.hr.dto.response.AttendanceResponse;
import com.example.hr.entity.Attendance;

@Component
public class AttendanceMapper {

    public Attendance toEntity(AttendanceRequest request) {
        Attendance attendance = new Attendance();

        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());
        attendance.setWorkingHours(request.workingHours());
        attendance.setRemarks(request.remarks());

        return attendance;
    }

    public void updateEntity(
            Attendance attendance,
            AttendanceRequest request) {

        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());
        attendance.setWorkingHours(request.workingHours());
        attendance.setRemarks(request.remarks());
    }

    public AttendanceResponse toResponse(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getAttendanceDate(),
                attendance.getStatus(),
                attendance.getWorkingHours(),
                attendance.getRemarks(),
                attendance.getEmployee().getId(),
                attendance.getEmployee().getFirstName() + " "
                        + attendance.getEmployee().getLastName(),
                attendance.getCreatedAt(),
                attendance.getUpdatedAt()
        );
    }
}

