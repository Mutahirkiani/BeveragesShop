package com.ottofriedrich.dsam.services;

import com.ottofriedrich.dsam.dtos.BottleDTO;
import com.ottofriedrich.dsam.models.Bottle;
import com.ottofriedrich.dsam.repositories.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BottleService {

    @Autowired
    private BottleRepository bottleRepository;

    public List<BottleDTO> getAllBottles() {
        List<BottleDTO> bottleDTOS = new ArrayList<>();
        bottleRepository.findAll().forEach(bottle -> {
            bottleDTOS.add(BottleDTO.valueOf(bottle));
        });
        return bottleDTOS;
    }
}
