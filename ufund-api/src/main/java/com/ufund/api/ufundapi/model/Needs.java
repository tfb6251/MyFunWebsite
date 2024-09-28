package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Needs {
    @JsonProperty("name")
    private String name;

    @JsonProperty("cost")
    private int cost;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("brand")
    private String brand;

    public Needs(@JsonProperty("name") String name, @JsonProperty("cost") int cost, 
    @JsonProperty("quantity") int quantity,   @JsonProperty("brand") String brand)
    {
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return ("Name: " + name + "Cost: $" + cost + "Quantity: " + quantity + " Brand: " + brand);
    }


}
