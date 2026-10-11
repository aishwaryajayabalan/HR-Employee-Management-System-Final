
package com.example.hr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hr.entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {
	
	Optional<Department> findByDepartmentNameIgnoreCase(
	        String departmentName);

    boolean existsByDepartmentCodeIgnoreCase(String departmentCode);

    boolean existsByDepartmentNameIgnoreCase(String departmentName);

    Optional<Department> findByDepartmentCodeIgnoreCase(String departmentCode);

    @Query("""
        SELECT d
        FROM Department d
        WHERE LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :name, '%'))
        """)
    java.util.List<Department> searchByDepartmentName(
            @Param("name") String name);

    @Query(value = """
        SELECT COUNT(*)
        FROM departments
        WHERE status = :status
        """, nativeQuery = true)
    long countByStatusNative(@Param("status") String status);
}
