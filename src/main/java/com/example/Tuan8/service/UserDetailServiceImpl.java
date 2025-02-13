package com.example.Tuan8.service;

import com.example.Tuan8.model.Role;
import com.example.Tuan8.model.Role_Permission;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.Role_PermissionRepo;
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
    private Role_PermissionRepo rolePermissionRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        Role role = user.getRole();

        List<Role_Permission> rolePermissions = rolePermissionRepo.findByRoleId(role.getId());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        rolePermissions.forEach(item ->
                grantedAuthorities.add(new SimpleGrantedAuthority(item.getPermission().toString()))
        );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
