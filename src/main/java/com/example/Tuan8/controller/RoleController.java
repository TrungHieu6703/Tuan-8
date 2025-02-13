package com.example.Tuan8.controller;

import com.example.Tuan8.dto.RoleDTO;
import com.example.Tuan8.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleDTO>> getRoles(){
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Optional<RoleDTO>> getRole(@PathVariable int roleId) {
        return ResponseEntity.ok(roleService.findById(roleId));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable int roleId) {
        roleService.deleteById(roleId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.create(roleDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateRole(@Valid @RequestBody RoleDTO roleDTO) {
        roleService.updateById(roleDTO);
        return ResponseEntity.noContent().build();
    }

}
