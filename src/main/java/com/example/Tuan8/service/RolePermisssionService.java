package com.example.Tuan8.service;

import com.example.Tuan8.dto.Role_PermissionDTO;
import com.example.Tuan8.model.Role_Permission;
import com.example.Tuan8.repository.RoleRepo;
import com.example.Tuan8.repository.Role_PermissionRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermisssionService {
    @Autowired
    private Role_PermissionRepo role_permissionRepo;
    private RoleRepo roleRepo;

//
//    public List<Role_PermissionDTO> findAll() {
//        List<Role_Permission> role_permissions = role_permissionRepo.findAll();
//        return role_permissions.stream()
//                .map(Role_Permission -> mapToDTO(Role_Permission, new Role_PermissionDTO()))
//                .toList();
//    }
//
//    public Optional<Role_PermissionDTO> findById(int roleId) {
//        return Optional.ofNullable(role_permissionRepo.findById(roleId)
//                .map(Role_Permission -> mapToDTO(Role_Permission, new Role_PermissionDTO()))
//                .orElseThrow(() -> new EntityNotFoundException("Role_Permission not found")));
//    }

    public void deleteById(int id) {
        role_permissionRepo.deleteById(id);
    }

    public Role_PermissionDTO create(Role_PermissionDTO Role_PermissionDTO) {
        Role_Permission Role_Permission = new Role_Permission();
        mapToEntity(Role_PermissionDTO, Role_Permission);
        role_permissionRepo.save(Role_Permission);
        return mapToDTO(Role_Permission, new Role_PermissionDTO());
    }

//    public void updateById(Role_PermissionDTO Role_PermissionDTO) {
//        Role_Permission Role_Permission = role_permissionRepo.findById(Role_PermissionDTO.getRole_id()).orElseThrow(
//                () -> new EntityNotFoundException("Role_Permission not found with id " + Role_PermissionDTO.getRole_id())
//        );
//        mapToEntity(Role_PermissionDTO, Role_Permission);
//        role_permissionRepo.save(Role_Permission);
//    }

    private Role_PermissionDTO mapToDTO(Role_Permission rolePermission, Role_PermissionDTO rolePermissionDTO){
        rolePermissionDTO.setRole_id(rolePermission.getId());
        rolePermissionDTO.setPermission(rolePermission.getPermission());
        return rolePermissionDTO;
    }

    private Role_Permission mapToEntity(Role_PermissionDTO rolePermissionDTO, Role_Permission rolePermission){
        rolePermission.setId(rolePermissionDTO.getId());
        rolePermission.setRole(roleRepo.findById(rolePermissionDTO.getRole_id()).orElseThrow(
                ()-> new EntityNotFoundException("role not found")
        ));
        rolePermission.setPermission(rolePermissionDTO.getPermission());
        return rolePermission;
    }
    
}
