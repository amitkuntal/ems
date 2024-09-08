package com.employee.management.service;

import com.employee.management.dto.DepartmentDTO;
import com.employee.management.entity.Department;
import com.employee.management.exception.DepartmentNotFoundException;
import com.employee.management.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDepartmentById() throws DepartmentNotFoundException {
        Long id = 1L;
        Department department = new Department();
        department.setId(id);
        department.setName("HR");

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("HR", result.getName());
    }

    @Test
    public void testGetDepartmentByIdThrowsException() {
        Long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        DepartmentNotFoundException thrown = assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(id));
        assertEquals("Department Not found " + id, thrown.getMessage());
    }

    @Test
    public void testSaveDepartment() {
        Department department = new Department();
        department.setName("HR");
        when(departmentRepository.save(any())).thenReturn(any());
        departmentService.saveDepartment(department);
        verify(departmentRepository).save(department);
    }

    @Test
    public void testGetAllDepartments() {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("HR");

        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        assertEquals(Collections.singletonList(departmentDTO).size(), departmentService.getAllDepartment().size());
    }

    @Test
    public void testDeleteDepartment() {
        Long id = 1L;

        doNothing().when(departmentRepository).deleteById(id);

        departmentService.deleteDepartment(id);

        verify(departmentRepository).deleteById(id);
    }

    @Test
    public void testDepartmentToDTOConverter() {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");

        DepartmentDTO departmentDTO = departmentService.departmentToDTOConverter(department);

        assertNotNull(departmentDTO);
        assertEquals(department.getId(), departmentDTO.getId());
        assertEquals(department.getName(), departmentDTO.getName());
    }

    @Test
    public void testDtoToDepartmentConverter() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("HR");

        Department department = departmentService.dtoToDepartmentConverter(departmentDTO);

        assertNotNull(department);
        assertEquals(departmentDTO.getId(), department.getId());
        assertEquals(departmentDTO.getName(), department.getName());
    }
}
