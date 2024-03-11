package com.api.server.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;

import org.springframework.stereotype.Service;
import com.api.server.config.JwtService;
import com.api.server.exception.UserNotFoundException;
import com.api.server.token.Token;
import com.api.server.token.TokenRepository;
import com.api.server.token.TokenType;
import com.api.server.user.Role;
import com.api.server.user.User;
import com.api.server.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<AuthenticationResponse> register(RegisterRequest request, Role role) {

    if (isUserExists(request.getEmail())) {
      var errorMessage = "User with email " + request.getEmail() + " already exists";
      var authenticationResponse = AuthenticationResponse.builder()
          .message(errorMessage)
          .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(authenticationResponse);
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(role).build();

    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    User savedUser = userRepository.save(user);
    saveUserToken(savedUser, jwtToken);

    var userResponse = createUserResponse(savedUser);

    var authenticationResponse = AuthenticationResponse.builder()
        .message("User created.")
        .user(userResponse)
        .build();

    return ResponseEntity.status(HttpStatus.OK)
        .header("Access-Control-Expose-Headers", "Authorization")
        .header("Authorization", "Bearer " + jwtToken)
        .body(authenticationResponse);
  }

  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request)
      throws UserNotFoundException {

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          request.getEmail(),
          request.getPassword()));
    } catch (AuthenticationException e) {
      return handleAuthenticationFailure(e);
    }

    User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException(null));

    var jwtToken = jwtService.generateToken(user);

    saveUserToken(user, jwtToken);

    var userResponse = createUserResponse(user);

    var authenticationResponse = AuthenticationResponse.builder()
        .message("User authenticated.")
        .user(userResponse)
        .build();

    ResponseEntity<AuthenticationResponse> responseEntity = ResponseEntity.status(HttpStatus.OK)
        .header("Access-Control-Expose-Headers", "Authorization")
        .header("Authorization", "Bearer " + jwtToken)
        .body(authenticationResponse);

    return responseEntity;

  }

  private UserResponse createUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  private boolean isUserExists(String email) {
    // return false;
    return userRepository.findByEmail(email).isPresent();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .revoked(false)
        .expired(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

    if (validUserTokens.isEmpty())
      return;

    validUserTokens.forEach(t -> {
      t.setExpired(true);
      t.setRevoked(true);
    });

    tokenRepository.saveAll(validUserTokens);
  }

  private ResponseEntity<AuthenticationResponse> handleAuthenticationFailure(AuthenticationException exception) {
    var errorMessage = exception instanceof BadCredentialsException ? "Invalid credentials." : "Authentication failed";

    var authenticationResponse = AuthenticationResponse.builder()
        .message(errorMessage)
        .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(authenticationResponse);
  }
}
