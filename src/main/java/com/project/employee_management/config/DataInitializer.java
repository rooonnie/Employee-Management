package com.project.employee_management.config;

import com.project.employee_management.entity.Employee;
import com.project.employee_management.entity.Employee.EmployeeStatus;
import com.project.employee_management.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            log.info("Loading sample employee data...");

            Employee emp1 = new Employee();
            emp1.setEmployeeId("EMP001");
            emp1.setFirstName("Juan");
            emp1.setLastName("Dela Cruz");
            emp1.setEmail("juan.delacruz@company.com");
            emp1.setRole("Software Engineer");
            emp1.setStatus(EmployeeStatus.ACTIVE);
            emp1.setPrimarySkill("Java");
            emp1.setSecondarySkill("Spring Boot");

            Employee emp2 = new Employee();
            emp2.setEmployeeId("EMP002");
            emp2.setFirstName("Maria");
            emp2.setLastName("Santos");
            emp2.setEmail("maria.santos@company.com");
            emp2.setRole("Senior Developer");
            emp2.setStatus(EmployeeStatus.ACTIVE);
            emp2.setPrimarySkill("Python");
            emp2.setSecondarySkill("Django");

            Employee emp3 = new Employee();
            emp3.setEmployeeId("EMP003");
            emp3.setFirstName("Pedro");
            emp3.setLastName("Garcia");
            emp3.setEmail("pedro.garcia@company.com");
            emp3.setRole("Full Stack Developer");
            emp3.setStatus(EmployeeStatus.BENCH);
            emp3.setPrimarySkill("JavaScript");
            emp3.setSecondarySkill("React");

            Employee emp4 = new Employee();
            emp4.setEmployeeId("EMP004");
            emp4.setFirstName("Anna");
            emp4.setLastName("Reyes");
            emp4.setEmail("anna.reyes@company.com");
            emp4.setRole("DevOps Engineer");
            emp4.setStatus(EmployeeStatus.ACTIVE);
            emp4.setPrimarySkill("Docker");
            emp4.setSecondarySkill("Kubernetes");

            Employee emp5 = new Employee();
            emp5.setEmployeeId("EMP005");
            emp5.setFirstName("Jose");
            emp5.setLastName("Ramos");
            emp5.setEmail("jose.ramos@company.com");
            emp5.setRole("QA Engineer");
            emp5.setStatus(EmployeeStatus.RESIGNED);
            emp5.setPrimarySkill("Selenium");
            emp5.setSecondarySkill("TestNG");

            repository.save(emp1);
            repository.save(emp2);
            repository.save(emp3);
            repository.save(emp4);
            repository.save(emp5);

            log.info("Sample data loaded successfully! Total employees: {}", repository.count());
        };
    }
}
