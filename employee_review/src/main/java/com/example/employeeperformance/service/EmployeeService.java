
package com.example.employeeperformance.service;

import com.example.employeeperformance.model.Employee;
import com.example.employeeperformance.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    // Define the STANDARD_DISTRIBUTION map
    private static final Map<String, Double> STANDARD_DISTRIBUTION = new HashMap<>();
    static {
        STANDARD_DISTRIBUTION.put("A", 10.0);
        STANDARD_DISTRIBUTION.put("B", 20.0);
        STANDARD_DISTRIBUTION.put("C", 40.0);
        STANDARD_DISTRIBUTION.put("D", 20.0);
        STANDARD_DISTRIBUTION.put("E", 10.0);
    }

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Fetch detailed employee data including rating, standard percentage, actual percentage, and deviation
    public List<Map<String, Object>> getDetailedEmployeeData() {
        List<Employee> employees = repository.findAll();
        int totalEmployees = employees.size();

        if (totalEmployees == 0) {
            return Collections.emptyList();
        }

        // Calculate actual percentages based on rating counts
        Map<String, Long> ratingCounts = employees.stream()
                .filter(emp -> emp.getRating() != null)
                .collect(Collectors.groupingBy(Employee::getRating, Collectors.counting()));

        Map<String, Double> actualPercentage = new HashMap<>();
        for (Map.Entry<String, Long> entry : ratingCounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalEmployees;
            actualPercentage.put(entry.getKey(), percentage);
        }

        // Prepare the detailed employee data with employee ID, name, rating, standard %, actual %, and deviation
        List<Map<String, Object>> detailedData = new ArrayList<>();
        for (Employee employee : employees) {
            String rating = employee.getRating();
            if (rating != null && STANDARD_DISTRIBUTION.containsKey(rating)) {
                Map<String, Object> employeeData = new HashMap<>();
                employeeData.put("id", employee.getId()); // Add the employee ID
                employeeData.put("name", employee.getName());
                employeeData.put("rating", rating);
                employeeData.put("standardPercentage", STANDARD_DISTRIBUTION.get(rating)); 
                employeeData.put("actualPercentage", actualPercentage.getOrDefault(rating, 0.0)); 
                employeeData.put("deviation", actualPercentage.getOrDefault(rating, 0.0) - STANDARD_DISTRIBUTION.get(rating)); 
                detailedData.add(employeeData);
            }
        }

        return detailedData;
    }

    // Additional CRUD operations (Optional for your use case)

    // Add an employee
    public Employee addEmployee(Employee employee) {
        // Save the employee first
        Employee savedEmployee = repository.save(employee);

        // After saving the employee, calculate and set the automatic fields

        String rating = savedEmployee.getRating();

        // Ensure that the rating exists in the standard distribution
        if (rating != null && STANDARD_DISTRIBUTION.containsKey(rating)) {
            // Set the standard percentage based on the rating
            savedEmployee.setStandardPercentage(STANDARD_DISTRIBUTION.get(rating));

            // Calculate the actual percentage for this rating from all employees
            List<Employee> employees = repository.findAll();
            int totalEmployees = employees.size();

            // Avoid division by zero
            if (totalEmployees > 0) {
                Map<String, Long> ratingCounts = employees.stream()
                        .filter(emp -> emp.getRating() != null)
                        .collect(Collectors.groupingBy(Employee::getRating, Collectors.counting()));

                double actualPercentage = (ratingCounts.getOrDefault(rating, 0L) * 100.0) / totalEmployees;
                savedEmployee.setActualPercentage(actualPercentage);

                // Calculate deviation (difference between actual and standard percentage)
                double deviation = actualPercentage - savedEmployee.getStandardPercentage();
                savedEmployee.setDeviation(deviation);
            }
        }

        // Save the updated employee with calculated values
        return repository.save(savedEmployee);
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    // Update an employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (repository.existsById(id)) {
            updatedEmployee.setId(id);
            return repository.save(updatedEmployee);
        }
        return null;
    }

    // Delete an employee
    public String deleteEmployee(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Employee with id " + id + " deleted successfully.";
        }
        return "Employee not found.";
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Additional helper methods (Optional)

    // Get actual percentage for each rating
    public Map<String, Double> calculateActualPercentage() {
        List<Employee> employees = repository.findAll();
        int totalEmployees = employees.size();

        if (totalEmployees == 0) {
            return Collections.emptyMap();
        }

        Map<String, Long> ratingCounts = employees.stream()
                .filter(emp -> emp.getRating() != null)
                .collect(Collectors.groupingBy(Employee::getRating, Collectors.counting()));

        Map<String, Double> actualPercentage = new HashMap<>();
        for (Map.Entry<String, Long> entry : ratingCounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalEmployees;
            actualPercentage.put(entry.getKey(), percentage);
        }

        return actualPercentage;
    }

    // Get deviation for each rating
    public Map<String, Double> calculateDeviation() {
        Map<String, Double> actualPercentage = calculateActualPercentage();
        Map<String, Double> deviation = new HashMap<>();

        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            double standardPercentage = STANDARD_DISTRIBUTION.get(rating);
            double actualPercentageValue = actualPercentage.getOrDefault(rating, 0.0);
            deviation.put(rating, actualPercentageValue - standardPercentage);
        }

        return deviation;
    }

    // Get suggestions for revision based on deviation (example suggestion method)
    public List<String> suggestEmployeesForRevision() {
        Map<String, Double> deviation = calculateDeviation();
        List<Employee> employees = repository.findAll();
        List<String> suggestions = new ArrayList<>();

        for (Employee employee : employees) {
            String rating = employee.getRating();
            if (deviation.containsKey(rating) && deviation.get(rating) > 5) {
                String suggestion = "Employee: " + employee.getName() + ", Performance Percentage: "
                        + calculateActualPercentage().getOrDefault(rating, 0.0) + ", Suggestion: Needs revision due to high deviation.";

                // Add personalized improvement area
                if (rating.equals("A")) {
                    suggestion += " Improvement Area: Leadership development.";
                } else if (rating.equals("B")) {
                    suggestion += " Improvement Area: Technical skill enhancement.";
                } else if (rating.equals("C")) {
                    suggestion += " Improvement Area: Time management.";
                } else if (rating.equals("D")) {
                    suggestion += " Improvement Area: Team collaboration.";
                } else {
                    suggestion += " Improvement Area: Overall performance review.";
                }

                // Include deviation value
                suggestion += " Deviation: " + deviation.get(rating);

                suggestions.add(suggestion);
            }
        }

        return suggestions;
    }

}
