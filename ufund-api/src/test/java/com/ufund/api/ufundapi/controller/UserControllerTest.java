package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userBasketController;
    private UserService mockUserService;

    @BeforeEach
    public void setup() {
        mockUserService = mock(UserService.class);
        userBasketController = new UserController(mockUserService);
    }

    @Test
    @DisplayName("Get User by ID - Success")
    public void getUserById_success() throws IOException {
        // Arrange
        User user = new User(1, "testuser", "password", null);
        when(mockUserService.getUser(user.getId())).thenReturn(user);

        // Act
        ResponseEntity<User> response = userBasketController.getUser(user.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    @DisplayName("Get User by ID - Not Found")
    public void getUserById_notFound() throws IOException {
        // Arrange
        int userId = 1;
        when(mockUserService.getUser(userId)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userBasketController.getUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Get User by ID - Internal Server Error")
    public void getUserById_exception() throws IOException {
        // Arrange
        int userId = 1;
        when(mockUserService.getUser(userId)).thenThrow(new IOException());

        // Act
        ResponseEntity<User> response = userBasketController.getUser(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Create User - Success")
    public void createUser_success() throws IOException {
        // Arrange
        User user = new User(2, "newuser", "password", null);
        when(mockUserService.createUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userBasketController.createUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    @DisplayName("Create User - Conflict")
    public void createUser_conflict() throws IOException {
        // Arrange
        User user = new User(2, "existinguser", "password", null);
        when(mockUserService.createUser(user)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userBasketController.createUser(user);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("Create User - Internal Server Error")
    public void createUser_exception() throws IOException {
        // Arrange
        User user = new User(2, "user", "password", null);
        when(mockUserService.createUser(user)).thenThrow(new IOException());

        // Act
        ResponseEntity<User> response = userBasketController.createUser(user);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Update User - Success")
    public void updateUser_success() throws IOException {
        // Arrange
        User user = new User(1, "updateduser", "newpassword", null);
        when(mockUserService.updateUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userBasketController.updateUser(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    @DisplayName("Update User - Not Found")
    public void updateUser_notFound() throws IOException {
        // Arrange
        User user = new User(1, "user", "password", null);
        when(mockUserService.updateUser(user)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userBasketController.updateUser(user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Update User - Internal Server Error")
    public void updateUser_exception() throws IOException {
        // Arrange
        User user = new User(1, "user", "password", null);
        when(mockUserService.updateUser(user)).thenThrow(new IOException());

        // Act
        ResponseEntity<User> response = userBasketController.updateUser(user);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete User - Success")
    public void deleteUser_success() throws IOException {
        // Arrange
        int userId = 1;
        when(mockUserService.deleteUser(userId)).thenReturn(true);

        // Act
        ResponseEntity<User> response = userBasketController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete User - Not Found")
    public void deleteUser_notFound() throws IOException {
        // Arrange
        int userId = 1;
        when(mockUserService.deleteUser(userId)).thenReturn(false);

        // Act
        ResponseEntity<User> response = userBasketController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete User - Internal Server Error")
    public void deleteUser_exception() throws IOException {
        // Arrange
        int userId = 1;
        when(mockUserService.deleteUser(userId)).thenThrow(new IOException());

        // Act
        ResponseEntity<User> response = userBasketController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Find Users - Success")
    public void findUsers_success() throws IOException {
        // Arrange
        User[] users = new User[] {
            new User(1, "user1", "password1", null),
            new User(2, "user2", "password2", null)
        };
        when(mockUserService.getUsers()).thenReturn(users);

        // Act
        ResponseEntity<User[]> response = userBasketController.findUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(users, response.getBody());
    }

    @Test
    @DisplayName("Find Users - Internal Server Error")
    public void findUsers_exception() throws IOException {
        // Arrange
        when(mockUserService.getUsers()).thenThrow(new IOException());

        // Act
        ResponseEntity<User[]> response = userBasketController.findUsers();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Search Users - Success")
    public void searchUsers_success() throws IOException {
        // Arrange
        String searchString = "test";
        User[] users = new User[] {
            new User(1, "testuser1", "password1", null),
            new User(2, "testuser2", "password2", null)
        };
        when(mockUserService.findUsers(searchString)).thenReturn(users);

        // Act
        ResponseEntity<User[]> response = userBasketController.searchUsers(searchString);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(users, response.getBody());
    }

    @Test
    @DisplayName("Search Users - Not Found")
    public void searchUsers_notFound() throws IOException {
        // Arrange
        String searchString = "nonexistent";
        when(mockUserService.findUsers(searchString)).thenReturn(null);

        // Act
        ResponseEntity<User[]> response = userBasketController.searchUsers(searchString);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Search Users - Internal Server Error")
    public void searchUsers_exception() throws IOException {
        // Arrange
        String searchString = "test";
        when(mockUserService.findUsers(searchString)).thenThrow(new IOException());

        // Act
        ResponseEntity<User[]> response = userBasketController.searchUsers(searchString);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
