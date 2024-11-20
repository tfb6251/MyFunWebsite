package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComputerControllerTest {
    private ComputerController computerController;
    private ComputerService mockComputerService;

    @BeforeEach
    public void setupComputerController() {
        mockComputerService = mock(ComputerService.class);
        computerController = new ComputerController(mockComputerService);
    }

    // Test for getting a computer by ID
    @Test
    public void testGetComputerById() throws IOException {
        Computer computer = new Computer(1, "Laptop", 1000, 5, "BrandA", "");
        when(mockComputerService.getComputer(computer.getId())).thenReturn(computer);

        ResponseEntity<Computer> response = computerController.getComputer(computer.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(computer, response.getBody());
    }

    // Test for computer not found by ID
    @Test
    public void testGetComputerByIdNotFound() throws IOException {
        int computerId = 1;
        when(mockComputerService.getComputer(computerId)).thenReturn(null);

        ResponseEntity<Computer> response = computerController.getComputer(computerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when getting computer by ID
    @Test
    public void testGetComputerByIdHandleException() throws IOException {
        int computerId = 1;
        doThrow(new IOException()).when(mockComputerService).getComputer(computerId);

        ResponseEntity<Computer> response = computerController.getComputer(computerId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for creating a new computer
    @Test
    public void testCreateComputer() throws IOException {
        Computer computer = new Computer(2, "Desktop", 1500, 3, "BrandB", "");
        when(mockComputerService.createComputer(computer)).thenReturn(computer);

        ResponseEntity<Computer> response = computerController.createComputer(computer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(computer, response.getBody());
    }

    // Test for conflict when creating a computer that already exists
    @Test
    public void testCreateComputerConflict() throws IOException {
        Computer computer = new Computer(2, "ExistingComputer", 1200, 4, "BrandC", "");
        when(mockComputerService.createComputer(computer)).thenReturn(null);

        ResponseEntity<Computer> response = computerController.createComputer(computer);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    // Test for exception when creating a computer
    @Test
    public void testCreateComputerHandleException() throws IOException {
        Computer computer = new Computer(2, "Computer", 1100, 2, "BrandD", "");
        doThrow(new IOException()).when(mockComputerService).createComputer(computer);

        ResponseEntity<Computer> response = computerController.createComputer(computer);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for updating a computer
    @Test
    public void testUpdateComputer() throws IOException {
        Computer computer = new Computer(1, "UpdatedComputer", 1300, 6, "BrandE", "");
        when(mockComputerService.updateComputer(computer)).thenReturn(computer);

        ResponseEntity<Computer> response = computerController.updateComputer(computer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(computer, response.getBody());
    }

    // Test for computer not found when updating
    @Test
    public void testUpdateComputerNotFound() throws IOException {
        Computer computer = new Computer(1, "Computer", 1000, 5, "BrandF", "");
        when(mockComputerService.updateComputer(computer)).thenReturn(null);

        ResponseEntity<Computer> response = computerController.updateComputer(computer);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when updating a computer
    @Test
    public void testUpdateComputerHandleException() throws IOException {
        Computer computer = new Computer(1, "Computer", 1000, 5, "BrandG", "");
        doThrow(new IOException()).when(mockComputerService).updateComputer(computer);

        ResponseEntity<Computer> response = computerController.updateComputer(computer);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for deleting a computer
    @Test
    public void testDeleteComputer() throws IOException {
        int computerId = 1;
        when(mockComputerService.deleteComputer(computerId)).thenReturn(true);

        ResponseEntity<Computer> response = computerController.deleteComputer(computerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test for computer not found when deleting
    @Test
    public void testDeleteComputerNotFound() throws IOException {
        int computerId = 1;
        when(mockComputerService.deleteComputer(computerId)).thenReturn(false);

        ResponseEntity<Computer> response = computerController.deleteComputer(computerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when deleting a computer
    @Test
    public void testDeleteComputerHandleException() throws IOException {
        int computerId = 1;
        doThrow(new IOException()).when(mockComputerService).deleteComputer(computerId);

        ResponseEntity<Computer> response = computerController.deleteComputer(computerId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for getting all computers
    @Test
    public void testFindComputers() throws IOException {
        Computer[] computers = new Computer[] {
            new Computer(1, "Laptop", 1000, 5, "BrandA", ""),
            new Computer(2, "Desktop", 1500, 3, "BrandB", "")
        };

        when(mockComputerService.getComputers()).thenReturn(computers);

        ResponseEntity<Computer[]> response = computerController.findComputers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(computers, response.getBody());
    }

    // Test for exception when getting all computers
    @Test
    public void testFindComputersHandleException() throws IOException {
        doThrow(new IOException()).when(mockComputerService).getComputers();

        ResponseEntity<Computer[]> response = computerController.findComputers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for searching computers by name
    @Test
    public void testSearchComputers() throws IOException {
        String searchString = "Laptop";
        Computer[] computers = new Computer[] {
            new Computer(1, "Gaming Laptop", 2000, 2, "BrandX", ""),
            new Computer(2, "Ultrabook Laptop", 1800, 4, "BrandY", "")
        };

        when(mockComputerService.findComputers(searchString)).thenReturn(computers);

        ResponseEntity<Computer[]> response = computerController.searchComputers(searchString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(computers, response.getBody());
    }

    // Test for computers not found when searching
    @Test
    public void testSearchComputersNotFound() throws IOException {
        String searchString = "Nonexistent";
        when(mockComputerService.findComputers(searchString)).thenReturn(null);

        ResponseEntity<Computer[]> response = computerController.searchComputers(searchString);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when searching computers
    @Test
    public void testSearchComputersHandleException() throws IOException {
        String searchString = "Laptop";
        doThrow(new IOException()).when(mockComputerService).findComputers(searchString);

        ResponseEntity<Computer[]> response = computerController.searchComputers(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
