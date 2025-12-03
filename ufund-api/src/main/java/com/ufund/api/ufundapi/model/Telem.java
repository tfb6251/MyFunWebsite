package com.ufund.api.ufundapi.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telem {
    @JsonProperty("Id")
    private int Id;
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

    public Telem(@JsonProperty("Id") int Id, @JsonProperty("Temp") float Temp,
    @JsonProperty("Pres") float Pres, @JsonProperty("Humi") float Humi,
    @JsonProperty("Alti") float Alti, @JsonProperty("Dcm") float Dcm,
    @JsonProperty("Din") float Din, @JsonProperty("Dt") float Dt) 
    {
        this.Id = Id;
        this.Temp = Temp;
        this.Pres = Pres;
        this.Humi = Humi;
        this.Alti = Alti;
        this.Dcm = Dcm;
        this.Din = Din;
        this.Dt = Dt;
    }

    public int getId() {
        return this.Id;
    }
    public float getTemp() {
        return this.Temp;
    }
    public float getPres() {
        return this.Pres;
    }
    public float getHumi() {
        return this.Humi;
    }
    public float getAlti() {
        return this.Alti;
    }
    public float getDcm() {
        return this.Dcm;
    }
    public float getDin() {
        return this.Din;
    }
    public float getDt() {
        return this.Dt;
    }


    @Override
    public String toString() {
        return ("Id: " + Id + ", Temp: " + Temp + ", Pres: " + Pres + 
        ", Humi: " + Humi + ", Alti: " + Alti + ", Dcm: " + Dcm + ", Din: " + Din
        + ", Dt: " + Dt);
    }

    // @Override
    // public boolean equals(Object obj) {
    //     // Check if the same object reference
    //     if (this == obj) return true; // Check if same reference
    //     if (obj == null) return false; // Check if obj is null
    //     // Check if not the same class
    //     if (getClass() != obj.getClass()) return false;
    
    //     // Cast obj to User type and compare relevant fields
    //     User user = (User) obj;
    
    //     // Check basket length
    //     if (this.basket.length != user.basket.length) return false;
    
    //     // Check if all items in the basket are equal
    //     for (int i = 0; i < this.basket.length; i++) {
    //         if (!this.basket[i].equals(user.basket[i])) {
    //             return false;
    //         }
    //     }
    
    //     return this.id == user.id &&                    // Compare id
    //            Objects.equals(this.name, user.name) &&   // Compare name
    //            Objects.equals(this.password, user.password); // Compare password
    // }
    
}
