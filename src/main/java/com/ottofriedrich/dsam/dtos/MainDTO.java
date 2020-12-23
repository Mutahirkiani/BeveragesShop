package com.ottofriedrich.dsam.dtos;

import java.util.List;

public class MainDTO {

    List<BottleDTO> bottleDTOS;
    List<CrateDTO> crateDTOS;
    List<OrderDTO> orderDTOS;

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

    public List<OrderDTO> getOrderDTOS() {
        return orderDTOS;
    }

    public void setOrderDTOS(List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }
}
