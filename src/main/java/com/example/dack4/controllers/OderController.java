package com.example.dack4.controllers;

import com.example.dack4.config.MessageResponse;
import com.example.dack4.dto.OrderDto;
import com.example.dack4.models.Customer;
import com.example.dack4.models.Orders;
import com.example.dack4.models.Table;
import com.example.dack4.models.Waiter;
import com.example.dack4.services.CustomerService;
import com.example.dack4.services.OrderService;
import com.example.dack4.services.TableService;
import com.example.dack4.services.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/orders")
public class OderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TableService tableService;
    @Autowired
    private WaiterService waiterService;

    @GetMapping
    public List<Orders> getOrders() {
        return orderService.findAll();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createOrder(@RequestBody OrderDto orderDto) {
        Customer customer = customerService.getCustomerById(orderDto.getCustomerId());
        Optional<Table> table = tableService.getTableById(orderDto.getTableId());
        if (table.isPresent()) {
            Set<Waiter> waiters = waiterService.getWaitersByOrder(orderDto.getWaiterIds());
            Date createdAt = new Date();
            Timestamp timestamp = new Timestamp(createdAt.getTime());
            try {
                Orders newOrders = new Orders();
                newOrders.setCustomer(customer);
                newOrders.setTable(table.get());
                newOrders.setWaiters(waiters);
                newOrders.setCreate_at(timestamp);
                newOrders.setPayment_method(orderDto.getPaymentMethod());
                newOrders.setBooking_time(orderDto.getBookingTime());
                newOrders.setPaid_at(orderDto.getPaidAt());
                newOrders.setTotal(orderDto.getTotal());
                newOrders.setCoupon(orderDto.getCoupon());
                newOrders.setOrigin_total(orderDto.getOriginTotal());
                newOrders.setTotal_discount(orderDto.getTotalDiscount());

                orderService.save(newOrders);
                return ResponseEntity.ok(new MessageResponse("Tạo đơn hàng thành công"));

            }catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Tạo dơn hàng lỗi"+e.getMessage()));
            }
        }

        return ResponseEntity.ok(new MessageResponse("Not found table"));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<MessageResponse> editOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        Orders order = orderService.findById(id);
        if (order != null) {
            Customer customer = customerService.getCustomerById(orderDto.getCustomerId());
            Optional<Table> table = tableService.getTableById(orderDto.getTableId());
            Set<Waiter> waiters = waiterService.getWaitersByOrder(orderDto.getWaiterIds());

            if (table.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Invalid table ID"));
            }

            order.setCustomer(customer);
            order.setTable(table.get());
            order.setWaiters(waiters);
            order.setCreate_at(new Timestamp(new Date().getTime()));
            order.setPayment_method(orderDto.getPaymentMethod());
            order.setCoupon(orderDto.getCoupon());
            order.setTotal_discount(orderDto.getTotalDiscount());
            order.setOrigin_total(orderDto.getOriginTotal());
            order.setTotal(orderDto.getTotal());

            orderService.update(order);

            return ResponseEntity.ok(new MessageResponse("Câp nhâật đơn hàng thaành công"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
