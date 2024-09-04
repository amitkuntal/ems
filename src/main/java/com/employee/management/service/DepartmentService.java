package com.employee.management.service;

import com.employee.management.dto.DepartmentDTO;
import com.employee.management.entity.Department;
import com.employee.management.exception.DepartmentException;

import java.util.List;

public interface DepartmentService {
    Department getDepartmentById(Long id) throws DepartmentException;

    void saveDepartment(Department department);

    List<DepartmentDTO> getAllDepartment();

    void deleteDepartment(Long id);

    DepartmentDTO departmentToDTOConverter(Department department);

    Department dtoToDepartmentConverter(DepartmentDTO departmentDTO);


}
