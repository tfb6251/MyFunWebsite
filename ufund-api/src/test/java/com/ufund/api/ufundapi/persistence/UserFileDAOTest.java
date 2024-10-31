package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the UserFileDAO class.
 */
public class UserFileDAOTest {
    private UserFileDAO userFileDAO;
    private User[] testUsers;
    private ObjectMapper mockObjectMapper;
    private String testFilename = "test_data/testusers.json";

    /**
     * Setup method executed before each test.
     * Initializes the mock ObjectMapper and UserFileDAO.
     */
    @BeforeEach
    public void setup() throws IOException {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        
        // Initialize the mock ObjectMapper
        mockObjectMapper = mock(ObjectMapper.class);
        
        // Initialize test data
        testUsers = new User[] {
            new User(0, "Alice", "passwordAlice", null),
            new User(1, "Bob", "passwordBob", null),
            new User(2, "Charlie", "passwordCharlie", null)
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
        
        // Mock ObjectMapper.readValue to return testUsers when reading the test file
        when(mockObjectMapper.readValue(eq(file), eq(User[].class)))
            .thenReturn(testUsers);
        
        // Initialize UserFileDAO with mocked ObjectMapper
        userFileDAO = new UserFileDAO(testFilename, mockObjectMapper);
        
        // Clean up the test file to ensure a clean state for tests that may write to it
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new IOException("Failed to delete existing test file.");
            }
        }
    }

    /**
     * Test that the constructor correctly loads users from the file.
     */
    @Test
    public void testConstructorLoadsUsers() {
        // Assert that userMap has the correct number of users
        assertNotNull(userFileDAO.userMap, "userMap should not be null after constructor.");
        assertEquals(3, userFileDAO.userMap.size(), "userMap should contain 3 users.");
        
        // Assert that the users are correctly loaded
        for (User u : testUsers) {
            assertTrue(userFileDAO.userMap.containsKey(u.getId()), 
                "userMap should contain user with id " + u.getId());
            assertEquals(u, userFileDAO.userMap.get(u.getId()), 
                "User in userMap should match testUser.");
        }
    }
    
    /**
     * Test retrieving an existing user by ID.
     */
    @Test
    public void testGetUserExisting() {
        // Act
        User user = userFileDAO.getUser(1);
        
        // Assert
        assertNotNull(user, "getUser should return a User object for existing id.");
        assertEquals(testUsers[1], user, "getUser should return the correct User.");
    }
    
    /**
     * Test retrieving a non-existing user by ID.
     */
    @Test
    public void testGetUserNonExisting() {
        // Act
        User user = userFileDAO.getUser(99);
        
        // Assert
        assertNull(user, "getUser should return null for non-existing id.");
    }
    
    /**
     * Test retrieving all users.
     */
    @Test
    public void testGetUsers() {
        // Act
        User[] users = userFileDAO.getUsers();
        
        // Assert
        assertNotNull(users, "getUsers should not return null.");
        assertArrayEquals(testUsers, users, "getUsers should return all users.");
    }
    
    /**
     * Test finding users with names containing specific text.
     */
    @Test
    public void testFindUsersWithMatch() {
        // Arrange
        String searchText = "e"; // matches "Alice" and "Charlie"
        
        // Act
        User[] foundUsers = userFileDAO.findUsers(searchText);
        
        // Assert
        assertNotNull(foundUsers, "findUsers should not return null.");
        assertEquals(2, foundUsers.length, "findUsers should return 2 users.");
        assertEquals(testUsers[0], foundUsers[0], "First found user should match testUsers[0].");
        assertEquals(testUsers[2], foundUsers[1], "Second found user should match testUsers[2].");
    }
    
    /**
     * Test finding users with names containing text that matches none.
     */
    @Test
    public void testFindUsersNoMatch() {
        // Arrange
        String searchText = "xyz";
        
        // Act
        User[] foundUsers = userFileDAO.findUsers(searchText);
        
        // Assert
        assertNotNull(foundUsers, "findUsers should not return null.");
        assertEquals(0, foundUsers.length, "findUsers should return 0 users for no matches.");
    }
    
    /**
     * Test finding users with null search text (should return all users).
     */
    @Test
    public void testFindUsersNullSearchText() {
        // Act
        User[] users = userFileDAO.findUsers(null);
        
        // Assert
        assertNotNull(users, "findUsers with null should not return null.");
        assertArrayEquals(testUsers, users, "findUsers with null should return all users.");
    }
    
    /**
     * Test creating a new user successfully.
     */
    @Test
    public void testCreateUser() throws IOException {
        // Arrange
        // User with id 0, id will be assigned to 3
        User newUserInput = new User(0, "Diana", "passwordDiana", null);
        User expectedUser = new User(3, "Diana", "passwordDiana", null);
        
        // Mock the ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(User[].class));
        
        // Act
        User createdUser = userFileDAO.createUser(newUserInput);
        
        // Assert
        assertNotNull(createdUser, "createUser should return the created User.");
        assertEquals(expectedUser.getId(), createdUser.getId(), "Created User should have the next available ID.");
        assertEquals(expectedUser.getName(), createdUser.getName(), "User name should match.");
        assertEquals(expectedUser.getPassword(), createdUser.getPassword(), "User password should match.");
        assertTrue(Arrays.equals(expectedUser.getBasket(), createdUser.getBasket()), "User basket should match.");
        
        // Verify that writeValue was called with the updated user array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), any(User[].class));
        
        // Verify that the new user is in userMap
        assertTrue(userFileDAO.userMap.containsKey(expectedUser.getId()), 
            "userMap should contain the new user.");
        assertEquals(createdUser, userFileDAO.userMap.get(expectedUser.getId()), 
            "userMap should contain the correct new user.");
    }
    
    /**
     * Test creating a new user when ObjectMapper.writeValue throws an IOException.
     */
    @Test
    public void testCreateUserIOException() throws IOException {
        // Arrange
        User newUserInput = new User(0, "Diana", "passwordDiana", null);
        
        // Mock ObjectMapper.writeValue to throw IOException
        doThrow(new IOException("Failed to write to file")).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            userFileDAO.createUser(newUserInput);
        }, "createUser should throw IOException when writeValue fails.");
        
        // Verify that the new user is not in userMap
        assertFalse(userFileDAO.userMap.containsKey(3), "userMap should not contain the new user after IOException.");
    }
    
    /**
     * Test updating an existing user successfully.
     */
    @Test
    public void testUpdateUserExisting() throws IOException {
        // Arrange
        User updatedUserInput = new User(1, "Bob Smith", "newPasswordBob", null);
        User updatedUser = new User(1, "Bob Smith", "newPasswordBob", null);
        
        // Mock the ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(User[].class));
        
        // Act
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(updatedUserInput),
                                "Unexpected exception thrown");
        
        // Assert
        assertNotNull(result, "updateUser should return the updated User.");
        assertEquals(updatedUser, result, "updateUser should return the correct updated User.");
        
        // Verify that writeValue was called with the updated user array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), any(User[].class));
        
        // Verify that the userMap has the updated user
        assertEquals(updatedUser, userFileDAO.userMap.get(updatedUser.getId()), 
            "userMap should contain the updated user.");
    }
    
    /**
     * Test updating a non-existing user (should return null).
     */
    @Test
    public void testUpdateUserNonExisting() throws IOException {
        // Arrange
        User nonExistingUser = new User(99, "NonExistent", "noPassword", null);
        
        // Act
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(nonExistingUser),
                                "Unexpected exception thrown");
        
        // Assert
        assertNull(result, "updateUser should return null when the user does not exist.");
        
        // Verify that writeValue was not called
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(User[].class));
    }
    
    /**
     * Test updating a user when ObjectMapper.writeValue throws an IOException.
     */
    @Test
    public void testUpdateUserIOException() throws IOException {
        // Arrange
        User updatedUserInput = new User(1, "Bob Smith", "newPasswordBob", null);
        
        // Mock ObjectMapper.writeValue to throw IOException
        doThrow(new IOException("Failed to write to file")).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            userFileDAO.updateUser(updatedUserInput);
        }, "updateUser should throw IOException when writeValue fails.");
        
        // Verify that the userMap still contains the updated user since save failed
        assertTrue(userFileDAO.userMap.containsKey(updatedUserInput.getId()), 
            "userMap should still contain the user after IOException.");
        assertEquals(testUsers[1], userFileDAO.userMap.get(updatedUserInput.getId()), 
            "userMap should contain the original user data.");
    }
    
    /**
     * Test deleting an existing user successfully.
     */
    @Test
    public void testDeleteUserExisting() throws IOException {
        // Arrange
        int idToDelete = 1;
        User[] expectedUsersAfterDelete = new User[] {
            testUsers[0],
            testUsers[2]
        };
        
        // Mock ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), any(User[].class));
        
        // Act
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(idToDelete),
                                "Unexpected exception thrown");
        
        // Assert
        assertTrue(result, "deleteUser should return true for existing user.");
        
        // Verify that writeValue was called with the updated user array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), any(User[].class));
        
        // Verify that the userMap no longer contains the deleted user
        assertFalse(userFileDAO.userMap.containsKey(idToDelete), 
            "userMap should not contain the deleted user.");
    }
    
    /**
     * Test deleting a non-existing user (should return false).
     */
    @Test
    public void testDeleteUserNonExisting() throws IOException {
        // Arrange
        int idToDelete = 99;
        
        // Act
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(idToDelete),
                                "Unexpected exception thrown");
        
        // Assert
        assertFalse(result, "deleteUser should return false for non-existing user.");
        
        // Verify that writeValue was not called
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(User[].class));
        
        // Verify that userMap size remains unchanged
        assertEquals(testUsers.length, userFileDAO.userMap.size(), 
            "userMap size should remain unchanged.");
    }
    
    /**
     * Test deleting a user when ObjectMapper.writeValue throws an IOException.
     */
    @Test
    public void testDeleteUserIOException() throws IOException {
        // Arrange
        int idToDelete = 1;
        
        // Mock ObjectMapper.writeValue to throw IOException
        doThrow(new IOException("Failed to write to file")).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            userFileDAO.deleteUser(idToDelete);
        }, "deleteUser should throw IOException when writeValue fails.");
        
        // Verify that the userMap still contains the deleted user since save failed
        assertTrue(userFileDAO.userMap.containsKey(idToDelete), 
            "userMap should still contain the user after IOException.");
    }
    
    /**
     * Test creating a user with an ID that already exists (should assign a new unique ID).
     */
    @Test
    public void testCreateUserDuplicateId() throws IOException {
        // Arrange
        // User with id 1 already exists
        User newUserInput = new User(1, "Diana", "passwordDiana", null);
        User expectedUser = new User(3, "Diana", "passwordDiana", null);
        User[] expectedUsersAfterCreate = new User[] {
            testUsers[0],
            testUsers[1],
            testUsers[2],
            expectedUser
        };
        
        // Mock ObjectMapper.writeValue to do nothing (simulate successful save)
        doNothing().when(mockObjectMapper).writeValue(eq(new File(testFilename)), eq(expectedUsersAfterCreate));
        
        // Act
        User createdUser = userFileDAO.createUser(newUserInput);
        
        // Assert
        assertNotNull(createdUser, "createUser should return the created User.");
        assertEquals(expectedUser.getId(), createdUser.getId(), "Created User should have a new unique ID.");
        assertEquals(expectedUser.getName(), createdUser.getName(), "User name should match.");
        assertEquals(expectedUser.getPassword(), createdUser.getPassword(), "User password should match.");
        assertTrue(Arrays.equals(expectedUser.getBasket(), createdUser.getBasket()), "User basket should match.");
        
        // Verify that writeValue was called with the updated user array
        verify(mockObjectMapper, times(1)).writeValue(eq(new File(testFilename)), eq(expectedUsersAfterCreate));
        
        // Verify that the new user is in userMap
        assertTrue(userFileDAO.userMap.containsKey(expectedUser.getId()), 
            "userMap should contain the new user with unique ID.");
        assertEquals(createdUser, userFileDAO.userMap.get(expectedUser.getId()), 
            "userMap should contain the correct new user.");
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
        when(faultyObjectMapper.readValue(eq(faultyFile), eq(User[].class)))
            .thenThrow(new IOException("Failed to read file"));
        
        // Act & Assert
        assertThrows(IOException.class, () -> {
            new UserFileDAO(testFilename, faultyObjectMapper);
        }, "UserFileDAO constructor should throw IOException when readValue fails.");
    }
}
