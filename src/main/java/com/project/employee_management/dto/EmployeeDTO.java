package com.project.employee_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee Data Transfer Object")
public class EmployeeDTO {

    @Schema(description = "Unique identifier of the employee in database", example = "1")
    private Long id;

    @NotBlank(message = "Employee ID is required")
    @Schema(description = "Unique employee identifier", example = "EMP001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String employeeId;

    @NotBlank(message = "First name is required")
    @Schema(description = "Employee's first name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Schema(description = "Employee's last name", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "Employee's email address", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Role is required")
    @Schema(description = "Employee's role", example = "Software Engineer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String role;

    @NotNull(message = "Status is required")
    @Schema(description = "Employee's current status", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"ACTIVE", "BENCH", "RESIGNED"})
    private String status;

    @NotBlank(message = "Primary skill is required")
    @Schema(description = "Employee's primary skill", example = "Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String primarySkill;

    @Schema(description = "Employee's secondary skill", example = "Python")
    private String secondarySkill;

    @Schema(description = "Date when employee record was created", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dateCreated;

    @Schema(description = "Date when employee record was last updated", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dateUpdated;
}
