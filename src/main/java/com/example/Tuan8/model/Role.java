package com.example.Tuan8.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Role name cannot be blank")
    private String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;

    @OneToMany(mappedBy = "role")
    private Set<Role_Permission> role_permissions;


    public Role() {
    }

    public Role(String roleName, int id) {
        this.roleName = roleName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Role name cannot be blank") String getRoleName() {
        return roleName;
    }

    public void setRoleName(@NotBlank(message = "Role name cannot be blank") String roleName) {
        this.roleName = roleName;
    }
}
