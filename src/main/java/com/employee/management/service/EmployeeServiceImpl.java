package com.employee.management.service;

import com.employee.management.dto.EmployeeDTO;
import com.employee.management.entity.Employee;
import com.employee.management.exception.EmployeeNotFoundException;
import com.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAll().stream().map(this::employeeToDtoConverter).toList();
    }

    @Override
    public void saveEmployee(Employee employee) throws EmployeeNotFoundException {
        Employee dbEmployee = employeeRepository.findByEmail(employee.getEmail());
        if( dbEmployee !=null && dbEmployee.getId() != employee.getId()){
            throw new EmployeeNotFoundException("Employee already exist with email "+ employee.getEmail());
        }
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }else{
            throw new EmployeeNotFoundException("Employee Not found for id  "+ id);
        }
    }

    @Override
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee dtoToEmployeeConverter(EmployeeDTO employeeDTO) {
        Employee employee =  new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        try{
            employee.setDepartment(departmentService.getDepartmentById(employeeDTO.getDepartmentId()));
        }catch (Exception ex){

        }
        return employee;
    }

    @Override
    public EmployeeDTO employeeToDtoConverter(Employee employee){
        EmployeeDTO employeeDTO =  new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDepartmentId(employee.getDepartment().getId());
        employeeDTO.setDepartment(departmentService.departmentToDTOConverter(employee.getDepartment()));
        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }
}
