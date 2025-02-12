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

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    @OneToMany(mappedBy = "role")
    private Set<Role_Permission> role_permissions;

    public Role() {
    }

    public Role(int id, String role_name) {
        this.id = id;
        this.roleName = role_name;
    }

    public Role(Object o, String roleAdmin, Object o1) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_name() {
        return roleName;
    }

    public void setRole_name(String role_name) {
        this.roleName = role_name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role_name='" + roleName + '\'' +
                ", users=" + users +
                ", role_permissions=" + role_permissions +
                '}';
    }
}
