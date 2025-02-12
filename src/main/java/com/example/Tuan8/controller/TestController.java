package com.example.Tuan8.controller;

import com.example.Tuan8.model.Role_Permission;
import com.example.Tuan8.repository.Role_PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private Role_PermissionRepo role_permissionRepo;

    @GetMapping("/")
    public List<Role_Permission> test() {

        int role_id = 2;
        List<Role_Permission> rolePermissions = role_permissionRepo.findByRoleId(role_id);

        return rolePermissions;
    }
}
