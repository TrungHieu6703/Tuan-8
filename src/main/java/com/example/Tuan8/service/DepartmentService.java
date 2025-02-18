package com.example.Tuan8.service;

import com.example.Tuan8.dto.DepartmentDTO;
import com.example.Tuan8.model.Department;
import com.example.Tuan8.repository.DepartmentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;


    public List<DepartmentDTO>  findAll() {
        List<Department> departments = departmentRepo.findAll();
        return departments.stream()
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .toList();
    }


    public Optional<DepartmentDTO> findById(int departmentId) {
        return Optional.ofNullable(departmentRepo.findById(departmentId)
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .orElseThrow(() -> new EntityNotFoundException("department not found")));
    }

    public void deleteById(int departmentId) {
        if (!departmentRepo.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id " + departmentId);
        }
        departmentRepo.deleteById(departmentId);
    }

    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department department = new Department();
        mapToEntity(departmentDTO, department);
        departmentRepo.save(department);
        return mapToDTO(department, new DepartmentDTO());
    }

    public void updateById(DepartmentDTO departmentDTO) {
        Department department = departmentRepo.findById(departmentDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("Department not found with id " + departmentDTO.getId())
        );

        department.setRoleName(departmentDTO.getRoleName());

        departmentRepo.save(department);
    }


    private DepartmentDTO mapToDTO(Department department, DepartmentDTO departmentDTO){
        departmentDTO.setId(department.getId());
        departmentDTO.setRoleName(department.getRoleName());
        return departmentDTO;
    }

    private Department mapToEntity(DepartmentDTO departmentDTO, Department department){
        department.setId(departmentDTO.getId());
        department.setRoleName(departmentDTO.getRoleName());
        return department;
    }
}
