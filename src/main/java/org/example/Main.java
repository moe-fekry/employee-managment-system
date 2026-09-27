package org.example;

import org.example.audit.AuditLogger;
import org.example.config.AppConfig;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppConfig.class);
        context.refresh();

        System.out.println("\n=================================================");
        System.out.println(" EMPLOYEE MANAGEMENT SYSTEM - FULL FEATURE DEMO");
        System.out.println("=================================================");

        EmployeeService employeeService = context.getBean(EmployeeService.class);

        System.out.println("\n--- [1] Adding a VALID employee ---");
        Employee valid = new Employee(3, "Sara", "Finance", 9000);
        employeeService.addEmployee(valid);

        System.out.println("\n--- [2] Adding an INVALID employee (blank name) ---");
        try {
            Employee invalid = new Employee(4, "   ", "Marketing", 8000);
            employeeService.addEmployee(invalid);
        } catch (RuntimeException ex) {
            System.out.println("Caught expected validation error: " + ex.getMessage());
        }

        System.out.println("\n--- [3] Adding an INVALID employee (negative salary) ---");
        try {
            Employee invalid = new Employee(5, "Omar", "Sales", -100);
            employeeService.addEmployee(invalid);
        } catch (RuntimeException ex) {
            System.out.println("Caught expected validation error: " + ex.getMessage());
        }

        System.out.println("\n--- [4] Giving a raise WITHIN the limit (10%) ---");
        employeeService.giveRaise(3, 10);

        System.out.println("\n--- [5] Giving a raise EXCEEDING the limit (50%) ---");
        try {
            employeeService.giveRaise(3, 50);
        } catch (RuntimeException ex) {
            System.out.println("Caught expected raise error: " + ex.getMessage());
        }

        System.out.println("\n--- [6] Requesting prototype AuditLogger multiple times ---");
        AuditLogger l1 = context.getBean(AuditLogger.class);
        AuditLogger l2 = context.getBean(AuditLogger.class);
        AuditLogger l3 = context.getBean(AuditLogger.class);
        System.out.println("Instance 1 id: " + l1.getInstanceId());
        System.out.println("Instance 2 id: " + l2.getInstanceId());
        System.out.println("Instance 3 id: " + l3.getInstanceId());
        System.out.println("All different? "
                + (l1 != l2 && l2 != l3 && l1 != l3));

        EmployeeService s1 = context.getBean(EmployeeService.class);
        EmployeeService s2 = context.getBean(EmployeeService.class);
        System.out.println("EmployeeService (singleton) same instance? " + (s1 == s2));

        System.out.println("\n--- [7] Listing all employees ---");
        employeeService.getAllEmployees().forEach(System.out::println);

        System.out.println("\n--- [8] Injected @Value properties (from application.properties) ---");
        Environment env = context.getEnvironment();
        System.out.println("company.name             = " + env.getProperty("company.name"));
        System.out.println("company.currency         = " + env.getProperty("company.currency"));
        System.out.println("notification.retry-count = " + env.getProperty("notification.retry-count"));
        System.out.println("raise.max-percentage     = " + env.getProperty("raise.max-percentage"));

        System.out.println("\n--- [9] Closing context (triggers @PreDestroy) ---");
        context.close();
        System.out.println("Context closed. Demo finished.");
    }
}