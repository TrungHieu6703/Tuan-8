package com.example.Tuan8.service;

import com.example.Tuan8.dto.Department_PermissionDTO;
import com.example.Tuan8.model.Department_Permission;
import com.example.Tuan8.repository.DepartmentRepo;
import com.example.Tuan8.repository.Department_PermissionRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentPermisssionService {
    @Autowired
    private Department_PermissionRepo department_permissionRepo;
    private DepartmentRepo departmentRepo;

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
        department_permissionRepo.deleteById(id);
    }

    public Department_PermissionDTO create(Department_PermissionDTO Department_PermissionDTO) {
        Department_Permission Department_Permission = new Department_Permission();
        mapToEntity(Department_PermissionDTO, Department_Permission);
        department_permissionRepo.save(Department_Permission);
        return mapToDTO(Department_Permission, new Department_PermissionDTO());
    }

//    public void updateById(Role_PermissionDTO Role_PermissionDTO) {
//        Role_Permission Role_Permission = role_permissionRepo.findById(Role_PermissionDTO.getRole_id()).orElseThrow(
//                () -> new EntityNotFoundException("Role_Permission not found with id " + Role_PermissionDTO.getRole_id())
//        );
//        mapToEntity(Role_PermissionDTO, Role_Permission);
//        role_permissionRepo.save(Role_Permission);
//    }

    private Department_PermissionDTO mapToDTO(Department_Permission rolePermission, Department_PermissionDTO rolePermissionDTO){
        rolePermissionDTO.setRole_id(rolePermission.getId());
        rolePermissionDTO.setPermission(rolePermission.getPermission());
        return rolePermissionDTO;
    }

    private Department_Permission mapToEntity(Department_PermissionDTO rolePermissionDTO, Department_Permission rolePermission){
        rolePermission.setId(rolePermissionDTO.getId());
        rolePermission.setDepartment(departmentRepo.findById(rolePermissionDTO.getRole_id()).orElseThrow(
                ()-> new EntityNotFoundException("department not found")
        ));
        rolePermission.setPermission(rolePermissionDTO.getPermission());
        return rolePermission;
    }
    
}
