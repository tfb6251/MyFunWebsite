package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("id")
    final private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

    @JsonProperty("basket")
    private Computer[] basket;

    public User(@JsonProperty("id") int id, @JsonProperty("name") String name,
     @JsonProperty("password") String password,  @JsonProperty("basket") Computer[] basket)
    {
        this.id = id;
        this.name = (name != null) ? name : "";
        this.password = (password != null) ? password : "";
        this.basket = (basket != null) ? basket : new Computer[0];
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public Computer[] getBasket() {
        return this.basket;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setBasket(Computer[] basket) {
        this.basket = basket;
    }


    @Override
    public String toString() {
        return ("Id: " + id + "Name: " + name);
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the same object reference
        if (this == obj) return true; // Check if same reference
        if (obj == null) return false; // Check if obj is null
        // Check if not the same class
        if (getClass() != obj.getClass()) return false;
    
        // Cast obj to User type and compare relevant fields
        User user = (User) obj;
    
        // Check basket length
        if (this.basket.length != user.basket.length) return false;
    
        // Check if all items in the basket are equal
        for (int i = 0; i < this.basket.length; i++) {
            if (!this.basket[i].equals(user.basket[i])) {
                return false;
            }
        }
    
        return id == user.id &&                    // Compare id
               Objects.equals(this.name, user.name) &&   // Compare name
               Objects.equals(this.password, user.password); // Compare password
    }
    
}
