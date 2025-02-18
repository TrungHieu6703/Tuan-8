package com.example.Tuan8;

import com.example.Tuan8.enums.PermissionEnum;
import com.example.Tuan8.model.Department;
import com.example.Tuan8.model.Department_Permission;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.DepartmentRepo;
import com.example.Tuan8.repository.Department_PermissionRepo;
import com.example.Tuan8.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    @Autowired
    private DepartmentRepo departmentRepository;
    @Autowired
    private  UserRepo userRepository;
    @Autowired
    private Department_PermissionRepo rolePermissionRepo;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Value("${role_admin}")
    private String role_admin;

    @PostConstruct
    public void init() {
        if (departmentRepository.findByRoleName(role_admin).isEmpty()) {
            Department adminDepartment = new Department();
            adminDepartment.setRoleName(role_admin);
            departmentRepository.save(adminDepartment);
            Set<PermissionEnum> permissions = Set.of(
                    PermissionEnum.CREATE,
                    PermissionEnum.READ,
                    PermissionEnum.UPDATE,
                    PermissionEnum.DELETE
            );
            for (PermissionEnum permission : permissions) {
                Department_Permission rolePermission = new Department_Permission();
                rolePermission.setDepartment(adminDepartment);
                rolePermission.setPermission(permission);
                rolePermissionRepo.save(rolePermission);
            }
            // Tạo user admin
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setFullName("Administrator");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setDepartment(adminDepartment);
            userRepository.save(adminUser);

            System.out.println("✅ username: admin && password: admin123. Please login and change your information");
        }
    }
}
