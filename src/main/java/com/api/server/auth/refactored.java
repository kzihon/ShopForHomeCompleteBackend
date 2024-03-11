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
import com.api.server.exception.UserAlreadyExistsExcemption;
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
public class refactored {

  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<AuthenticationResponse> createAdmin(RegisterRequest request) {
    return register(request, Role.ADMIN);
  }

  public ResponseEntity<AuthenticationResponse> createCustomer(RegisterRequest request) {
    return register(request, Role.CUSTOMER);
  }

  private ResponseEntity<AuthenticationResponse> register(RegisterRequest request, Role role) {
    if (repository.findByEmail(request.getEmail()).isPresent()) {
      throw new UserAlreadyExistsExcemption("User with email " + request.getEmail() + " already exists");
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(role)
        .build();

    String jwtToken = generateAndSaveToken(user);

    UserResponse userResponse = mapToUserResponse(user);

    AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
        .message("User created.")
        .user(userResponse)
        .build();

    return buildAuthResponse(HttpStatus.OK, jwtToken, authenticationResponse);
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

    User user = repository.findByEmail(request.getEmail())
        .orElseThrow(() -> new UserNotFoundException(null));

    var jwtToken = generateAndSaveToken(user);

    UserResponse userResponse = mapToUserResponse(user);

    var authenticationResponse = AuthenticationResponse.builder()
        .message("User authenticated.")
        .user(userResponse)
        .build();

    return buildAuthResponse(HttpStatus.OK, jwtToken, authenticationResponse);
  }

  private String generateAndSaveToken(User user) {
    String jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return jwtToken;
  }

  private UserResponse mapToUserResponse(User user) {
    return UserResponse.builder()
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    Token token = Token.builder()
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

  private ResponseEntity<AuthenticationResponse> buildAuthResponse(HttpStatus status, String jwtToken,
      AuthenticationResponse authenticationResponse) {
    return ResponseEntity.status(status)
        .header("Access-Control-Expose-Headers", "Authorization")
        .header("Authorization", "Bearer " + jwtToken)
        .body(authenticationResponse);
  }
}
