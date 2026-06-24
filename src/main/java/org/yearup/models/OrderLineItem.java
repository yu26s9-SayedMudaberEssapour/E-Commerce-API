package org.yearup.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Columns;

import java.math.BigDecimal;

@Entity
@Table(name = "OrderLineItem")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderLineItemId")
    private int orderLineItemId;


    @Column(name = "orderId")
    private int orderId;


    @Column(name = "productId")
    private int productId;


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;


    public OrderLineItem(int orderLineItemId, int orderId, int productId, int quantity, BigDecimal price) {
        this.orderLineItemId = orderLineItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderLineItemId() {
        return orderLineItemId;
    }

    public void setOrderLineItemId(int orderLineItemId) {
        this.orderLineItemId = orderLineItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
