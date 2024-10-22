package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NeedsTest {

    @Mock
    private Needs mockNeeds;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockNeeds = mock(Needs.class);
    }

    @Test
    
    public void testConstructorAndGetters() {
        when(mockNeeds.getName()).thenReturn("Laptop");
        when(mockNeeds.getCost()).thenReturn(1200);
        when(mockNeeds.getQuantity()).thenReturn(2);
        when(mockNeeds.getBrand()).thenReturn("Dell");

        assertAll(
            () -> assertEquals("Laptop", mockNeeds.getName(), "Name should match the expected value"),
            () -> assertEquals(1200, mockNeeds.getCost(), "Cost should match the expected value"),
            () -> assertEquals(2, mockNeeds.getQuantity(), "Quantity should match the expected value"),
            () -> assertEquals("Dell", mockNeeds.getBrand(), "Brand should match the expected value")
        );
    }

    @Test
    
    public void testSetters() {
        mockNeeds.setName("Tablet");
        mockNeeds.setCost(600);
        mockNeeds.setQuantity(3);
        mockNeeds.setBrand("Samsung");

        verify(mockNeeds).setName("Tablet");
        verify(mockNeeds).setCost(600);
        verify(mockNeeds).setQuantity(3);
        verify(mockNeeds).setBrand("Samsung");
    }

    @Test
    
    public void testToString() {
        when(mockNeeds.toString()).thenReturn("Name: HeadphonesCost: $100Quantity: 1 Brand: Sony");
        String expectedString = "Name: HeadphonesCost: $100Quantity: 1 Brand: Sony";
        assertEquals(expectedString, mockNeeds.toString(), "toString() should return the expected string");
    }

    @Test
    
    public void testEquality() {
        Needs needs1 = new Needs("Monitor", 300, 2, "LG");
        Needs needs2 = new Needs("Monitor", 300, 2, "LG");
        assertAll(
            () -> assertEquals(needs1.getName(), needs2.getName(), "Names should be equal"),
            () -> assertEquals(needs1.getCost(), needs2.getCost(), "Costs should be equal"),
            () -> assertEquals(needs1.getQuantity(), needs2.getQuantity(), "Quantities should be equal"),
            () -> assertEquals(needs1.getBrand(), needs2.getBrand(), "Brands should be equal")
        );
    }

    @Test
    
    public void testDefaultValues() {
        when(mockNeeds.getName()).thenReturn("Item");
        when(mockNeeds.getCost()).thenReturn(0);
        when(mockNeeds.getQuantity()).thenReturn(0);
        when(mockNeeds.getBrand()).thenReturn("");

        assertAll(
            () -> assertNotNull(mockNeeds.getName(), "Name should not be null"),
            () -> assertEquals(0, mockNeeds.getCost(), "Default cost should be 0"),
            () -> assertEquals(0, mockNeeds.getQuantity(), "Default quantity should be 0"),
            () -> assertNotNull(mockNeeds.getBrand(), "Brand should not be null")
        );
    }
}
