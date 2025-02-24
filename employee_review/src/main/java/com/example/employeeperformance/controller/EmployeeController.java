
package com.example.employeeperformance.controller;

import com.example.employeeperformance.model.Employee;
import com.example.employeeperformance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    // Add a new employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    // Update an existing employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    // Get the detailed employee performance data (name, rating, standard %, actual %, deviation)
    @GetMapping("/detailed-performance")
    public List<Map<String, Object>> getDetailedEmployeeData() {
        return employeeService.getDetailedEmployeeData();
    }

    // Get actual percentages for each rating
    @GetMapping("/actual-percentage")
    public Map<String, Double> getActualPercentage() {
        return employeeService.calculateActualPercentage();
    }

    // Get deviation details for each rating
    @GetMapping("/deviation")
    public Map<String, Double> getDeviation() {
        return employeeService.calculateDeviation();
    }

    // Get suggestions for revision based on deviation
    @GetMapping("/suggestions")
    public List<String> getSuggestionsForRevision() {
        return employeeService.suggestEmployeesForRevision();
    }
}
