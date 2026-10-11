
package com.example.hr.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hr.entity.LeaveRequest;
import com.example.hr.enums.LeaveStatus;

public interface LeaveRequestRepository
        extends JpaRepository<LeaveRequest, Long> {

    Page<LeaveRequest> findByEmployeeId(
            Long employeeId, Pageable pageable);

    Page<LeaveRequest> findByStatus(
            LeaveStatus status, Pageable pageable);

    boolean existsByEmployeeIdAndStatus(
            Long employeeId, LeaveStatus status);

    @Query("""
        SELECT l FROM LeaveRequest l
        WHERE l.employee.id = :employeeId
          AND l.startDate <= :endDate
          AND l.endDate >= :startDate
        """)
    Page<LeaveRequest> findOverlappingLeaves(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    @Query("""
        SELECT l FROM LeaveRequest l
        WHERE l.status = :status
          AND l.startDate >= :fromDate
          AND l.startDate <= :toDate
        ORDER BY l.startDate ASC
        """)
    Page<LeaveRequest> findLeavesForApproval(
            @Param("status") LeaveStatus status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable);

    @Query(value = """
        SELECT COUNT(*)
        FROM leave_requests
        WHERE employee_id = :employeeId
          AND status = :status
        """, nativeQuery = true)
    long countByEmployeeAndStatusNative(
            @Param("employeeId") Long employeeId,
            @Param("status") String status);
}
