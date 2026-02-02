package com.project.employee_management.repository;

import com.project.employee_management.entity.Employee;
import com.project.employee_management.entity.Employee.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by employee ID
     */
    Optional<Employee> findByEmployeeId(String employeeId);

    /**
     * Find employees by role
     */
    List<Employee> findByRole(String role);

    /**
     * Find employees by status
     */
    List<Employee> findByStatus(EmployeeStatus status);

    /**
     * Find employees by primary skill
     */
    List<Employee> findByPrimarySkill(String primarySkill);

    /**
     * Find employees by secondary skill
     */
    List<Employee> findBySecondarySkill(String secondarySkill);

    /**
     * Check if email already exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if employee ID already exists
     */
    boolean existsByEmployeeId(String employeeId);
}
