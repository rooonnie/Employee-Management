package com.project.employee_management.service;

import com.project.employee_management.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    
    EmployeeDTO getEmployeeById(Long id);
    
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    
    void deleteEmployee(Long id);
    
    List<EmployeeDTO> listEmployees();
    
    List<EmployeeDTO> listEmployeesByRole(String role);
    
    List<EmployeeDTO> listEmployeesByStatus(String status);
}
