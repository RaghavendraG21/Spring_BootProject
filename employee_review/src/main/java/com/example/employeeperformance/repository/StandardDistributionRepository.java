package com.example.employeeperformance.repository;

import com.example.employeeperformance.model.StandardDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardDistributionRepository extends JpaRepository<StandardDistribution, Long> {
}