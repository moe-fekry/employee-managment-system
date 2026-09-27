package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;
import org.example.notify.NotificationManager;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final NotificationManager notificationManager;

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
        notificationManager.notifyAll("New employee added: " + employee.getName());
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void giveRaise(int id, double percentage) {
        Employee employee = employeeRepository.findById(id);

        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }

        double currentSalary = employee.getSalary();
        double raise = currentSalary * (percentage / 100);
        double newSalary = currentSalary + raise;

        employee.setSalary(newSalary);

        employeeRepository.save(employee);

        notificationManager.notifyAll(
                "Employee " + employee.getName() + " received a " + percentage +
                        "% raise. New salary: " + newSalary
        );
    }
}