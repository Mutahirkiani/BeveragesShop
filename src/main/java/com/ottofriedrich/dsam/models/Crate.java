package com.ottofriedrich.dsam.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Crate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cratePic;
    private int noOfBottles;
    private double price;
    private int cratesInStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCratePic() {
        return cratePic;
    }

    public void setCratePic(String cratePic) {
        this.cratePic = cratePic;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCratesInStock() {
        return cratesInStock;
    }

    public void setCratesInStock(int cratesInStock) {
        this.cratesInStock = cratesInStock;
    }
}
