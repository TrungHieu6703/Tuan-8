package com.example.Tuan8.controller;

import com.example.Tuan8.dto.Role_PermissionDTO;
import com.example.Tuan8.model.Role;
import com.example.Tuan8.model.Role_Permission;
import com.example.Tuan8.repository.RoleRepo;
import com.example.Tuan8.repository.Role_PermissionRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role_permissions")
public class Role_PermissionController {
    @Autowired
    private Role_PermissionRepo role_permissionRepo;

    @Autowired
    private RoleRepo roleRepo;

    @PostMapping("/")
    public ResponseEntity<?> createRole_Permission(@RequestBody Role_PermissionDTO rolePermissionDTO) {
        Role_Permission rolePermission = new Role_Permission();
        Role role = roleRepo.findById(rolePermissionDTO.getRole_id()).orElseThrow(
                ()-> new EntityNotFoundException("role not found")
        );
        rolePermission.setRole(role);
        rolePermission.setPermission(rolePermissionDTO.getPermission());
        return  ResponseEntity.ok(role_permissionRepo.save(rolePermission));
    }

}
