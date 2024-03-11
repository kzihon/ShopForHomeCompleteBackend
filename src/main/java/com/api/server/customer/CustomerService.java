package com.api.server.customer;

import com.api.server.ShoppingCart.CartItem;
import com.api.server.ShoppingCart.ShoppingCart;
import com.api.server.auth.UserResponse;
import com.api.server.coupon.Coupon;
import com.api.server.coupon.CouponRepository;
import com.api.server.coupon.CouponService;
import com.api.server.order.Order;
import com.api.server.order.OrderRepository;
import com.api.server.product.Product;
import com.api.server.product.ProductRepository;
import com.api.server.user.UpdateUser;
import com.api.server.user.User;
import com.api.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private static final double SALES_TAX=8.875;
//  @Autowired
//  private CustomerRepository customerRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OrderRepository orderRepository;
  
  // @Autowired
  // private CouponRepository couponRepository;
  @Autowired
  private CouponService couponService;

  public List<User> getAllCustomers() {
    return userRepository.findAll();
  }

  public User getCustomerById(long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
  }

  public UserResponse updateCustomer(long customerId, UpdateUser updateUser) {
      User customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
      UserResponse user= new UserResponse();
      user.setId(customerId);
      user.setFirstname(updateUser.getFirstname());
      user.setLastname(updateUser.getLastname());
      user.setEmail(customer.getEmail());
      user.setRole(customer.getRole());
      customer.setFirstname(updateUser.getFirstname());
      customer.setLastname(updateUser.getLastname());

       userRepository.save(customer);
       return user;



    }

  public void deleteCustomer(Long id) {
    userRepository.deleteById(id);
  }

  public void addToWishList(long customer_id, long product_id) {
    User customer = userRepository.findById(customer_id).orElseThrow(() -> new RuntimeException("Customer not found"));
    Product product = productRepository.findById(product_id).orElseThrow(()->new RuntimeException("Product not found"));

    customer.getWishlist().add(product);
    userRepository.save(customer);
  }


  public List<Product> getWishList(long customer_id) {
    User customer = userRepository.findById(customer_id).orElseThrow(() -> new RuntimeException("Customer not found"));

    return customer.getWishlist();
  }


  public void removeFromWishList(long customer_id, long product_id) {
    User customer = userRepository.findById(customer_id).orElseThrow(() -> new RuntimeException("Customer not found"));
    Product product = productRepository.findById(product_id).orElseThrow(()->new RuntimeException("Product not found"));
    if(!customer.getWishlist().contains(product)) {
      throw new RuntimeException("Property not found");
    }
    customer.getWishlist().remove(product);
    userRepository.save(customer);
  }


  public Order proceedToCheckoutWithCoupon(long customerId, ShoppingCart shoppingCart, String couponCode) {
    Coupon coupon = couponService.verifyCouponByCode(couponCode);
    if( coupon==null){
      return null;
    }

    double subTotal=0;

    double discount=0;
    double totalBeforeTax=0;
    double estimatedTaxToBeCollected=0;
    double orderTotal=0;
    User customer = userRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    shoppingCart.setCustomer(customer);

    List<CartItem> cartItems = shoppingCart.getCartItems();
    Order order= new Order();

    for (CartItem cartItem : cartItems) {
      int requestedQuantity=cartItem.getQuantity();
      long productId=cartItem.getProductId();
      Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));

      if (product.getNumberInStock() < requestedQuantity) {
        throw new RuntimeException("Product out of stock: " + product.getName()+" ProductId "+ product.getProductId());
      }

    }
    for(CartItem cartItem: cartItems){
      int requestedQuantity=cartItem.getQuantity();
      long productId=cartItem.getProductId();
      Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));
      product.setNumberInStock(product.getNumberInStock() - requestedQuantity);
      for(int i=0; i<requestedQuantity ;i++){
        subTotal+= product.getPrice();
      }

      productRepository.save(product);
}

    shoppingCart.setCartItems(new ArrayList<>());
    // Save the updated shopping cart
    //cartRepository.save(shoppingCart);
    discount=(coupon.getDiscount()/100)*subTotal;
    // discount=(customer.getDiscountPercentage()/100)*subTotal;
    totalBeforeTax = subTotal - discount;
    estimatedTaxToBeCollected= totalBeforeTax *(SALES_TAX/100);
    orderTotal=totalBeforeTax + estimatedTaxToBeCollected;

    order.setSubTotal(subTotal);
    order.setDiscount(discount);
    order.setTotalBeforeTax(totalBeforeTax);
    order.setEstimatedTaxToBeCollected(estimatedTaxToBeCollected);
    order.setOrderTotal(orderTotal);

    orderRepository.save(order);
    customer.getOrderList().add(order);

    // Optionally, update the customer's reference to the shopping cart
    customer.setCart(new ShoppingCart());
    userRepository.save(customer);
    return order;
  }

  public Order proceedToCheckoutWithOutCoupons(long customerId, ShoppingCart shoppingCart) {


    double subTotal=0;

    double discount=0;
    double totalBeforeTax=0;
    double estimatedTaxToBeCollected=0;
    double orderTotal=0;
    User customer = userRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    shoppingCart.setCustomer(customer);

    List<CartItem> cartItems = shoppingCart.getCartItems();
    Order order= new Order();

    for (CartItem cartItem : cartItems) {
      int requestedQuantity=cartItem.getQuantity();
      long productId=cartItem.getProductId();
      Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));

      if (product.getNumberInStock() < requestedQuantity) {
        throw new RuntimeException("Product out of stock: " + product.getName()+" ProductId "+ product.getProductId());
      }

    }
    for(CartItem cartItem: cartItems){
      int requestedQuantity=cartItem.getQuantity();
      long productId=cartItem.getProductId();
      Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));

      product.setNumberInStock(product.getNumberInStock() - requestedQuantity);
      for(int i=0; i<requestedQuantity ;i++){
        subTotal+= product.getPrice();
      }

      productRepository.save(product);
    }

    shoppingCart.setCartItems(new ArrayList<>());
    // Save the updated shopping cart
    //cartRepository.save(shoppingCart);

    discount=(customer.getDiscountPercentage()/100)*subTotal;
    totalBeforeTax = subTotal - discount;
    estimatedTaxToBeCollected= totalBeforeTax *(SALES_TAX/100);
    orderTotal=totalBeforeTax + estimatedTaxToBeCollected;

    order.setSubTotal(subTotal);
    order.setDiscount(discount);
    order.setTotalBeforeTax(totalBeforeTax);
    order.setEstimatedTaxToBeCollected(estimatedTaxToBeCollected);
    order.setOrderTotal(orderTotal);

    orderRepository.save(order);
    customer.getOrderList().add(order);

    customer.setCart(new ShoppingCart());
    userRepository.save(customer);
    return order;
  }



  public ShoppingCart getCart(long customerId) {
    User customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

    return customer.getCart();
  }
}
