package com.example.Tuan8.service;

import com.example.Tuan8.dto.UserDTO;
import com.example.Tuan8.model.Department;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.DepartmentRepo;
import com.example.Tuan8.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${role_admin}")
    private String role_admin;

    public List<UserDTO> findAll() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(User -> mapToDTO(User, new UserDTO()))
                .toList();
    }

    public Optional<UserDTO> findById(int roleId) {
        return Optional.ofNullable(userRepo.findById(roleId)
                .map(User -> mapToDTO(User, new UserDTO()))
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    public void deleteById(int roleId) {
        userRepo.deleteById(roleId);
    }

    public UserDTO create(UserDTO UserDTO) {
        User User = new User();
        mapToEntity(UserDTO, User);
        userRepo.save(User);
        return mapToDTO(User, new UserDTO());
    }

    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(role_admin));
    }

    public UserDTO updateFieldById(int id, Map<String, Object> updates) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        User finalUser = user;
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findRequiredField(User.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, finalUser, value);
        });

        Department department = user.getDepartment();
        if (department == null) {
            throw new RuntimeException("User does not have a valid department");
        }

        String roleName = department.getRoleName();

        if (!isAdmin() && role_admin.equalsIgnoreCase(roleName)) {
            throw new RuntimeException("Can't update user with admin department");
        }

        user = userRepo.save(user);

        return mapToDTO(user, new UserDTO());
    }

    private UserDTO mapToDTO(User user, UserDTO userDTO){
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoleId(user.getDepartment().getId());
        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO, User user){
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setDepartment(departmentRepo.findById(userDTO.getRoleId()).orElseThrow(
                () -> new EntityNotFoundException("User not found with id " + userDTO.getRoleId())
        ));
        return user;
    }
}
