package org.example.config;

import org.example.service.EmployeeService;
import org.example.util.DataInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.example")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public DataInitializer dataInitializer(EmployeeService employeeService) {
        return new DataInitializer(employeeService);
    }
}