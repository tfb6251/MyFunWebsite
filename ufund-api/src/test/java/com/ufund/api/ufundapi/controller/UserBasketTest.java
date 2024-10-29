package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserBasketTest {
    private UserBasket userBasketController;
    private UserService mockUserService;

    @BeforeEach
    public void setupUserBasket() {
        mockUserService = mock(UserService.class);
        userBasketController = new UserBasket(mockUserService);
    }

    // Test for getting a user by ID
    @Test
    public void testGetUserById() throws IOException {
        User user = new User(1, "testuser", "password");
        when(mockUserService.getUser(user.getId())).thenReturn(user);

        ResponseEntity<User> response = userBasketController.getUser(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    // Test for user not found by ID
    @Test
    public void testGetUserByIdNotFound() throws IOException {
        int userId = 1;
        when(mockUserService.getUser(userId)).thenReturn(null);

        ResponseEntity<User> response = userBasketController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when getting user by ID
    @Test
    public void testGetUserByIdHandleException() throws IOException {
        int userId = 1;
        doThrow(new IOException()).when(mockUserService).getUser(userId);

        ResponseEntity<User> response = userBasketController.getUser(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for getting a user by name
    @Test
    public void testGetUserByName() throws IOException {
        User user = new User(1, "testuser", "password");
        when(mockUserService.getUserN(user.getName())).thenReturn(user);

        ResponseEntity<User> response = userBasketController.getUserN(user.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    // Test for user not found by name
    @Test
    public void testGetUserByNameNotFound() throws IOException {
        String userName = "testuser";
        when(mockUserService.getUserN(userName)).thenReturn(null);

        ResponseEntity<User> response = userBasketController.getUserN(userName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when getting user by name
    @Test
    public void testGetUserByNameHandleException() throws IOException {
        String userName = "testuser";
        doThrow(new IOException()).when(mockUserService).getUserN(userName);

        ResponseEntity<User> response = userBasketController.getUserN(userName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for creating a new user
    @Test
    public void testCreateUser() throws IOException {
        User user = new User(2, "newuser", "password");
        when(mockUserService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userBasketController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    // Test for conflict when creating a user that already exists
    @Test
    public void testCreateUserConflict() throws IOException {
        User user = new User(2, "existinguser", "password");
        when(mockUserService.createUser(user)).thenReturn(null);

        ResponseEntity<User> response = userBasketController.createUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    // Test for exception when creating a user
    @Test
    public void testCreateUserHandleException() throws IOException {
        User user = new User(2, "user", "password");
        doThrow(new IOException()).when(mockUserService).createUser(user);

        ResponseEntity<User> response = userBasketController.createUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for updating a user
    @Test
    public void testUpdateUser() throws IOException {
        User user = new User(1, "updateduser", "newpassword");
        when(mockUserService.updateUser(user)).thenReturn(user);

        ResponseEntity<User> response = userBasketController.updateUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    // Test for user not found when updating
    @Test
    public void testUpdateUserNotFound() throws IOException {
        User user = new User(1, "user", "password");
        when(mockUserService.updateUser(user)).thenReturn(null);

        ResponseEntity<User> response = userBasketController.updateUser(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when updating a user
    @Test
    public void testUpdateUserHandleException() throws IOException {
        User user = new User(1, "user", "password");
        doThrow(new IOException()).when(mockUserService).updateUser(user);

        ResponseEntity<User> response = userBasketController.updateUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for deleting a user
    @Test
    public void testDeleteUser() throws IOException {
        int userId = 1;
        when(mockUserService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<User> response = userBasketController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test for user not found when deleting
    @Test
    public void testDeleteUserNotFound() throws IOException {
        int userId = 1;
        when(mockUserService.deleteUser(userId)).thenReturn(false);

        ResponseEntity<User> response = userBasketController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when deleting a user
    @Test
    public void testDeleteUserHandleException() throws IOException {
        int userId = 1;
        doThrow(new IOException()).when(mockUserService).deleteUser(userId);

        ResponseEntity<User> response = userBasketController.deleteUser(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for getting all users
    @Test
    public void testFindUsers() throws IOException {
        User[] users = new User[2];
        users[0] = new User(1, "user1", "password1");
        users[1] = new User(2, "user2", "password2");

        when(mockUserService.getUsers()).thenReturn(users);

        ResponseEntity<User[]> response = userBasketController.findUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    // Test for exception when getting all users
    @Test
    public void testFindUsersHandleException() throws IOException {
        doThrow(new IOException()).when(mockUserService).getUsers();

        ResponseEntity<User[]> response = userBasketController.findUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test for searching users by name
    @Test
    public void testSearchUsers() throws IOException {
        String searchString = "test";
        User[] users = new User[2];
        users[0] = new User(1, "testuser1", "password1");
        users[1] = new User(2, "testuser2", "password2");

        when(mockUserService.findUsers(searchString)).thenReturn(users);

        ResponseEntity<User[]> response = userBasketController.searchNeeds(searchString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    // Test for users not found when searching
    @Test
    public void testSearchUsersNotFound() throws IOException {
        String searchString = "nonexistent";
        when(mockUserService.findUsers(searchString)).thenReturn(null);

        ResponseEntity<User[]> response = userBasketController.searchNeeds(searchString);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for exception when searching users
    @Test
    public void testSearchUsersHandleException() throws IOException {
        String searchString = "test";
        doThrow(new IOException()).when(mockUserService).findUsers(searchString);

        ResponseEntity<User[]> response = userBasketController.searchNeeds(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
