package com.ottofriedrich.dsam.repositories;

import com.ottofriedrich.dsam.models.Crate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrateRepository extends JpaRepository<Crate, Long> {
}
