package com.employee.management.service;

import com.employee.management.dto.DepartmentDTO;
import com.employee.management.entity.Department;
import com.employee.management.exception.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    Department getDepartmentById(Long id) throws DepartmentNotFoundException;

    void saveDepartment(Department department);

    List<DepartmentDTO> getAllDepartment();

    void deleteDepartment(Long id);

    DepartmentDTO departmentToDTOConverter(Department department);

    Department dtoToDepartmentConverter(DepartmentDTO departmentDTO);


}
