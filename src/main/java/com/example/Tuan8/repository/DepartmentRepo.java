package com.example.Tuan8.repository;

import com.example.Tuan8.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    Optional<Department> findByRoleName(String roleName);
}
