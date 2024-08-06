package com.example.dack4.repositories;

import com.example.dack4.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByPhone(String phone);
}
