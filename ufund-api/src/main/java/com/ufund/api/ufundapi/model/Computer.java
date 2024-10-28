package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Computer {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cost")
    private int cost;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("brand")
    private String brand;

    public Computer(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("cost") int cost, 
    @JsonProperty("quantity") int quantity,   @JsonProperty("brand") String brand)
    {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.brand = brand;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getBrand() {
        return this.brand;
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
        return ("Id: " + id + "Name: " + name + "Cost: $" + cost + "Quantity: " + quantity + " Brand: " + brand);
    }


}
