package com.example.Tuan8.controller;

import com.example.Tuan8.dto.UserDTO;
import com.example.Tuan8.model.Role;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.RoleRepo;
import com.example.Tuan8.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @PostMapping("/")
    public void addUser(@RequestBody UserDTO userDTO) {
        User newUser = new User();
        newUser.setFullName(userDTO.getFullName());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        Role role = roleRepo.findById(userDTO.getRoleId()).orElse(null);
        newUser.setRole(role);
        userRepo.save(newUser);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userRepo.deleteById(userId);
        return "Delete User " + userId;
    }

    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateAFields(@PathVariable int userId, @RequestBody Map<String, Object> updates) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findRequiredField(User.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
        });

        int role_id = user.getRole().getId();

        Role role = roleRepo.findById(role_id).orElse(null);

        assert role != null;

        String role_name = role.getRole_name();

        if(isAdmin()) {
            return ResponseEntity.ok(userRepo.save(user));
        } else {
            if ("ROLE_ADMIN".equals(role_name)) {
                return new ResponseEntity<>("can't update user with your role", HttpStatus.NOT_ACCEPTABLE);
            }else {
                return ResponseEntity.ok(userRepo.save(user));
            }
        }
    }
}
