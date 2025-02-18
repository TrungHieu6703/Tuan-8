package com.example.Tuan8.model;

import com.example.Tuan8.enums.PermissionEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "department_permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"department_id", "permission"})
})
public class Department_Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;
}
