package org.example.repository;

import org.example.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void save(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employee.getId()) {
                employees.set(i, employee);
                return;
            }
        }

        employees.add(employee);
    }

    @Override
    public Employee findById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return new Employee(
                        employee.getId(),
                        employee.getName(),
                        employee.getDepartment(),
                        employee.getSalary()
                );
            }
        }

        return null;
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }
}