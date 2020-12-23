package com.ottofriedrich.dsam.dtos;

import java.util.List;

public class CartDTO {

    private List<BottleDTO> bottleDTOS;
    private List<CrateDTO> crateDTOS;

    public List<BottleDTO> getBottleDTOS() {
        return bottleDTOS;
    }

    public void setBottleDTOS(List<BottleDTO> bottleDTOS) {
        this.bottleDTOS = bottleDTOS;
    }

    public List<CrateDTO> getCrateDTOS() {
        return crateDTOS;
    }

    public void setCrateDTOS(List<CrateDTO> crateDTOS) {
        this.crateDTOS = crateDTOS;
    }
}
