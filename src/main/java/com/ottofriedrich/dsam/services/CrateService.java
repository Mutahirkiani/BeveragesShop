package com.ottofriedrich.dsam.services;

import com.ottofriedrich.dsam.dtos.CrateDTO;
import com.ottofriedrich.dsam.repositories.CrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrateService {

    @Autowired
    private CrateRepository crateRepository;

    public List<CrateDTO> getAllCrates() {
        List<CrateDTO> crateDTOS = new ArrayList<>();
        crateRepository.findAll().forEach(crate -> {
            crateDTOS.add(CrateDTO.valueOf(crate));
        });
        return crateDTOS;
    }
}
