package com.ottofriedrich.dsam.repositories;

import com.ottofriedrich.dsam.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
