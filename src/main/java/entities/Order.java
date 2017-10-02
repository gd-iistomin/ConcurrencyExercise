package entities;

import java.time.LocalDate;

public class Order {
    private long id;
    private int cost;
    private String deliveryAddress;
    private String outpostAddress;
    private LocalDate orderDate;
    private long customerId;
    private boolean orderPaid;
    private boolean orderSent;
    private boolean orderDelivered;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOutpostAddress() {
        return outpostAddress;
    }

    public void setOutpostAddress(String outpostAddress) {
        this.outpostAddress = outpostAddress;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public boolean isOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(boolean orderPaid) {
        this.orderPaid = orderPaid;
    }

    public boolean isOrderSent() {
        return orderSent;
    }

    public void setOrderSent(boolean orderSent) {
        this.orderSent = orderSent;
    }

    public boolean isOrderDelivered() {
        return orderDelivered;
    }

    public void setOrderDelivered(boolean orderDelivered) {
        this.orderDelivered = orderDelivered;
    }
}
