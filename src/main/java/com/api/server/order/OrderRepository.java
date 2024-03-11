 package com.api.server.order;

 import org.springframework.data.jpa.repository.JpaRepository;

 public interface OrderRepository extends JpaRepository<Order, Long> {

 //Order findByIdAndOrderStatus(Long customerId, OrderStatus pending);

 }