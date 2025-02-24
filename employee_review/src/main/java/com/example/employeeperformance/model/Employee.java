
package com.example.employeeperformance.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String rating;

    private Double standardPercentage;
    private Double actualPercentage;
    private Double deviation;

    // Default constructor
    public Employee() {}

    // Constructor with fields
    public Employee(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getStandardPercentage() {
        return standardPercentage;
    }

    public void setStandardPercentage(Double standardPercentage) {
        this.standardPercentage = standardPercentage;
    }

    public Double getActualPercentage() {
        return actualPercentage;
    }

    public void setActualPercentage(Double actualPercentage) {
        this.actualPercentage = actualPercentage;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }
}
