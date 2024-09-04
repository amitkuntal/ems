package com.employee.management.service;

import com.employee.management.dto.EmployeeDTO;
import com.employee.management.entity.Employee;
import com.employee.management.exception.DepartmentException;
import com.employee.management.exception.EmployeeException;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployee();

    void saveEmployee(Employee employee) throws EmployeeException;

    Employee getEmployeeById(Long id) throws EmployeeException;

    void deleteEmployee(Long id);

    Employee dtoToEmployeeConverter(EmployeeDTO employeeDTO);
    EmployeeDTO employeeToDtoConverter(Employee employee);
}
