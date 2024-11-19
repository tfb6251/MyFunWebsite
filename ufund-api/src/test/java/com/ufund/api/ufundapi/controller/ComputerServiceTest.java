package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Computer;
import com.ufund.api.ufundapi.persistence.ComputerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComputerServiceTest {

    private ComputerService computerService;
    private ComputerDAO mockComputerDAO;

    @BeforeEach
    public void setup() {
        mockComputerDAO = mock(ComputerDAO.class);
        computerService = new ComputerService(mockComputerDAO);
    }

    // Test getComputers()
    @Test
    public void testGetComputers() throws IOException {
        Computer[] computers = new Computer[]{
                new Computer(1, "Computer1", 1000, 5, "BrandA", ""),
                new Computer(2, "Computer2", 1500, 3, "BrandB", "")
        };
        when(mockComputerDAO.getComputers()).thenReturn(computers);

        Computer[] result = computerService.getComputers();

        assertArrayEquals(computers, result);
    }

    @Test
    public void testGetComputersEmpty() throws IOException {
        Computer[] computers = new Computer[]{};
        when(mockComputerDAO.getComputers()).thenReturn(computers);

        Computer[] result = computerService.getComputers();

        assertArrayEquals(computers, result);
    }

    @Test
    public void testGetComputersThrowsException() throws IOException {
        when(mockComputerDAO.getComputers()).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            computerService.getComputers();
        });
    }

    // Test getComputer(int id)
    @Test
    public void testGetComputer() throws IOException {
        Computer computer = new Computer(1, "Computer1", 1000, 5, "BrandA", "");
        when(mockComputerDAO.getComputer(1)).thenReturn(computer);

        Computer result = computerService.getComputer(1);

        assertEquals(computer, result);
    }

    @Test
    public void testGetComputerNotFound() throws IOException {
        when(mockComputerDAO.getComputer(1)).thenReturn(null);

        Computer result = computerService.getComputer(1);

        assertNull(result);
    }

    @Test
    public void testGetComputerThrowsException() throws IOException {
        when(mockComputerDAO.getComputer(1)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            computerService.getComputer(1);
        });
    }

    // Test findComputers(String name)
    @Test
    public void testFindComputers() throws IOException {
        String searchString = "Gaming";
        Computer[] computers = new Computer[]{
                new Computer(1, "Gaming PC", 2000, 2, "BrandC", ""),
                new Computer(2, "Gaming Laptop", 2500, 1, "BrandD", "")
        };
        when(mockComputerDAO.findComputers(searchString)).thenReturn(computers);

        Computer[] result = computerService.findComputers(searchString);

        assertArrayEquals(computers, result);
    }

    @Test
    public void testFindComputersNotFound() throws IOException {
        String searchString = "Nonexistent";
        Computer[] computers = new Computer[]{};
        when(mockComputerDAO.findComputers(searchString)).thenReturn(computers);

        Computer[] result = computerService.findComputers(searchString);

        assertArrayEquals(computers, result);
    }

    @Test
    public void testFindComputersThrowsException() throws IOException {
        String searchString = "Error";
        when(mockComputerDAO.findComputers(searchString)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            computerService.findComputers(searchString);
        });
    }

    // Test createComputer(Computer computer)
    @Test
    public void testCreateComputer() throws IOException {
        Computer computer = new Computer(3, "New PC", 1200, 4, "BrandE", "");
        when(mockComputerDAO.createComputer(computer)).thenReturn(computer);

        Computer result = computerService.createComputer(computer);

        assertEquals(computer, result);
    }

    @Test
    public void testCreateComputerAlreadyExists() throws IOException {
        Computer computer = new Computer(3, "Existing PC", 1200, 4, "BrandE", "");
        when(mockComputerDAO.createComputer(computer)).thenReturn(null);

        Computer result = computerService.createComputer(computer);

        assertNull(result);
    }

    @Test
    public void testCreateComputerThrowsException() throws IOException {
        Computer computer = new Computer(3, "Error PC", 1200, 4, "BrandE", "");
        when(mockComputerDAO.createComputer(computer)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            computerService.createComputer(computer);
        });
    }

    // Test updateComputer(Computer computer)
    @Test
    public void testUpdateComputer() throws IOException {
        Computer computer = new Computer(1, "Updated PC", 1300, 3, "BrandA", "");
        when(mockComputerDAO.updateComputer(computer)).thenReturn(computer);

        Computer result = computerService.updateComputer(computer);

        assertEquals(computer, result);
    }

    @Test
    public void testUpdateComputerNotFound() throws IOException {
        Computer computer = new Computer(1, "Nonexistent PC", 1300, 3, "BrandA", "");
        when(mockComputerDAO.updateComputer(computer)).thenReturn(null);

        Computer result = computerService.updateComputer(computer);

        assertNull(result);
    }

    @Test
    public void testUpdateComputerThrowsException() throws IOException {
        Computer computer = new Computer(1, "Error PC", 1300, 3, "BrandA", "");
        when(mockComputerDAO.updateComputer(computer)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            computerService.updateComputer(computer);
        });
    }

    // Test deleteComputer(int id)
    @Test
    public void testDeleteComputer() throws IOException {
        int computerId = 1;
        when(mockComputerDAO.deleteComputer(computerId)).thenReturn(true);

        boolean result = computerService.deleteComputer(computerId);

        assertTrue(result);
    }

    @Test
    public void testDeleteComputerNotFound() throws IOException {
        int computerId = 1;
        when(mockComputerDAO.deleteComputer(computerId)).thenReturn(false);

        boolean result = computerService.deleteComputer(computerId);

        assertFalse(result);
    }

    @Test
    public void testDeleteComputerThrowsException() throws IOException {
        int computerId = 1;
        when(mockComputerDAO.deleteComputer(computerId)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            computerService.deleteComputer(computerId);
        });
    }
}
