package com.ottofriedrich.dsam.repositories;

import com.ottofriedrich.dsam.models.OnlineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OnlineOrder, Long> {
    List<OnlineOrder> findByUserId(Long userId);
}
