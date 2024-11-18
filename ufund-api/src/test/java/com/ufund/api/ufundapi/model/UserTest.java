package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        int expectedId = 1;
        String expectedName = "Alice";
        String expectedPassword = "password123";
        Computer[] expectedBasket = new Computer[] {
            new Computer(101, "Laptop", 1000, 2, "BrandA", ""),
            new Computer(102, "Desktop", 1500, 1, "BrandB", "")
        };

        // Act
        User user = new User(expectedId, expectedName, expectedPassword, expectedBasket);

        // Assert
        assertEquals(expectedId, user.getId(), "User ID should match the expected value.");
        assertEquals(expectedName, user.getName(), "User name should match the expected value.");
        assertEquals(expectedPassword, user.getPassword(), "User password should match the expected value.");
        assertArrayEquals(expectedBasket, user.getBasket(), "User basket should match the expected value.");
    }

    // @Test
    // public void testConstructorWithNullValues() {
    //     // Arrange
    //     int expectedId = 2;
    //     String expectedName = "";
    //     String expectedPassword = "";
    //     Computer[] expectedBasket = new Computer[0];

    //     // Act
    //     User user = new User(expectedId, null, null, null);

    //     // Assert
    //     assertEquals(expectedId, user.getId(), "User ID should match the expected value.");
    //     assertEquals(expectedName, user.getName(), "User name should default to an empty string.");
    //     assertEquals(expectedPassword, user.getPassword(), "User password should default to an empty string.");
    //     assertArrayEquals(expectedBasket, user.getBasket(), "User basket should default to an empty array.");
    // }

    @Test
    public void testSetters() {
        // Arrange
        User user = new User(3, "Bob", "oldPass", null);
        String newName = "Charlie";
        String newPassword = "newPass";
        Computer[] newBasket = new Computer[] {
            new Computer(103, "Tablet", 500, 5, "BrandC", "")
        };

        // Act
        user.setName(newName);
        user.setPassword(newPassword);
        user.setBasket(newBasket);

        // Assert
        assertEquals(newName, user.getName(), "User name should be updated to the new value.");
        assertEquals(newPassword, user.getPassword(), "User password should be updated to the new value.");
        assertArrayEquals(newBasket, user.getBasket(), "User basket should be updated to the new value.");
    }

    @Test
    public void testToString() {
        // Arrange
        int id = 4;
        String name = "Diana";
        User user = new User(id, name, "pass", null);
        String expectedString = "Id: " + id + "Name: " + name;

        // Act
        String actualString = user.toString();

        // Assert
        assertEquals(expectedString, actualString, "User's toString() method should return the correct string.");
    }

    @Test
    public void testJsonPropertyAnnotations() {
        // Arrange
        int id = 5;
        String name = "Eve";
        String password = "secret";
        Computer[] basket = new Computer[] {
            new Computer(104, "Monitor", 200, 3, "BrandD", "")
        };
        User user = new User(id, name, password, basket);

        // Act & Assert
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try {
            // Serialize User to JSON
            String jsonString = objectMapper.writeValueAsString(user);
            String expectedJson = "{\"id\":5,\"name\":\"Eve\",\"password\":\"secret\",\"basket\":[{\"id\":104,\"name\":\"Monitor\",\"cost\":200,\"quantity\":3,\"brand\":\"BrandD\",\"description\":\"\"}]}";
            assertEquals(expectedJson, jsonString, "Serialized JSON should match the expected JSON string.");

            // Deserialize JSON to User
            User deserializedUser = objectMapper.readValue(jsonString, User.class);
            assertEquals(id, deserializedUser.getId(), "Deserialized User ID should match the original ID.");
            assertEquals(name, deserializedUser.getName(), "Deserialized User name should match the original name.");
            assertEquals(password, deserializedUser.getPassword(), "Deserialized User password should match the original password.");

            // Compare the baskets field-by-field
            Computer[] originalBasket = user.getBasket();
            Computer[] deserializedBasket = deserializedUser.getBasket();

            assertNotNull(deserializedBasket, "Deserialized basket should not be null");
            assertEquals(originalBasket.length, deserializedBasket.length, "Basket sizes should match");

            for (int i = 0; i < originalBasket.length; i++) {
                Computer originalComputer = originalBasket[i];
                Computer deserializedComputer = deserializedBasket[i];

                assertEquals(originalComputer.getId(), deserializedComputer.getId(), "Computer ID should match at index " + i);
                assertEquals(originalComputer.getName(), deserializedComputer.getName(), "Computer name should match at index " + i);
                assertEquals(originalComputer.getCost(), deserializedComputer.getCost(), "Computer cost should match at index " + i);
                assertEquals(originalComputer.getQuantity(), deserializedComputer.getQuantity(), "Computer quantity should match at index " + i);
                assertEquals(originalComputer.getBrand(), deserializedComputer.getBrand(), "Computer brand should match at index " + i);
            }

        } catch (Exception e) {
            fail("Serialization/deserialization failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void testEqualsSameObject() {
        User user = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        assertTrue(user.equals(user), "Same object should be equal.");
    }

    @Test
    public void testEqualsNull() {
        User user = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        assertFalse(user.equals(null), "Object should not be equal to null.");
    }

    @Test
    public void testEqualsDifferentClass() {
        User user = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        String differentClassObject = "Not a User";
        assertFalse(user.equals(differentClassObject), "Object should not be equal to a different class.");
    }

    @Test
    public void testEqualsDifferentId() {
        User user1 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        User user2 = new User(2, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        assertFalse(user1.equals(user2), "Users with different IDs should not be equal.");
    }

    @Test
    public void testEqualsDifferentName() {
        User user1 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        User user2 = new User(1, "Bob", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        assertFalse(user1.equals(user2), "Users with different names should not be equal.");
    }

    @Test
    public void testEqualsDifferentPassword() {
        User user1 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        User user2 = new User(1, "Alice", "password456", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        assertFalse(user1.equals(user2), "Users with different passwords should not be equal.");
    }

    @Test
    public void testEqualsDifferentBasketLength() {
        User user1 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        User user2 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", ""), new Computer(2, "item2", 1, 1, "brand", "")});
        assertFalse(user1.equals(user2), "Users with different basket lengths should not be equal.");
    }

    @Test
    public void testEqualsDifferentBasketItems() {
        User user1 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand", "")});
        User user2 = new User(1, "Alice", "password123", new Computer[]{new Computer(2, "item2", 1, 1, "brand", "")});
        assertFalse(user1.equals(user2), "Users with different basket items should not be equal.");
    }

    // @Test
    // public void testEqualsSameValues() {
    //     User user1 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand")});
    //     User user2 = new User(1, "Alice", "password123", new Computer[]{new Computer(1, "item1", 1, 1, "brand")});
    //     assertTrue(user1.equals(user2), "Users with the same values should be equal.");
    // }
}
