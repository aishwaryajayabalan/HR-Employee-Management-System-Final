
package com.example.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hr.entity.Employee;
import com.example.hr.enums.EmployeeStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeNumberIgnoreCase(String employeeNumber);

    Optional<Employee> findByEmailIgnoreCase(String email);

    boolean existsByEmployeeNumberIgnoreCase(String employeeNumber);

    boolean existsByEmailIgnoreCase(String email);

    Page<Employee> findByStatus(EmployeeStatus status, Pageable pageable);

    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    
    @Query(
        value = """
            SELECT e FROM Employee e
            WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """,
        countQuery = """
            SELECT COUNT(e) FROM Employee e
            WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """
    )
    Page<Employee> searchEmployees(
            @Param("keyword") String keyword,
            Pageable pageable);
    

    @Query("""
        SELECT e FROM Employee e
        JOIN FETCH e.department
        WHERE e.id = :id
        """)
    Optional<Employee> findByIdWithDepartment(@Param("id") Long id);

    @Query(value = """
        SELECT COUNT(*)
        FROM employees
        WHERE status = :status
        """, nativeQuery = true)
    long countByStatusNative(@Param("status") String status);

    @Query("""
        SELECT e FROM Employee e
        WHERE e.department.id = :departmentId
        ORDER BY e.lastName ASC, e.firstName ASC
        """)
    List<Employee> findEmployeesByDepartmentSorted(
            @Param("departmentId") Long departmentId);
}
