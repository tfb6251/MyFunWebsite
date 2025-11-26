package com.ufund.api.ufundapi.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telem {
    @JsonProperty("Temp")
    private float Temp;
    @JsonProperty("Pres")
    private float Pres;
    @JsonProperty("Humi")
    private float Humi;
    @JsonProperty("Alti")
    private float Alti;
    @JsonProperty("Dcm")
    private float Dcm;
    @JsonProperty("Din")
    private float Din;
    @JsonProperty("Dt")
    private float Dt;

    public Telem(@JsonProperty("Temp") float Temp, @JsonProperty("Pres") float Pres,
    @JsonProperty("Humi") float Humi, @JsonProperty("Alti") float Alti,
    @JsonProperty("Dcm") float Dcm, @JsonProperty("Din") float Din,
     @JsonProperty("Dt") float Dt) {
        this.Temp = Temp;
        this.Pres = Pres;
        this.Humi = Humi;
        this.Alti = Alti;
        this.Dcm = Dcm;
        this.Din = Din;
        this.Dt = Dt;
    }


    public int getId() {
        return this.id;
    }

    public float getName() {
        return this.name;
    }

    public float getPassword() {
        return this.password;
    }

    public float[] getPer) {
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
    
        return this.id == user.id &&                    // Compare id
               Objects.equals(this.name, user.name) &&   // Compare name
               Objects.equals(this.password, user.password); // Compare password
    }
    
}
