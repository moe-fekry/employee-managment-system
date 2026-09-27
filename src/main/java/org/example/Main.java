package org.example;

import org.example.config.AppConfig;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.getEnvironment().setActiveProfiles("prod");

        context.register(AppConfig.class);
        context.refresh();

        EmployeeService employeeService =
                context.getBean(EmployeeService.class);

        System.out.println("All employees:");
        System.out.println(employeeService.getAllEmployees());

        System.out.println("\nEmployee with ID 1:");
        System.out.println(employeeService.getEmployeeById(1));

        employeeService.giveRaise(1, 10);

        System.out.println("\nAfter 10% raise:");
        System.out.println(employeeService.getEmployeeById(1));

        context.close();
    }
}