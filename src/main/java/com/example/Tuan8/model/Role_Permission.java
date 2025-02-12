package com.example.Tuan8.model;

import com.example.Tuan8.enums.PermissionEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "role_permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_id", "permission"})
})
public class Role_Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;


    public Role_Permission() {
    }

    public Role_Permission(int id, Role role, PermissionEnum permission) {
        this.id = id;
        this.role = role;
        this.permission = permission;
    }

    public PermissionEnum getPermission() {
        return permission;
    }

    public void setPermission(PermissionEnum permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Role_Permission{" +
                "id=" + id +
                ", role=" + role +
                ", permission=" + permission +
                '}';
    }
}
