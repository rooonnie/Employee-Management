package com.project.employee_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.employee_management.dto.EmployeeDTO;
import com.project.employee_management.exception.ResourceNotFoundException;
import com.project.employee_management.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
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
        employeeDTO.setDateCreated(LocalDateTime.now());
        employeeDTO.setDateUpdated(LocalDateTime.now());
    }

    @Test
    void createEmployee_Success() throws Exception {
        // Arrange
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        // Act & Assert
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.employeeId", is("EMP001")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.role", is("Software Engineer")))
                .andExpect(jsonPath("$.status", is("ACTIVE")))
                .andExpect(jsonPath("$.primarySkill", is("Java")));

        verify(employeeService, times(1)).createEmployee(any(EmployeeDTO.class));
    }

    @Test
    void createEmployee_InvalidInput_ReturnsBadRequest() throws Exception {
        // Arrange - Create invalid DTO (missing required fields)
        EmployeeDTO invalidDTO = new EmployeeDTO();
        invalidDTO.setEmployeeId(""); // Empty employee ID

        // Act & Assert
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).createEmployee(any(EmployeeDTO.class));
    }

    @Test
    void getAllEmployees_Success() throws Exception {
        // Arrange
        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setId(2L);
        employee2.setEmployeeId("EMP002");
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("jane.smith@example.com");
        employee2.setRole("Product Manager");
        employee2.setStatus("ACTIVE");
        employee2.setPrimarySkill("Management");

        List<EmployeeDTO> employees = Arrays.asList(employeeDTO, employee2);
        when(employeeService.listEmployees()).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId", is("EMP001")))
                .andExpect(jsonPath("$[1].employeeId", is("EMP002")));

        verify(employeeService, times(1)).listEmployees();
    }

    @Test
    void getEmployeeById_Success() throws Exception {
        // Arrange
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeDTO);

        // Act & Assert
        mockMvc.perform(get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.employeeId", is("EMP001")))
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void getEmployeeById_NotFound() throws Exception {
        // Arrange
        when(employeeService.getEmployeeById(999L))
                .thenThrow(new ResourceNotFoundException("Employee not found with id: 999"));

        // Act & Assert
        mockMvc.perform(get("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById(999L);
    }

    @Test
    void updateEmployee_Success() throws Exception {
        // Arrange
        EmployeeDTO updatedDTO = new EmployeeDTO();
        updatedDTO.setId(1L);
        updatedDTO.setEmployeeId("EMP001");
        updatedDTO.setFirstName("John");
        updatedDTO.setLastName("Doe");
        updatedDTO.setEmail("john.doe@example.com");
        updatedDTO.setRole("Senior Software Engineer");
        updatedDTO.setStatus("ACTIVE");
        updatedDTO.setPrimarySkill("Java");

        when(employeeService.updateEmployee(eq(1L), any(EmployeeDTO.class))).thenReturn(updatedDTO);

        // Act & Assert
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role", is("Senior Software Engineer")));

        verify(employeeService, times(1)).updateEmployee(eq(1L), any(EmployeeDTO.class));
    }

    @Test
    void updateEmployee_NotFound() throws Exception {
        // Arrange
        when(employeeService.updateEmployee(eq(999L), any(EmployeeDTO.class)))
                .thenThrow(new ResourceNotFoundException("Employee not found with id: 999"));

        // Act & Assert
        mockMvc.perform(put("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).updateEmployee(eq(999L), any(EmployeeDTO.class));
    }

    @Test
    void deleteEmployee_Success() throws Exception {
        // Arrange
        doNothing().when(employeeService).deleteEmployee(1L);

        // Act & Assert
        mockMvc.perform(delete("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    void deleteEmployee_NotFound() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Employee not found with id: 999"))
                .when(employeeService).deleteEmployee(999L);

        // Act & Assert
        mockMvc.perform(delete("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).deleteEmployee(999L);
    }

    @Test
    void getEmployeesByRole_Success() throws Exception {
        // Arrange
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
        when(employeeService.listEmployeesByRole("Software Engineer")).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(get("/employees/role/Software Engineer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].role", is("Software Engineer")));

        verify(employeeService, times(1)).listEmployeesByRole("Software Engineer");
    }

    @Test
    void getEmployeesByStatus_Success() throws Exception {
        // Arrange
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
        when(employeeService.listEmployeesByStatus("ACTIVE")).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(get("/employees/status/ACTIVE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")));

        verify(employeeService, times(1)).listEmployeesByStatus("ACTIVE");
    }
}
