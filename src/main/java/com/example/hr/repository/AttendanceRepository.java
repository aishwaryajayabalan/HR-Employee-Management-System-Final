
package com.example.hr.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hr.entity.Attendance;
import com.example.hr.enums.AttendanceStatus;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeId(Long employeeId);

    Page<Attendance> findByEmployeeId(Long employeeId, Pageable pageable);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    Page<Attendance> findByAttendanceDateBetween(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable);

    Optional<Attendance> findByEmployeeIdAndAttendanceDate(
            Long employeeId,
            LocalDate attendanceDate);

    boolean existsByEmployeeIdAndAttendanceDate(
            Long employeeId,
            LocalDate attendanceDate);

    @Query("""
        SELECT a FROM Attendance a
        WHERE a.employee.id = :employeeId
          AND a.attendanceDate BETWEEN :startDate AND :endDate
        ORDER BY a.attendanceDate DESC
        """)
    List<Attendance> findEmployeeAttendanceHistory(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT COUNT(a) FROM Attendance a
        WHERE a.attendanceDate = :date
          AND a.status = :status
        """)
    long countByDateAndStatus(
            @Param("date") LocalDate date,
            @Param("status") AttendanceStatus status);

    @Query(value = """
        SELECT COUNT(*)
        FROM attendance
        WHERE attendance_date = :date
          AND status = :status
        """, nativeQuery = true)
    long countByDateAndStatusNative(
            @Param("date") LocalDate date,
            @Param("status") String status);
    
    Page<Attendance> findByStatus(
            AttendanceStatus status, Pageable pageable);
}

