package com.example.Tuan8.controller;

import com.example.Tuan8.model.Role;
import com.example.Tuan8.repository.RoleRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleRepo roleRepo;

    @GetMapping("/")
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @GetMapping("/{roleId}")
    public Role getRole(@PathVariable int roleId) {
        return roleRepo.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found with id " + roleId ));
    }

    @DeleteMapping("/{roleId}")
    public String deleteRole(@PathVariable int roleId) {
        roleRepo.deleteById(roleId);
        return "Delete Role " + roleId;
    }

    @PostMapping("/")
    public Role addRole(@RequestBody Role role) {
        return roleRepo.save(role);
    }


}
