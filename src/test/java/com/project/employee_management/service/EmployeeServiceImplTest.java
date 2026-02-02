package com.project.employee_management.service;

import com.project.employee_management.dto.EmployeeDTO;
import com.project.employee_management.entity.Employee;
import com.project.employee_management.entity.Employee.EmployeeStatus;
import com.project.employee_management.exception.ResourceNotFoundException;
import com.project.employee_management.repository.EmployeeRepository;
import com.project.employee_management.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeId("EMP001");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setRole("Software Engineer");
        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setPrimarySkill("Java");
        employee.setSecondarySkill("Python");
        employee.setDateCreated(LocalDateTime.now());
        employee.setDateUpdated(LocalDateTime.now());

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setEmployeeId("EMP001");
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setRole("Software Engineer");
        employeeDTO.setStatus("ACTIVE");
        employeeDTO.setPrimarySkill("Java");
        employeeDTO.setSecondarySkill("Python");
    }

    @Test
    void createEmployee_Success() {
        // Arrange
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeRepository.existsByEmployeeId(anyString())).thenReturn(false);
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        // Act
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals("EMP001", result.getEmployeeId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_EmailAlreadyExists_ThrowsException() {
        // Arrange
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(employeeDTO);
        });
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void createEmployee_EmployeeIdAlreadyExists_ThrowsException() {
        // Arrange
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeRepository.existsByEmployeeId(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(employeeDTO);
        });
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_Success() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("EMP001", result.getEmployeeId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_NotFound_ThrowsException() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
    }

    @Test
    void updateEmployee_Success() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        // Act
        EmployeeDTO result = employeeService.updateEmployee(1L, employeeDTO);

        // Assert
        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_NotFound_ThrowsException() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, employeeDTO);
        });
    }

    @Test
    void deleteEmployee_Success() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployee_NotFound_ThrowsException() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(1L);
        });
        verify(employeeRepository, never()).deleteById(anyLong());
    }

    @Test
    void listEmployees_Success() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        // Act
        List<EmployeeDTO> result = employeeService.listEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void listEmployeesByRole_Success() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findByRole("Software Engineer")).thenReturn(employees);
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        // Act
        List<EmployeeDTO> result = employeeService.listEmployeesByRole("Software Engineer");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByRole("Software Engineer");
    }

    @Test
    void listEmployeesByStatus_Success() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findByStatus(EmployeeStatus.ACTIVE)).thenReturn(employees);
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        // Act
        List<EmployeeDTO> result = employeeService.listEmployeesByStatus("ACTIVE");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByStatus(EmployeeStatus.ACTIVE);
    }
}
