package com.example.Tuan8.service;

import com.example.Tuan8.model.Department;
import com.example.Tuan8.model.Department_Permission;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.Department_PermissionRepo;
import com.example.Tuan8.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Department_PermissionRepo rolePermissionRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        Department department = user.getDepartment();

        List<Department_Permission> rolePermissions = rolePermissionRepo.findByDepartmentId(department.getId());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + department.getRoleName()));
        System.out.println(department.getRoleName());

        rolePermissions.forEach(item ->
                grantedAuthorities.add(new SimpleGrantedAuthority(item.getPermission().toString()))
        );

        grantedAuthorities.forEach(grantedAuthority ->
                        System.out.println(grantedAuthority.getAuthority())
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
