package com.employee.management.service;


import com.employee.management.dto.DepartmentDTO;
import com.employee.management.entity.Department;
import com.employee.management.exception.DepartmentException;
import com.employee.management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public Department getDepartmentById(Long id) throws DepartmentException {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if(departmentOptional.isPresent()){
            return departmentOptional.get();
        }else{
            throw new DepartmentException("Department Not found " + id);
        }
    }

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartment() {
        return departmentRepository.findAll().stream().map(this::departmentToDTOConverter).toList();
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDTO departmentToDTOConverter(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        return departmentDTO;
    }

    @Override
    public Department dtoToDepartmentConverter(DepartmentDTO departmentDTO) {
        Department department = new Department();
        if(departmentDTO.getId()!=null){
            department.setId(departmentDTO.getId());
        }
        department.setName(departmentDTO.getName());
        return department;
    }
}
