package org.example.util;

import org.example.model.Employee;
import org.example.service.EmployeeService;

public class DataInitializer {

    public DataInitializer(EmployeeService employeeService) {
        employeeService.addEmployee(new Employee(1, "Ahmed", "IT", 10000));
        employeeService.addEmployee(new Employee(2, "Mohamed", "HR", 12000));
    }
}
