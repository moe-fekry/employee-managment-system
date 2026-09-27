package org.example;

import org.example.audit.AuditLogger;
import org.example.config.AppConfig;
import org.example.service.EmployeeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppConfig.class);
        context.refresh();

        System.out.println("\n=== Prototype scope demo ===");
        AuditLogger logger1 = context.getBean(AuditLogger.class);
        AuditLogger logger2 = context.getBean(AuditLogger.class);
        System.out.println("AuditLogger is prototype? " + (logger1 != logger2));

        EmployeeService service1 = context.getBean(EmployeeService.class);
        EmployeeService service2 = context.getBean(EmployeeService.class);
        System.out.println("EmployeeService is singleton? " + (service1 == service2));

        System.out.println("\n=== Business operations ===");
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        System.out.println("\nAll employees:");
        System.out.println(employeeService.getAllEmployees());

        // valid raise (<= 20%)
        employeeService.giveRaise(1, 10);

        System.out.println("\nAfter 10% raise:");
        System.out.println(employeeService.getEmployeeById(1));

        // invalid raise (> 20%) -> should throw
        System.out.println("\nTrying a 50% raise (max is 20%)...");
        try {
            employeeService.giveRaise(1, 50);
        } catch (RuntimeException ex) {
            System.out.println("Rejected as expected: " + ex.getMessage());
        }

        System.out.println("\n=== Closing context ===");
        context.close();
    }
}