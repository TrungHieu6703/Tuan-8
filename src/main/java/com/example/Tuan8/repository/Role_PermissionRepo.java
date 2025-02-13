package com.example.Tuan8.repository;

import com.example.Tuan8.model.Role_Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permission;
import java.util.List;


public interface Role_PermissionRepo extends JpaRepository<Role_Permission, Integer>{

    List<Role_Permission> findByRoleId(int roleId);

    boolean existsByRoleAndPermission(Role_Permission role, Permission permission);
}
