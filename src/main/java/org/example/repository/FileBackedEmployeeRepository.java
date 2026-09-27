package org.example.repository;

import org.example.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("prod")
public class FileBackedEmployeeRepository implements EmployeeRepository {

    private static final String FILE_NAME = "employees.csv";
    private final List<Employee> employees = new ArrayList<>();

    public FileBackedEmployeeRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String department = parts[2].trim();
                    double salary = Double.parseDouble(parts[3].trim());
                    employees.add(new Employee(id, name, department, salary));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load employees from file", e);
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," +
                        employee.getName() + "," +
                        employee.getDepartment() + "," +
                        employee.getSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save employees to file", e);
        }
    }

    @Override
    public void save(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employee.getId()) {
                employees.set(i, employee);
                saveToFile();
                return;
            }
        }
        employees.add(employee);
        saveToFile();
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