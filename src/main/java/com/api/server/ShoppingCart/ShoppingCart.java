package com.api.server.ShoppingCart;

import com.api.server.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    @Id
    @GeneratedValue
    private long CartId;
    @Transient
    private List<CartItem> cartItems = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore  // Ignore the customer property during JSON serialization
    private User customer;
    public ShoppingCart() {
    }

    public void addCartItem(CartItem cartItem){
        cartItems.add(cartItem);
    }
    public void removeCartItem(CartItem cartItem){
        cartItems.remove(cartItem);
    }
    public long getCartId() {
        return CartId;
    }

    public void setCartId(long cartId) {
        CartId = cartId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }



}
