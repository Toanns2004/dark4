package com.example.dack4.services;

import com.example.dack4.models.Table;
import com.example.dack4.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<Table> getTableById(Long id) {
        return tableRepository.findById(id);
    }


    public Table saveTable(Table table) {
        return tableRepository.save(table);
    }

    public void deleteTableById(Long id) {
        tableRepository.deleteById(id);
    }

    public Table updateTable(Table table) {
        return tableRepository.save(table);
    }

    public Optional<Table> getTableByName(String number) {
        return tableRepository.findByNumberOfTables(number);
    }
}
