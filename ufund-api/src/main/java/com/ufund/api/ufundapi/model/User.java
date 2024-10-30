package com.ufund.api.ufundapi.model;

import java.util.ArrayList;

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


}
