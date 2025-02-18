package com.example.Tuan8.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Department name cannot be blank")
    private String roleName;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<User> users;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Department_Permission> department_permissions;

    public @NotBlank(message = "Department name cannot be blank") String getRoleName() {
        return roleName;
    }

}
