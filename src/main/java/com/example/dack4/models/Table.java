package com.example.dack4.models;

import com.example.dack4.enums.TableStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@jakarta.persistence.Table(name = "restaurant_tables")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numberOfTables;

    private String qrCode;
    private int chairs;

    @Enumerated(EnumType.STRING)
    private TableStatus status = TableStatus.Empty;

    public Table() {

    }
}
