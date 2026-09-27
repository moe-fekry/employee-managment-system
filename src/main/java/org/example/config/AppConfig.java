package org.example.config;

import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.example.service.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    EmployeeService employeeService(EmployeeRepository employeeRepository){
        return new EmployeeServiceImpl(employeeRepository);
    }
}
