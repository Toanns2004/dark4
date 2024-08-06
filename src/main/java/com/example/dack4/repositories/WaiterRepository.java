package com.example.dack4.repositories;

import com.example.dack4.models.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {
    List<Waiter> findByIdIn(Set<Long> ids);
}
