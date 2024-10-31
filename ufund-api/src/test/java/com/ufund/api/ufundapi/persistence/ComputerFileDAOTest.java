package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Computer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the ComputerFileDAO class.
 */
public class ComputerFileDAOTest {
    private ComputerFileDAO computerFileDAO;
    private Computer[] testComputers;
    private ObjectMapper mockObjectMapper;
    private String testFilename = "test_data/testcomputers.json";

    /**
     * Setup method executed before each test.
     * Initializes the mock ObjectMapper and ComputerFileDAO.
     */
    @BeforeEach
    public void setup() throws IOException {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        
        // Initialize the mock ObjectMapper
        mockObjectMapper = mock(ObjectMapper.class);
        
        // Initialize test data
        testComputers = new Computer[] {
            new Computer(0, "Obiwan", 1000000, 1000, "Toshiba"),
            new Computer(1, "QuiGon", 100000, 100, "Toyota"),
            new Computer(2, "Yoda", 10000, 10, "Honda")
        };
        
        // Define the test filename with directory path
        File file = new File(testFilename);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean dirsCreated = parentDir.mkdirs();
            if (!dirsCreated && !parentDir.exists()) {
                throw new IOException("Failed to create directories for the test file.");
            }
        }
        
        // Mock ObjectMapper.readValue to return testComputers when reading the test file
        when(mockObjectMapper.readValue(eq(file), eq(Computer[].class)))
            .thenReturn(testComputers);
        
        // Initialize ComputerFileDAO with mocked ObjectMapper
        computerFileDAO = new ComputerFileDAO(testFilename, mockObjectMapper);
        
