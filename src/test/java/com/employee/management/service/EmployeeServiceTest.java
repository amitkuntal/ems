package com.employee.management.service;

import com.employee.management.dto.EmployeeDTO;
import com.employee.management.entity.Department;
import com.employee.management.entity.Employee;
import com.employee.management.exception.DepartmentException;
import com.employee.management.exception.EmployeeException;
import com.employee.management.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Amit Kuntal");
        employee.setEmail("amit.kuntal@gmail.com");
        employee.setDepartment(new Department(1l, "H")); // Mock department or set a dummy value if needed

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("Amit Kuntal");
        employeeDTO.setEmail("amit.kuntal@gmail.com");
        employeeDTO.setDepartmentId(null); // Mock departmentId or set a dummy value if needed

        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        assertEquals(Collections.singletonList(employeeDTO).size(), employeeService.getAllEmployee().size());
    }

    @Test
    public void testSaveEmployee() throws EmployeeException, DepartmentException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("Amit Kuntal");
        employeeDTO.setEmail("amit.kuntal@gmail.com");
        employeeDTO.setDepartmentId(1L);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Amit Kuntal");
        employee.setEmail("amit.kuntal@gmail.com");

        when(departmentService.getDepartmentById(employeeDTO.getDepartmentId())).thenReturn(new Department());
        when(employeeRepository.findByEmail(employeeDTO.getEmail())).thenReturn(null);
        employeeService.saveEmployee(employee);

        verify(employeeRepository).save(employee);
    }

    @Test
    public void testSaveEmployeeThrowsException() {
        Employee employee = new Employee();
        employee.setId(2L);
        employee.setName("Amit Kuntal");
        employee.setEmail("amit.kuntal@gmail.com");
        employee.setDepartment(null); // Mock department or set a dummy value if needed

        Employee employee1 = new Employee();
        employee1.setEmail("amit.kuntal@gmail.com");
        employee1.setId(1L);

        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(employee); // Simulate existing employee with the same email
        EmployeeException thrown = assertThrows(EmployeeException.class, () -> {
            employeeService.saveEmployee(employee1);
        });
        assertEquals("Employee already exist with email amit.kuntal@gmail.com", thrown.getMessage());
    }

    @Test
    public void testGetEmployeeById() throws EmployeeException {
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Amit Kuntal");
        employee.setEmail("amit.kuntal@gmail.com");
        employee.setDepartment(new Department(1l,"HR")); // Mock department or set a dummy value if needed

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(id);
        employeeDTO.setName("Amit Kuntal");
        employeeDTO.setEmail("amit.kuntal@gmail.com");
        employeeDTO.setDepartmentId(1l); // Mock departmentId or set a dummy value if needed

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        Employee result = employeeService.getEmployeeById(id);
        assertEquals(employee.getDepartment().getName(), result.getDepartment().getName());
    }

    @Test
    public void testGetEmployeeByIdThrowsException() {
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        EmployeeException thrown = assertThrows(EmployeeException.class, () -> {
            employeeService.getEmployeeById(id);
        });
        assertEquals("Employee Not found for id  1", thrown.getMessage());
    }

    @Test
    public void testDeleteEmployee() {
        Long id = 1L;

        doNothing().when(employeeRepository).deleteById(id);

        employeeService.deleteEmployee(id);

        verify(employeeRepository).deleteById(id);
    }
}
