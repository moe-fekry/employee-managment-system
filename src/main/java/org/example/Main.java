package org.example;

import org.example.config.AppConfig;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        EmployeeService employeeService =
                context.getBean(EmployeeService.class);

        Employee employee1 =
                new Employee(1, "Ahmed", "IT", 10000);

        Employee employee2 =
                new Employee(2, "Mohamed", "HR", 12000);

        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);

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