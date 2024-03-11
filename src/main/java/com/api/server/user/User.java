package com.api.server.user;

import java.util.Collection;
import java.util.List;

import com.api.server.ShoppingCart.ShoppingCart;
import com.api.server.order.Order;
import com.api.server.product.Product;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.server.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "_user")
public  class User implements UserDetails {

  @Id
  @GeneratedValue
  private long id;

  private String firstname;
  private String lastname;

  @Column(unique = true)
  @NotBlank(message = "Email is required.")
  @Email(message = "Invalid email format")
  private String email;

  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Token> tokens;

  private double discountPercentage=0;
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable
  private List<Product> wishlist;
  @Transient
  private ShoppingCart cart;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name="orders")
  private List<Order> orderList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
