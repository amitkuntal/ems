package com.employee.management.controller;

import com.employee.management.dto.EmployeeDTO;
import com.employee.management.entity.Employee;
import com.employee.management.exception.EmployeeNotFoundException;
import com.employee.management.service.DepartmentService;
import com.employee.management.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewHomePage() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        when(employeeService.getAllEmployee()).thenReturn(Collections.singletonList(employeeDTO));

        String viewName = employeeController.viewHomePage(model);
        assertEquals("index", viewName);
        verify(model).addAttribute("listEmployees", Collections.singletonList(employeeDTO));
    }

    @Test
    public void testNewEmployeeForm() {
        when(departmentService.getAllDepartment()).thenReturn(Collections.emptyList());

        String viewName = employeeController.newEmployeeForm(model);
        assertEquals("newEmployeeForm", viewName);
    }

    @Test
    public void testSaveEmployee() throws EmployeeNotFoundException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee employee = new Employee();
        when(employeeService.dtoToEmployeeConverter(employeeDTO)).thenReturn(employee);
        doNothing().when(employeeService).saveEmployee(employee);

        String viewName = employeeController.saveEmployee(employeeDTO, model);
        assertEquals("redirect:/", viewName);
        verify(employeeService).saveEmployee(employee);
    }


    @Test
    public void testUpdateEmployeeThrowsException() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        when(employeeService.dtoToEmployeeConverter(employeeDTO)).thenThrow(new RuntimeException("Error"));

        String viewName = employeeController.updateEmployee(employeeDTO, model);
        assertEquals("update_employee", viewName);
        verify(model).addAttribute("error", "Error");
    }

    @Test
    public void testEditEmployee() throws EmployeeNotFoundException {
        Long id = 1L;
        Employee employee = new Employee();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        when(employeeService.getEmployeeById(id)).thenReturn(employee);
        when(employeeService.employeeToDtoConverter(employee)).thenReturn(employeeDTO);
        when(departmentService.getAllDepartment()).thenReturn(Collections.emptyList());

        String viewName = employeeController.editEmployee(id, model);
        assertEquals("update_employee", viewName);
        verify(model).addAttribute("employee", employeeDTO);
        verify(model).addAttribute("departments", Collections.emptyList());
    }

    @Test
    public void testEditEmployeeThrowsException() throws EmployeeNotFoundException {
        Long id = 1L;
        when(employeeService.getEmployeeById(id)).thenThrow(new EmployeeNotFoundException("Error"));

        String viewName = employeeController.editEmployee(id, model);
        assertEquals("index", viewName);
        verify(model).addAttribute("error", "Error");
    }

    @Test
    public void testDeleteEmployee() {
        Long id = 1L;
        doNothing().when(employeeService).deleteEmployee(id);

        String viewName = employeeController.deleteEmployee(id);
        assertEquals("redirect:/", viewName);
        verify(employeeService).deleteEmployee(id);
    }
}
