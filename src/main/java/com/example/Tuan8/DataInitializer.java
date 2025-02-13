package com.example.Tuan8;

import com.example.Tuan8.enums.PermissionEnum;
import com.example.Tuan8.model.Role;
import com.example.Tuan8.model.Role_Permission;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.RoleRepo;
import com.example.Tuan8.repository.Role_PermissionRepo;
import com.example.Tuan8.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    @Autowired
    private  RoleRepo roleRepository;
    @Autowired
    private  UserRepo userRepository;
    @Autowired
    private Role_PermissionRepo rolePermissionRepo;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (roleRepository.findByRoleName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            Set<PermissionEnum> permissions = Set.of(
                    PermissionEnum.CREATE,
                    PermissionEnum.READ,
                    PermissionEnum.UPDATE,
                    PermissionEnum.DELETE
            );
            for (PermissionEnum permission : permissions) {
                Role_Permission rolePermission = new Role_Permission();
                rolePermission.setRole(adminRole);
                rolePermission.setPermission(permission);
                rolePermissionRepo.save(rolePermission);
            }
            // Tạo user admin
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setFullName("Administrator");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(adminRole);
            userRepository.save(adminUser);

            System.out.println("✅ username: admin && password: admin123");
        }
    }
}
