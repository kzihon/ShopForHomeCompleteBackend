package com.api.server.customer;

import com.api.server.ShoppingCart.ShoppingCart;
import com.api.server.order.Order;
import com.api.server.product.Product;
import com.api.server.user.User;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
//
//public class Customer{//} extends User {
//
//
//
//}

// -----------------------------------------------------------------

// ?? DATABASE CART
// @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
// private Cart cart;

// ?? ORDER HISTORY
// @OneToMany
// @JoinTable(name = "orders")
// private List<Order> orderList;