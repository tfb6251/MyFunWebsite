package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ComputerTest {

    private Computer computer;

    /**
     * Before each test, create a new Computer object
     */
    @BeforeEach
    public void setupComputer() {
        computer = new Computer("Laptop", 1200, 2, "Dell");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Laptop", computer.getName());
        assertEquals(1200, computer.getCost());
        assertEquals(2, computer.getQuantity());
        assertEquals("Dell", computer.getBrand());
    }

    @Test
    public void testSetName() {
        computer.setName("Tablet");
        assertEquals("Tablet", computer.getName());
    }

    @Test
    public void testSetCost() {
        computer.setCost(600);
        assertEquals(600, computer.getCost());
    }

    @Test
    public void testSetQuantity() {
        computer.setQuantity(3);
        assertEquals(3, computer.getQuantity());
    }

    @Test
    public void testSetBrand() {
        computer.setBrand("Samsung");
        assertEquals("Samsung", computer.getBrand());
    }

    @Test
    public void testToString() {
        String expectedString = "Name: LaptopCost: $1200Quantity: 2 Brand: Dell";
        assertEquals(expectedString, computer.toString());
    }

    @Test
    public void testEquality() {
        Computer anotherComputer = new Computer("Laptop", 1200, 2, "Dell");
        assertEquals(computer.getName(), anotherComputer.getName());
        assertEquals(computer.getCost(), anotherComputer.getCost());
        assertEquals(computer.getQuantity(), anotherComputer.getQuantity());
        assertEquals(computer.getBrand(), anotherComputer.getBrand());
    }

    @Test
    public void testDefaultValues() {
        Computer defaultComputer = new Computer("Item", 0, 0, "");
        assertEquals("Item", defaultComputer.getName());
        assertEquals(0, defaultComputer.getCost());
        assertEquals(0, defaultComputer.getQuantity());
        assertEquals("", defaultComputer.getBrand());
    }
}
