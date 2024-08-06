package com.example.dack4.repositories;

import com.example.dack4.models.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {
    Optional<Table> findByNumberOfTables(String number);
}
