package com.ottofriedrich.dsam.dtos;

import com.ottofriedrich.dsam.models.Crate;
import org.springframework.beans.BeanUtils;

public class CrateDTO {
    private Long id;
    private String name;
    private String cratePic;
    private int noOfBottles;
    private double price;
    private int cratesInStock;
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static CrateDTO valueOf(Crate crate) {
        CrateDTO crateDTO = new CrateDTO();
        BeanUtils.copyProperties(crate, crateDTO);
        return crateDTO;
    }
}
