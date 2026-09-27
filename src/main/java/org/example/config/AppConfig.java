package org.example.config;

import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.example.service.EmployeeServiceImpl;
import org.example.util.DataInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
public class AppConfig {
    @Bean
    public DataInitializer dataInitializer(EmployeeService employeeService) {
        return new DataInitializer(employeeService);
    }
}
