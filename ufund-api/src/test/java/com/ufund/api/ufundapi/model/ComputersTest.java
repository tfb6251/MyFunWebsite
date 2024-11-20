package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;



public class ComputersTest {

    @SuppressWarnings("unused")
    private Computer computer;

    /**
     * Before each test, create a new Computer object
     */
    @BeforeEach
    public void setupComputer() {
        computer = new Computer(10, "Laptop", 1200, 2, "Dell", "");
    }

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        int expectedId = 1;
        String expectedName = "Gaming PC";
        int expectedCost = 1500;
        int expectedQuantity = 10;
        String expectedBrand = "Alienware";

        // Act
        Computer computer = new Computer(expectedId, expectedName, expectedCost, expectedQuantity, expectedBrand, "");

        // Assert
        assertEquals(expectedId, computer.getId(), "Computer ID should match the expected value.");
        assertEquals(expectedName, computer.getName(), "Computer name should match the expected value.");
        assertEquals(expectedCost, computer.getCost(), "Computer cost should match the expected value.");
        assertEquals(expectedQuantity, computer.getQuantity(), "Computer quantity should match the expected value.");
        assertEquals(expectedBrand, computer.getBrand(), "Computer brand should match the expected value.");
        assertEquals("", computer.getDescription(), "Computer description should be updated to the new value.");
    }

    @Test
    public void testSetters() {
        // Arrange
        Computer computer = new Computer(2, "Office PC", 800, 5, "Dell", "");
        String newName = "Workstation";
        int newCost = 1200;
        int newQuantity = 7;
        String newBrand = "HP";

        // Act
        computer.setName(newName);
        computer.setCost(newCost);
        computer.setQuantity(newQuantity);
        computer.setBrand(newBrand);
        computer.setDescription("");

        // Assert
        assertEquals(newName, computer.getName(), "Computer name should be updated to the new value.");
        assertEquals(newCost, computer.getCost(), "Computer cost should be updated to the new value.");
        assertEquals(newQuantity, computer.getQuantity(), "Computer quantity should be updated to the new value.");
        assertEquals(newBrand, computer.getBrand(), "Computer brand should be updated to the new value.");
        assertEquals("", computer.getDescription(), "Computer description should be updated to the new value.");
    }

    @Test
    public void testToString() {
        // Arrange
        int id = 3;
        String name = "Server";
        int cost = 5000;
        int quantity = 2;
        String brand = "IBM";
        Computer computer = new Computer(id, name, cost, quantity, brand, "");
        String expectedString = "Id: " + id + "Name: " + name + "Cost: $" + cost + "Quantity: " + quantity + " Brand: " + brand + " Description: ";

        // Act
        String actualString = computer.toString();

        // Assert
        assertEquals(expectedString, actualString, "Computer's toString() method should return the correct string.");
    }

    @Test
    public void testJsonPropertyAnnotations() {
        // Arrange
        int id = 4;
        String name = "Laptop";
        int cost = 1000;
        int quantity = 15;
        String brand = "Apple";
        Computer computer = new Computer(id, name, cost, quantity, brand, "");

        // Act & Assert
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try {
            // Serialize Computer to JSON
            String jsonString = objectMapper.writeValueAsString(computer);
            String expectedJson = "{\"id\":4,\"name\":\"Laptop\",\"cost\":1000,\"quantity\":15,\"brand\":\"Apple\",\"description\":\"\"}";
            assertEquals(expectedJson, jsonString, "Serialized JSON should match the expected JSON string.");

            // Deserialize JSON to Computer
            Computer deserializedComputer = objectMapper.readValue(jsonString, Computer.class);
            assertEquals(id, deserializedComputer.getId(), "Deserialized Computer ID should match the original ID.");
            assertEquals(name, deserializedComputer.getName(), "Deserialized Computer name should match the original name.");
            assertEquals(cost, deserializedComputer.getCost(), "Deserialized Computer cost should match the original cost.");
            assertEquals(quantity, deserializedComputer.getQuantity(), "Deserialized Computer quantity should match the original quantity.");
            assertEquals(brand, deserializedComputer.getBrand(), "Deserialized Computer brand should match the original brand.");
        } catch (Exception e) {
            fail("Serialization/deserialization failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void testEqualsSameObject() {
        Computer computer = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        assertTrue(computer.equals(computer), "Same object should be equal.");
    }

    @Test
    public void testEqualsNull() {
        Computer computer = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        assertFalse(computer.equals(null), "Object should not be equal to null.");
    }

    @Test
    public void testEqualsDifferentClass() {
        Computer computer = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        String differentClassObject = "Not a Computer";
        assertFalse(computer.equals(differentClassObject), "Object should not be equal to a different class.");
    }

    @Test
    public void testEqualsDifferentId() {
        Computer computer1 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        Computer computer2 = new Computer(2, "Laptop", 999, 5, "BrandX", "");
        assertFalse(computer1.equals(computer2), "Computers with different IDs should not be equal.");
    }

    @Test
    public void testEqualsDifferentName() {
        Computer computer1 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        Computer computer2 = new Computer(1, "Desktop", 999, 5, "BrandX", "");
        assertFalse(computer1.equals(computer2), "Computers with different names should not be equal.");
    }

    @Test
    public void testEqualsDifferentCost() {
        Computer computer1 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        Computer computer2 = new Computer(1, "Laptop", 1099, 5, "BrandX", "");
        assertFalse(computer1.equals(computer2), "Computers with different costs should not be equal.");
    }

    @Test
    public void testEqualsDifferentQuantity() {
        Computer computer1 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        Computer computer2 = new Computer(1, "Laptop", 999, 10, "BrandX", "");
        assertFalse(computer1.equals(computer2), "Computers with different quantities should not be equal.");
    }

    @Test
    public void testEqualsDifferentBrand() {
        Computer computer1 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        Computer computer2 = new Computer(1, "Laptop", 999, 5, "BrandY", "");
        assertFalse(computer1.equals(computer2), "Computers with different brands should not be equal.");
    }

    @Test
    public void testEqualsSameValues() {
        Computer computer1 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        Computer computer2 = new Computer(1, "Laptop", 999, 5, "BrandX", "");
        assertTrue(computer1.equals(computer2), "Computers with the same values should be equal.");
    }
}
