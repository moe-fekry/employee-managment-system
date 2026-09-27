package org.example.validation;

import org.example.exception.InvalidEmployeeException;
import org.example.exception.InvalidRaisePersentageException;
import org.example.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator {

    public void validate(Employee employee) {
        if (employee.getName() == null || employee.getName().isBlank()) {
            throw new InvalidEmployeeException("Employee name cannot be blank");
        }
        if (employee.getSalary() < 0) {
            throw new InvalidEmployeeException("Employee salary cannot be negative");
        }
    }
    public void validate(Employee employee, double raisePersentage) {
        validate(employee);
        if (raisePersentage<0){
            throw new InvalidRaisePersentageException("the raise persentage should be positive otherwise it will not be raise .");
        }
    }
}