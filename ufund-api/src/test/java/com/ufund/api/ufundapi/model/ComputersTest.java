package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
public class ComputersTest {

    private Computer computer;

    @BeforeEach
    public void setup() {
        computer = new Computer("Laptop", 1200, 2, "Dell");
    }

    @Test
    public void testConstructorAndGetters() {
        assertAll(
            () -> assertEquals("Laptop", computer.getName(), "Name should match the expected value"),
            () -> assertEquals(1200, computer.getCost(), "Cost should match the expected value"),
            () -> assertEquals(2, computer.getQuantity(), "Quantity should match the expected value"),
            () -> assertEquals("Dell", computer.getBrand(), "Brand should match the expected value")
        );
    }

    @Test
    public void testSetters() {
        computer.setName("Tablet");
        computer.setCost(600);
        computer.setQuantity(3);
        computer.setBrand("Samsung");

        assertAll(
            () -> assertEquals("Tablet", computer.getName(), "Name should match the updated value"),
            () -> assertEquals(600, computer.getCost(), "Cost should match the updated value"),
            () -> assertEquals(3, computer.getQuantity(), "Quantity should match the updated value"),
            () -> assertEquals("Samsung", computer.getBrand(), "Brand should match the updated value")
        );
    }

    @Test
    public void testToString() {
        String expectedString = "Name: LaptopCost: $1200Quantity: 2 Brand: Dell";
        assertEquals(expectedString, computer.toString(), "toString() should return the expected string");
    }

    @Test
    public void testEquality() {
        Computer computer2 = new Computer("Laptop", 1200, 2, "Dell");
        assertAll(
            () -> assertEquals(computer.getName(), computer2.getName(), "Names should be equal"),
            () -> assertEquals(computer.getCost(), computer2.getCost(), "Costs should be equal"),
            () -> assertEquals(computer.getQuantity(), computer2.getQuantity(), "Quantities should be equal"),
            () -> assertEquals(computer.getBrand(), computer2.getBrand(), "Brands should be equal")
        );
    }

    @Test
    public void testDefaultValues() {
        Computer defaultComputer = new Computer("Item", 0, 0, "");
        assertAll(
            () -> assertEquals("Item", defaultComputer.getName(), "Name should match the default value"),
            () -> assertEquals(0, defaultComputer.getCost(), "Cost should match the default value"),
            () -> assertEquals(0, defaultComputer.getQuantity(), "Quantity should match the default value"),
            () -> assertEquals("", defaultComputer.getBrand(), "Brand should match the default value")
        );
    }
}
