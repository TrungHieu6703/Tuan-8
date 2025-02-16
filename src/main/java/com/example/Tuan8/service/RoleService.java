package com.example.Tuan8.service;

import com.example.Tuan8.dto.RoleDTO;
import com.example.Tuan8.model.Role;
import com.example.Tuan8.repository.RoleRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public List<RoleDTO>  findAll() {
        List<Role> roles = roleRepo.findAll();
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public Optional<RoleDTO> findById(int roleId) {
        return Optional.ofNullable(roleRepo.findById(roleId)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(() -> new EntityNotFoundException("role not found")));
    }

    public void deleteById(int roleId) {
        roleRepo.deleteById(roleId);
    }


    public RoleDTO create(RoleDTO roleDTO) {
        Role role = new Role();
        mapToEntity(roleDTO, role);
        roleRepo.save(role);
        return mapToDTO(role, new RoleDTO());
    }

    public void updateById(RoleDTO roleDTO) {
        Role role = roleRepo.findById(roleDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Role not found with id " + roleDTO.getId())
        );
        mapToEntity(roleDTO, role);
        roleRepo.save(role);
    }

    private RoleDTO mapToDTO(Role role, RoleDTO roleDTO){
        roleDTO.setId(role.getId());
        roleDTO.setRoleName(role.getRoleName());
        return roleDTO;
    }

    private Role mapToEntity(RoleDTO roleDTO, Role role){
        role.setId(roleDTO.getId());
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }
}
