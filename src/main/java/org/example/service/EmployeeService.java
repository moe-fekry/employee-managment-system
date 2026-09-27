package org.example.service;

import org.example.model.Employee;

import java.util.List;

public interface EmployeeService {
    public void addEmployee(Employee employee);
    public Employee getEmployeeById(int employeeId);
    public List<Employee> getAllEmployees();
    void  giveRaise(int id, double percentage);

}
