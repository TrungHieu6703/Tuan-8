package com.example.Tuan8.repository;

import com.example.Tuan8.model.Department_Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permission;
import java.util.List;


public interface Department_PermissionRepo extends JpaRepository<Department_Permission, Integer>{

    List<Department_Permission> findByDepartmentId(int DepartmentId);

    boolean existsByDepartmentAndPermission(Department_Permission department, Permission permission);
}
