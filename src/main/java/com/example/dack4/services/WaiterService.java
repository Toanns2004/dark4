package com.example.dack4.services;

import com.example.dack4.models.Waiter;
import com.example.dack4.repositories.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WaiterService {

    @Autowired
    private WaiterRepository waiterRepository;

    public List<Waiter> getAllWaiters() {
        return waiterRepository.findAll();
    }

    public Waiter getWaiterById(Long id) {
        return waiterRepository.findById(id).orElse(null);
    }

    public Waiter saveWaiter(Waiter waiter) {
        return waiterRepository.save(waiter);
    }

    public void deleteWaiter(Long id) {
        waiterRepository.deleteById(id);
    }

    public Waiter updateWaiter(Waiter waiter) {
        return waiterRepository.save(waiter);
    }
    public Set<Waiter> getWaitersByOrder(Set<Long> ids) {
        return new HashSet<>(waiterRepository.findByIdIn(ids));
    }
}
