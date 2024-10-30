package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDAO mockUserDAO;

    @BeforeEach
    public void setup() {
        mockUserDAO = mock(UserDAO.class);
        userService = new UserService(mockUserDAO);
    }

    // Test getUsers()
    @Test
    public void testGetUsers() throws IOException {
        User[] users = new User[]{
                new User(1, "user1"),
                new User(2, "user2")
        };
        when(mockUserDAO.getUsers()).thenReturn(users);

        User[] result = userService.getUsers();

        assertArrayEquals(users, result);
    }

    @Test
    public void testGetUsersEmpty() throws IOException {
        User[] users = new User[]{};
        when(mockUserDAO.getUsers()).thenReturn(users);

        User[] result = userService.getUsers();

        assertArrayEquals(users, result);
    }

    @Test
    public void testGetUsersThrowsException() throws IOException {
        when(mockUserDAO.getUsers()).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            userService.getUsers();
        });
    }

    // Test getUser(int id)
    @Test
    public void testGetUser() throws IOException {
        User user = new User(1, "user1");
        when(mockUserDAO.getUser(1)).thenReturn(user);

        User result = userService.getUser(1);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserNotFound() throws IOException {
        when(mockUserDAO.getUser(1)).thenReturn(null);

        User result = userService.getUser(1);

        assertNull(result);
    }

    @Test
    public void testGetUserThrowsException() throws IOException {
        when(mockUserDAO.getUser(1)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            userService.getUser(1);
        });
    }

    // // Test getUserN(String name)
    // @Test
    // public void testGetUserN() throws IOException {
    //     User user = new User(1, "user1");
    //     when(mockUserDAO.getUserN("user1")).thenReturn(user);

    //     User result = userService.getUserN("user1");

    //     assertEquals(user, result);
    // }

    // @Test
    // public void testGetUserNNotFound() throws IOException {
    //     when(mockUserDAO.getUserN("user1")).thenReturn(null);

    //     User result = userService.getUserN("user1");

    //     assertNull(result);
    // }

    // @Test
    // public void testGetUserNThrowsException() throws IOException {
    //     when(mockUserDAO.getUserN("user1")).thenThrow(new IOException("Database error"));

    //     assertThrows(IOException.class, () -> {
    //         userService.getUserN("user1");
    //     });
    // }

    // Test findUsers(String name)
    @Test
    public void testFindUsers() throws IOException {
        String searchString = "test";
        User[] users = new User[]{
                new User(1, "testuser1"),
                new User(2, "testuser2")
        };
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        User[] result = userService.findUsers(searchString);

        assertArrayEquals(users, result);
    }

    @Test
    public void testFindUsersNotFound() throws IOException {
        String searchString = "nonexistent";
        User[] users = new User[]{};
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        User[] result = userService.findUsers(searchString);

        assertArrayEquals(users, result);
    }

    @Test
    public void testFindUsersThrowsException() throws IOException {
        String searchString = "test";
        when(mockUserDAO.findUsers(searchString)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            userService.findUsers(searchString);
        });
    }

    // Test createUser(User user)
    @Test
    public void testCreateUser() throws IOException {
        User user = new User(3, "newuser");
        when(mockUserDAO.createUser(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
    }

    @Test
    public void testCreateUserAlreadyExists() throws IOException {
        User user = new User(3, "existinguser");
        when(mockUserDAO.createUser(user)).thenReturn(null);

        User result = userService.createUser(user);

        assertNull(result);
    }

    @Test
    public void testCreateUserThrowsException() throws IOException {
        User user = new User(3, "user");
        when(mockUserDAO.createUser(user)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            userService.createUser(user);
        });
    }

    // Test updateUser(User user)
    @Test
    public void testUpdateUser() throws IOException {
        User user = new User(1, "updateduser");
        when(mockUserDAO.updateUser(user)).thenReturn(user);

        User result = userService.updateUser(user);

        assertEquals(user, result);
    }

    @Test
    public void testUpdateUserNotFound() throws IOException {
        User user = new User(1, "user");
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        User result = userService.updateUser(user);

        assertNull(result);
    }

    @Test
    public void testUpdateUserThrowsException() throws IOException {
        User user = new User(1, "user");
        when(mockUserDAO.updateUser(user)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            userService.updateUser(user);
        });
    }

    // Test deleteUser(int id)
    @Test
    public void testDeleteUser() throws IOException {
        int userId = 1;
        when(mockUserDAO.deleteUser(userId)).thenReturn(true);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
    }

    @Test
    public void testDeleteUserNotFound() throws IOException {
        int userId = 1;
        when(mockUserDAO.deleteUser(userId)).thenReturn(false);

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
    }

    @Test
    public void testDeleteUserThrowsException() throws IOException {
        int userId = 1;
        when(mockUserDAO.deleteUser(userId)).thenThrow(new IOException("Database error"));

        assertThrows(IOException.class, () -> {
            userService.deleteUser(userId);
        });
    }
}