        // Clean up the test file to ensure a clean state for tests that may write to it
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new IOException("Failed to delete existing test file.");
            }
        }
    }

    /**
     * Test that the constructor correctly loads computers from the file.
     */
    @Test
    public void testConstructorLoadsComputers() {
        // Assert that computerMap has the correct number of computers
        assertNotNull(computerFileDAO.computerMap, "computerMap should not be null after constructor.");
        assertEquals(3, computerFileDAO.computerMap.size(), "computerMap should contain 3 computers.");
        
        // Assert that the computers are correctly loaded
        for (Computer c : testComputers) {
            assertTrue(computerFileDAO.computerMap.containsKey(c.getId()), 
                "computerMap should contain computer with id " + c.getId());
            assertEquals(c, computerFileDAO.computerMap.get(c.getId()), 
                "Computer in computerMap should match testComputer.");
        }
    }
    
    /**
     * Test retrieving an existing computer by ID.
     */
    @Test
    public void testGetComputerExisting() {
        // Act
        Computer computer = computerFileDAO.getComputer(1);
        
        // Assert
        assertNotNull(computer, "getComputer should return a Computer object for existing id.");
        assertEquals(testComputers[1], computer, "getComputer should return the correct Computer.");
    }
    
    /**
     * Test retrieving a non-existing computer by ID.
     */
    @Test
    public void testGetComputerNonExisting() {
        // Act
        Computer computer = computerFileDAO.getComputer(99);
        
        // Assert
        assertNull(computer, "getComputer should return null for non-existing id.");
    }
    
    /**
     * Test retrieving all computers.
     */
    @Test
    public void testGetComputers() {
        // Act
        Computer[] computers = computerFileDAO.getComputers();
        
        // Assert
        assertNotNull(computers, "getComputers should not return null.");
        assertArrayEquals(testComputers, computers, "getComputers should return all computers.");
    }
    
    /**
     * Test finding computers with names containing specific text.
     */
    @Test
    public void testFindComputersWithMatch() {
        // Arrange
        String searchText = "n"; // matches "Obiwan" and "QuiGon"
        
        // Act
        Computer[] foundComputers = computerFileDAO.findComputers(searchText);
        
        // Assert
        assertNotNull(foundComputers, "findComputers should not return null.");
        assertEquals(2, foundComputers.length, "findComputers should return 2 computers.");
        assertEquals(testComputers[0], foundComputers[0], "First found computer should match testComputers[0].");
        assertEquals(testComputers[1], foundComputers[1], "Second found computer should match testComputers[1].");
    }
    
    /**
     * Test finding computers with names containing text that matches none.
     */
    @Test
    public void testFindComputersNoMatch() {
        // Arrange
        String searchText = "xyz";
        
        // Act
        Computer[] foundComputers = computerFileDAO.findComputers(searchText);
        
        // Assert
        assertNotNull(foundComputers, "findComputers should not return null.");
        assertEquals(0, foundComputers.length, "findComputers should return 0 computers for no matches.");
    }
    
    /**
     * Test finding computers with null search text (should return all computers).
     */
    @Test
    public void testFindComputersNullSearchText() {
        // Act
        Computer[] computers = computerFileDAO.findComputers(null);
        
        // Assert
        assertNotNull(computers, "findComputers with null should not return null.");
        assertArrayEquals(testComputers, computers, "findComputers with null should return all computers.");
    }
    
    /**
     * Test creating a new computer successfully.
     */
    @Test
    public void testCreateComputer() throws IOException {
        // Arrange
        // Computer with id 0, id will be assigned to 3
        Computer newComputerInput = new Computer(0, "MaceWindu", 5000, 5, "Dell");
        Computer expectedComputer = new Computer(3, "MaceWindu", 5000, 5, "Dell");
        
        // Mock the ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Act
        Computer createdComputer = computerFileDAO.createComputer(newComputerInput);
        
        // Assert
        assertNotNull(createdComputer, "createComputer should return the created Computer.");
        assertEquals(expectedComputer.getId(), createdComputer.getId(), "Created Computer should have the next available ID.");
        assertEquals(expectedComputer.getName(), createdComputer.getName(), "Computer name should match.");
        assertEquals(expectedComputer.getCost(), createdComputer.getCost(), "Computer cost should match.");
        assertEquals(expectedComputer.getQuantity(), createdComputer.getQuantity(), "Computer quantity should match.");
        assertEquals(expectedComputer.getBrand(), createdComputer.getBrand(), "Computer brand should match.");
        
        // Verify that writeValue was called with the updated computer array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Verify that the new computer is in computerMap
        assertTrue(computerFileDAO.computerMap.containsKey(expectedComputer.getId()), 
            "computerMap should contain the new computer.");
        assertEquals(createdComputer, computerFileDAO.computerMap.get(expectedComputer.getId()), 
            "computerMap should contain the correct new computer.");
    }
    
    /**
     * Test creating a new computer when ObjectMapper.writeValue throws an IOException.
     */
    @Test
    public void testCreateComputerIOException() throws IOException {
        // Arrange
        Computer newComputerInput = new Computer(0, "MaceWindu", 5000, 5, "Dell");
        
        // Mock ObjectMapper.writeValue to throw IOException
        doThrow(new IOException("Failed to write to file")).when(mockObjectMapper).writeValue(any(File.class), any(Computer[].class));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            computerFileDAO.createComputer(newComputerInput);
        }, "createComputer should throw IOException when writeValue fails.");
        
        // Verify that the new computer is not in computerMap
        assertFalse(computerFileDAO.computerMap.containsKey(3), "computerMap should not contain the new computer after IOException.");
    }
    
    /**
     * Test updating an existing computer successfully.
     */
    @Test
    public void testUpdateComputerExisting() throws IOException {
        // Arrange
        Computer updatedComputerInput = new Computer(1, "QuiGon Jinn", 120000, 120, "Toyota Premium");
        Computer updatedComputer = new Computer(1, "QuiGon Jinn", 120000, 120, "Toyota Premium");
        
        // Mock the ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Act
        Computer result = computerFileDAO.updateComputer(updatedComputerInput);
        
        // Assert
        assertNotNull(result, "updateComputer should return the updated Computer.");
        assertEquals(updatedComputer, result, "updateComputer should return the correct updated Computer.");
        
        // Verify that writeValue was called with the updated computer array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Verify that the computerMap has the updated computer
        assertEquals(updatedComputer, computerFileDAO.computerMap.get(updatedComputer.getId()), 
            "computerMap should contain the updated computer.");
    }
    
    /**
     * Test updating a non-existing computer (should return null).
     */
    @Test
    public void testUpdateComputerNonExisting() throws IOException {
        // Arrange
        Computer nonExistingComputer = new Computer(99, "NonExistent", 1000, 1, "NonBrand");
        
        // Act
        Computer result = computerFileDAO.updateComputer(nonExistingComputer);
        
        // Assert
        assertNull(result, "updateComputer should return null when the computer does not exist.");
        
        // Verify that writeValue was not called
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(Computer[].class));
    }
    
    /**
     * Test updating a computer when ObjectMapper.writeValue throws an IOException.
     */
    @Test
    public void testUpdateComputerIOException() throws IOException {
        // Arrange
        Computer updatedComputerInput = new Computer(1, "QuiGon Jinn", 120000, 120, "Toyota Premium");
        
        // Mock ObjectMapper.writeValue to throw IOException
        doThrow(new IOException("Failed to write to file")).when(mockObjectMapper).writeValue(any(File.class), any(Computer[].class));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            computerFileDAO.updateComputer(updatedComputerInput);
        }, "updateComputer should throw IOException when writeValue fails.");
        
        // Verify that the computerMap still contains the updated computer
        assertEquals(testComputers[1], computerFileDAO.computerMap.get(updatedComputerInput.getId()), 
            "computerMap should still contain the computer after IOException.");
    }
    
    /**
     * Test deleting an existing computer successfully.
     */
    @Test
    public void testDeleteComputerExisting() throws IOException {
        // Arrange
        int idToDelete = 1;
        Computer[] expectedComputersAfterDelete = new Computer[] {
            testComputers[0],
            testComputers[2]
        };
        
        // Mock ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Act
        boolean result = assertDoesNotThrow(() -> computerFileDAO.deleteComputer(idToDelete),
                                "Unexpected exception thrown");
        
        // Assert
        assertTrue(result, "deleteComputer should return true for existing computer.");
        
        // Verify that writeValue was called with the updated computer array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Verify that the computerMap no longer contains the deleted computer
        assertFalse(computerFileDAO.computerMap.containsKey(idToDelete), 
            "computerMap should not contain the deleted computer.");
    }
    
    /**
     * Test deleting a non-existing computer (should return false).
     */
    @Test
    public void testDeleteComputerNonExisting() throws IOException {
        // Arrange
        int idToDelete = 99;
        
        // Act
        boolean result = assertDoesNotThrow(() -> computerFileDAO.deleteComputer(idToDelete),
                                "Unexpected exception thrown");
        
        // Assert
        assertFalse(result, "deleteComputer should return false for non-existing computer.");
        
        // Verify that writeValue was not called
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(Computer[].class));
        
        // Verify that computerMap size remains unchanged
        assertEquals(testComputers.length, computerFileDAO.computerMap.size(), 
            "computerMap size should remain unchanged.");
    }
    
    /**
     * Test deleting a computer when ObjectMapper.writeValue throws an IOException.
     */
    @Test
    public void testDeleteComputerIOException() throws IOException {
        // Arrange
        int idToDelete = 1;
        Computer[] expectedComputersAfterDelete = new Computer[] {
            testComputers[0],
            testComputers[2]
        };
        
        // Mock ObjectMapper.writeValue to throw IOException
        doThrow(new IOException("Failed to write to file")).when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(Computer[].class));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            computerFileDAO.deleteComputer(idToDelete);
        }, "deleteComputer should throw IOException when writeValue fails.");
        
        // Verify that the computerMap still contains the deleted computer since save failed
        assertTrue(computerFileDAO.computerMap.containsKey(idToDelete), 
            "computerMap should still contain the computer after IOException.");
    }
    
    /**
     * Test creating a computer with an ID that already exists (should assign a new unique ID).
     */
    @Test
    public void testCreateComputerDuplicateId() throws IOException {
        // Arrange
        // Computer with id 1 already exists
        Computer newComputerInput = new Computer(1, "DuplicateIdComputer", 3000, 3, "BrandX");
        Computer expectedComputer = new Computer(3, "DuplicateIdComputer", 3000, 3, "BrandX");
        Computer[] expectedComputersAfterCreate = new Computer[] {
            testComputers[0],
            testComputers[1],
            testComputers[2],
            expectedComputer
        };
        
        // Mock ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), eq(expectedComputersAfterCreate));
        
        // Act
        Computer createdComputer = computerFileDAO.createComputer(newComputerInput);
        
        // Assert
        assertNotNull(createdComputer, "createComputer should return the created Computer.");
        assertEquals(expectedComputer.getId(), createdComputer.getId(), "Created Computer should have a new unique ID.");
        assertEquals(expectedComputer.getName(), createdComputer.getName(), "Computer name should match.");
        assertEquals(expectedComputer.getCost(), createdComputer.getCost(), "Computer cost should match.");
        assertEquals(expectedComputer.getQuantity(), createdComputer.getQuantity(), "Computer quantity should match.");
        assertEquals(expectedComputer.getBrand(), createdComputer.getBrand(), "Computer brand should match.");
        
        // Verify that writeValue was called with the updated computer array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), eq(expectedComputersAfterCreate));
        
        // Verify that the new computer is in computerMap
        assertTrue(computerFileDAO.computerMap.containsKey(expectedComputer.getId()), 
            "computerMap should contain the new computer with unique ID.");
        assertEquals(createdComputer, computerFileDAO.computerMap.get(expectedComputer.getId()), 
            "computerMap should contain the correct new computer.");
    }
    
    /**
     * Test that the constructor throws an IOException when ObjectMapper.readValue fails.
     */
    @Test
    public void testConstructorIOException() throws IOException {
        // Arrange
        ObjectMapper faultyObjectMapper = mock(ObjectMapper.class);
        File faultyFile = new File(testFilename);
        
        // Mock ObjectMapper.readValue to throw IOException
        when(faultyObjectMapper.readValue(eq(faultyFile), eq(Computer[].class)))
            .thenThrow(new IOException("Failed to read file"));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            new ComputerFileDAO(testFilename, faultyObjectMapper);
        }, "ComputerFileDAO constructor should throw IOException when readValue fails.");
    }
}
