package com.example.Tuan8.controller;

import com.example.Tuan8.dto.Role_PermissionDTO;
import com.example.Tuan8.repository.RoleRepo;
import com.example.Tuan8.service.RolePermisssionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/role_permissions")
public class Role_PermissionController {
    @Autowired
    private RolePermisssionService rolePermisssionService;

    @Autowired
    private RoleRepo roleRepo;

    @PostMapping("/")
    public ResponseEntity<Role_PermissionDTO> createRole_Permission(@RequestBody Role_PermissionDTO rolePermissionDTO) {
        return ResponseEntity.ok(rolePermisssionService.create(rolePermissionDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRole_Permission(@PathVariable int id){
        rolePermisssionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
