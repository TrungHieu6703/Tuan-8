package com.example.Tuan8.dto;

import com.example.Tuan8.enums.PermissionEnum;

public class Role_PermissionDTO {
    private int role_id;

    private PermissionEnum permission;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public PermissionEnum getPermission() {
        return permission;
    }

    public void setPermission(PermissionEnum permission) {
        this.permission = permission;
    }
}
