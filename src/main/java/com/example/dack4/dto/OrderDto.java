package com.example.dack4.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class OrderDto {
    private Long id;
    private Long customerId;
    private Long tableId;
    private Set<Long> waiterIds;
    private Date createAt;
    private Date paidAt;
    private Date bookingTime;
    private String paymentMethod;
    private double coupon;
    private double totalDiscount;
    private double originTotal;
    private double total;

    // Constructors, Getters và Setters
    public OrderDto() {}

    // Getters và Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getTableId() { return tableId; }
    public void setTableId(Long tableId) { this.tableId = tableId; }

    public Set<Long> getWaiterIds() {
        return waiterIds;
    }

    public void setWaiterIds(Set<Long> waiterIds) {
        this.waiterIds = waiterIds;
    }

    public Date getCreateAt() { return createAt; }
    public void setCreateAt(Date createAt) { this.createAt = createAt; }

    public Date getPaidAt() { return paidAt; }
    public void setPaidAt(Date paidAt) { this.paidAt = paidAt; }

    public Date getBookingTime() { return bookingTime; }
    public void setBookingTime(Date bookingTime) { this.bookingTime = bookingTime; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public double getCoupon() { return coupon; }
    public void setCoupon(double coupon) { this.coupon = coupon; }

    public double getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(double totalDiscount) { this.totalDiscount = totalDiscount; }

    public double getOriginTotal() { return originTotal; }
    public void setOriginTotal(double originTotal) { this.originTotal = originTotal; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}

