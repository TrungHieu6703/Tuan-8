package com.example.Tuan8.controller;

import com.example.Tuan8.dto.Department_PermissionDTO;
import com.example.Tuan8.repository.DepartmentRepo;
import com.example.Tuan8.service.DepartmentPermisssionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/department_permissions")
public class Department_PermissionController {
    @Autowired
    private DepartmentPermisssionService departmentPermisssionService;

    @Autowired
    private DepartmentRepo departmentRepo;

    @PostMapping("/")
    public ResponseEntity<Department_PermissionDTO> createRole_Permission(@RequestBody Department_PermissionDTO rolePermissionDTO) {
        return ResponseEntity.ok(departmentPermisssionService.create(rolePermissionDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRole_Permission(@PathVariable int id){
        departmentPermisssionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
