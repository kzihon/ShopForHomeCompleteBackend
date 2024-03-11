package com.api.server.customer;

import java.util.List;

import com.api.server.ShoppingCart.ShoppingCart;
import com.api.server.order.Order;
import com.api.server.product.Product;
import com.api.server.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.api.server.auth.AuthenticationResponse;
import com.api.server.auth.AuthenticationService;
import com.api.server.auth.RegisterRequest;
import com.api.server.auth.UserResponse;
import com.api.server.user.Role;
import com.api.server.user.UpdateUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final AuthenticationService authenticationService;
  private final CustomerService customerService;

  @GetMapping("/")
  @PreAuthorize("hasAuthority('admin:read')")
  public ResponseEntity<List<User>> getAllCustomers() {
    List<User> customers = customerService.getAllCustomers();

    return ResponseEntity.ok(customers);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('admin:read', 'customer:read')")
  public ResponseEntity<User> getCustomerById(@PathVariable Integer id) {
    User customer = customerService.getCustomerById(id);

    return ResponseEntity.ok(customer);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
          @RequestBody RegisterRequest request) {


    return authenticationService.register(request, Role.CUSTOMER);
  }

  @PutMapping("/update/{id}")
  @PreAuthorize("hasAnyAuthority('admin:update', 'customer:update')")
  public ResponseEntity<UserResponse> updateCustomer(@PathVariable long id,
          @RequestBody UpdateUser updateUser) {
            UserResponse user= customerService.updateCustomer(id, updateUser);

    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('admin:delete', 'customer:delete')")
  public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{customer_id}/wish_list/{product_id}")
  @PreAuthorize("hasAuthority('customer:create')")
  public ResponseEntity<String> addToWishList(@PathVariable long customer_id, @PathVariable long product_id) {

    try {
      customerService.addToWishList(customer_id, product_id);
      return ResponseEntity.ok("Product added to wishlist successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{customer_id}/wish_list")
  @PreAuthorize("hasAuthority('customer:read')")
  public List<Product> getWishList(@PathVariable long customer_id) {
    List<Product> products = customerService.getWishList(customer_id);

    return products;
  }

  @DeleteMapping("/{customer_id}/wish_list/{product_id}")
  @PreAuthorize("hasAuthority('customer:delete')")
  // @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<String> deleteFromWishList(@PathVariable long customer_id, @PathVariable long product_id) {

    try {
      customerService.removeFromWishList(customer_id, product_id);
      return ResponseEntity.ok("Product removed from wishlist successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @PostMapping("/{customer_id}/shopping-cart/coupon/{couponCode}")
  @PreAuthorize("hasAnyAuthority('customer:create')")
  public ResponseEntity<Order> proceedToCheckOutWithCouponsAndGetOrder(@PathVariable long customer_id, @PathVariable String couponCode, @RequestBody ShoppingCart sc){

    try {
      Order order=customerService.proceedToCheckoutWithCoupon(customer_id, sc, couponCode);
      return ResponseEntity.status(HttpStatus.OK).body(order);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/{customer_id}/shopping-cart/")
  //@PreAuthorize("hasAnyAuthority('customer:create')")
  public ResponseEntity<Order> proceedToCheckOutWithOutCouponsAndGetOrder(@PathVariable long customer_id, @RequestBody ShoppingCart sc){

    try {
      Order order=customerService.proceedToCheckoutWithOutCoupons(customer_id, sc);
      return ResponseEntity.status(HttpStatus.OK).body(order);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/{customer_id}")
  public ShoppingCart getCart(@PathVariable long customer_id){

    return customerService.getCart(customer_id);
  }
}