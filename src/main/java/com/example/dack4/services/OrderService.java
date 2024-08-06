package com.example.dack4.services;

import com.example.dack4.models.Customer;
import com.example.dack4.models.Orders;
import com.example.dack4.repositories.CustomerRepository;
import com.example.dack4.repositories.OrderRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public Orders findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Orders save(Orders order) {
        return orderRepository.save(order);
    }

    public void delete(Orders order) {
        orderRepository.delete(order);
    }

    public Orders update(Orders order) {
        return orderRepository.save(order);
    }

    public List<Orders> findByCustomerId(String phoneNumber) {
        Customer customer = customerRepository.findByPhone(phoneNumber);
        if (customer!= null) {
            return orderRepository.findByCustomer(customer);
        }
        return null;
    }
}
