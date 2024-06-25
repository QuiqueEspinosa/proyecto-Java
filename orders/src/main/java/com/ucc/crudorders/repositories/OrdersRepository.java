package com.ucc.crudorders.repositories;

import com.ucc.crudorders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
