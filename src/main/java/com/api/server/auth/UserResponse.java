package com.api.server.auth;

import com.api.server.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
  long id;
  String firstname;
  String lastname;
  String email;
  Role role;

  // wishlist

}
