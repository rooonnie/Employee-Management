package com.project.employee_management.service.impl;

import com.project.employee_management.dto.EmployeeDTO;
import com.project.employee_management.entity.Employee;
import com.project.employee_management.entity.Employee.EmployeeStatus;
import com.project.employee_management.exception.ResourceNotFoundException;
import com.project.employee_management.repository.EmployeeRepository;
import com.project.employee_management.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.debug("Creating new employee with employeeId: {}", employeeDTO.getEmployeeId());
        
        // Check if email already exists
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + employeeDTO.getEmail());
        }
        
        // Check if employee ID already exists
        if (employeeRepository.existsByEmployeeId(employeeDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Employee ID already exists: " + employeeDTO.getEmployeeId());
        }
        
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setStatus(EmployeeStatus.valueOf(employeeDTO.getStatus()));
        
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created successfully with id: {}", savedEmployee.getId());
        
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        log.debug("Fetching employee with id: {}", id);
        
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        dto.setStatus(employee.getStatus().name());
        
        return dto;
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.debug("Updating employee with id: {}", id);
        
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!existingEmployee.getEmail().equals(employeeDTO.getEmail()) &&
                employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + employeeDTO.getEmail());
        }
        
        // Check if employee ID is being changed and if new employee ID already exists
        if (!existingEmployee.getEmployeeId().equals(employeeDTO.getEmployeeId()) &&
                employeeRepository.existsByEmployeeId(employeeDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Employee ID already exists: " + employeeDTO.getEmployeeId());
        }
        
        // Update fields
        existingEmployee.setEmployeeId(employeeDTO.getEmployeeId());
        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setRole(employeeDTO.getRole());
        existingEmployee.setStatus(EmployeeStatus.valueOf(employeeDTO.getStatus()));
        existingEmployee.setPrimarySkill(employeeDTO.getPrimarySkill());
        existingEmployee.setSecondarySkill(employeeDTO.getSecondarySkill());
        
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        log.info("Employee updated successfully with id: {}", updatedEmployee.getId());
        
        EmployeeDTO dto = modelMapper.map(updatedEmployee, EmployeeDTO.class);
        dto.setStatus(updatedEmployee.getStatus().name());
        
        return dto;
    }

    @Override
    public void deleteEmployee(Long id) {
        log.debug("Deleting employee with id: {}", id);
        
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        
        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> listEmployees() {
        log.debug("Fetching all employees");
        
        return employeeRepository.findAll().stream()
                .map(employee -> {
                    EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
                    dto.setStatus(employee.getStatus().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> listEmployeesByRole(String role) {
        log.debug("Fetching employees by role: {}", role);
        
        return employeeRepository.findByRole(role).stream()
                .map(employee -> {
                    EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
                    dto.setStatus(employee.getStatus().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> listEmployeesByStatus(String status) {
        log.debug("Fetching employees by status: {}", status);
        
        EmployeeStatus employeeStatus = EmployeeStatus.valueOf(status);
        return employeeRepository.findByStatus(employeeStatus).stream()
                .map(employee -> {
                    EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
                    dto.setStatus(employee.getStatus().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
