package com.example.dack4.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private Table table;

    @ManyToMany
    @JoinTable(
            name = "order_waiter",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "waiter_id")
    )
    private Set<Waiter> waiters;
    private Date create_at;
    private Date paid_at;
    private Date booking_time;
    private String payment_method;
    private double coupon;
    private double total_discount;
    private double origin_total;
    private double total;


    public Orders() {

    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
}
