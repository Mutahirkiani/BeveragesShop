package com.ottofriedrich.dsam.repositories;

import com.ottofriedrich.dsam.models.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleRepository extends JpaRepository<Bottle, Long> {
}
