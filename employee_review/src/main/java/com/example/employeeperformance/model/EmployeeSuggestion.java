package com.example.employeeperformance.model;
public class EmployeeSuggestion {
    private String employeeName;
    private double performancePercentage;
    private String suggestion;
    private String improvementArea;
    private double deviation;

    // Constructor
    public EmployeeSuggestion(String employeeName, double performancePercentage, String suggestion, String improvementArea, double deviation) {
        this.employeeName = employeeName;
        this.performancePercentage = performancePercentage;
        this.suggestion = suggestion;
        this.improvementArea = improvementArea;
        this.deviation = deviation;
    }

    // Getters and Setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getPerformancePercentage() {
        return performancePercentage;
    }

    public void setPerformancePercentage(double performancePercentage) {
        this.performancePercentage = performancePercentage;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getImprovementArea() {
        return improvementArea;
    }

    public void setImprovementArea(String improvementArea) {
        this.improvementArea = improvementArea;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }
}
