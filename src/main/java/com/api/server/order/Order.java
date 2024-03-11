package com.api.server.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrderProducts")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    private double subTotal;
    private double discount;
    private double totalBeforeTax;
    private  double estimatedTaxToBeCollected;
    private double orderTotal;

    public Order(double subTotal, double discount, double totalBeforeTax, double estimatedTaxToBeCollected, double orderTotal) {
        this.subTotal = subTotal;
        this.discount = discount;
        this.totalBeforeTax = totalBeforeTax;
        this.estimatedTaxToBeCollected = estimatedTaxToBeCollected;
        this.orderTotal = orderTotal;
    }
}
