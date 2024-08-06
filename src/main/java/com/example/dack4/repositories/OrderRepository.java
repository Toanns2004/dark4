package com.example.dack4.repositories;

import com.example.dack4.models.Customer;
import com.example.dack4.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomer(Customer customer);
}
