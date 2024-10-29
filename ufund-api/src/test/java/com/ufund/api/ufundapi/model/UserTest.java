package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        int expectedId = 1;
        String expectedName = "Alice";

        // Act
        User user = new User(expectedId, expectedName);

        // Assert
        assertEquals(expectedId, user.getId(), "User ID should match the expected value.");
        assertEquals(expectedName, user.getName(), "User name should match the expected value.");
    }

    @Test
    public void testSetName() {
        // Arrange
        User user = new User(2, "Bob");
        String newName = "Charlie";

        // Act
        user.setName(newName);

        // Assert
        assertEquals(newName, user.getName(), "User name should be updated to the new value.");
    }

    @Test
    public void testToString() {
        // Arrange
        int id = 3;
        String name = "Diana";
        User user = new User(id, name);
        String expectedString = "Id: " + id + "Name: " + name;

        // Act
        String actualString = user.toString();

        // Assert
        assertEquals(expectedString, actualString, "User's toString() method should return the correct string.");
    }

    @Test
    public void testJsonPropertyAnnotations() {
        // Arrange
        int id = 4;
        String name = "Eve";
        User user = new User(id, name);

        // Act & Assert
        // Use Jackson's ObjectMapper to test serialization and deserialization
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try {
            // Serialize User to JSON
            String jsonString = objectMapper.writeValueAsString(user);
            String expectedJson = "{\"id\":4,\"name\":\"Eve\"}";
            assertEquals(expectedJson, jsonString, "Serialized JSON should match the expected JSON string.");

            // Deserialize JSON to User
            User deserializedUser = objectMapper.readValue(jsonString, User.class);
            assertEquals(id, deserializedUser.getId(), "Deserialized User ID should match the original ID.");
            assertEquals(name, deserializedUser.getName(), "Deserialized User name should match the original name.");
        } catch (Exception e) {
            fail("Serialization/deserialization failed with exception: " + e.getMessage());
        }
    }
}
