package com.example.Tuan8.dto;

import com.example.Tuan8.enums.PermissionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department_PermissionDTO {
    private int id;

    private int role_id;

    private PermissionEnum permission;
}
