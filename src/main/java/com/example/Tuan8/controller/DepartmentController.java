package com.example.Tuan8.controller;

import com.example.Tuan8.dto.DepartmentDTO;
import com.example.Tuan8.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public ResponseEntity<List<DepartmentDTO>> getRoles(){
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Optional<DepartmentDTO>> getRole(@PathVariable int departmentId) {
        return ResponseEntity.ok(departmentService.findById(departmentId));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteRole(@PathVariable int departmentId) {
        departmentService.deleteById(departmentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDTO> addRole(@RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.create(departmentDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateRole(@Valid @RequestBody DepartmentDTO departmentDTO) {
        departmentService.updateById(departmentDTO);
        return ResponseEntity.noContent().build();
    }

}
