package com.employee.management.controller;

import com.employee.management.dto.DepartmentDTO;
import com.employee.management.entity.Department;
import com.employee.management.exception.DepartmentNotFoundException;
import com.employee.management.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDepartments() {
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "HR");
        when(departmentService.getAllDepartment()).thenReturn(Collections.singletonList(departmentDTO));
        String viewName = departmentController.getAllDepartment(model);
        assertEquals("departments", viewName);
        verify(model).addAttribute("departments", Collections.singletonList(departmentDTO));
    }

    @Test
    public void testShowAddDepartmentForm() {
        String viewName = departmentController.addDepartment(model);
        assertEquals("add_department", viewName);
    }

    @Test
    public void testAddDepartment() {
        DepartmentDTO departmentDTO = new DepartmentDTO(null, "HR");
        Department department = new Department();
        when(departmentService.dtoToDepartmentConverter(departmentDTO)).thenReturn(department);
        doNothing().when(departmentService).saveDepartment(department);

        String viewName = departmentController.addDepartment(departmentDTO);
        assertEquals("redirect:/department", viewName);
        verify(departmentService).saveDepartment(department);
    }

    @Test
    public void testGetDepartmentById() throws DepartmentNotFoundException {
        Long id = 1L;
        Department department = new Department();
        DepartmentDTO departmentDTO = new DepartmentDTO(id, "HR");
        when(departmentService.getDepartmentById(id)).thenReturn(department);
        when(departmentService.departmentToDTOConverter(department)).thenReturn(departmentDTO);

        String viewName = departmentController.getDepartmentByid(id, model);
        assertEquals("update_department", viewName);
        verify(model).addAttribute("department", departmentDTO);
    }

    @Test
    public void testGetDepartmentByIdThrowsException() throws DepartmentNotFoundException {
        Long id = 1L;
        when(departmentService.getDepartmentById(id)).thenThrow(new DepartmentNotFoundException("Department not found"));

        String viewName = departmentController.getDepartmentByid(id, model);
        assertEquals("departments", viewName);
        verify(model).addAttribute("error", "Department not found");
    }

    @Test
    public void testUpdateDepartment() throws DepartmentNotFoundException {
        Long id = 1L;
        String departmentName = "Finance";
        DepartmentDTO departmentDTO = new DepartmentDTO(id, departmentName);
        Department department = new Department();
        when(departmentService.dtoToDepartmentConverter(departmentDTO)).thenReturn(department);
        doNothing().when(departmentService).saveDepartment(department);
        String viewName = departmentController.addDepartment(departmentDTO);
        assertEquals("redirect:/department", viewName);
        verify(departmentService).saveDepartment(department);
    }

    @Test
    public void testDeleteDepartment() {
        Long id = 1L;
        doNothing().when(departmentService).deleteDepartment(id);

        String viewName = departmentController.deleteDepartment(id);
        assertEquals("redirect:/department", viewName);
        verify(departmentService).deleteDepartment(id);
    }
}
