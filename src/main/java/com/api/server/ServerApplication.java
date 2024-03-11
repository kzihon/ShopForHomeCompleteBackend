package com.api.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.server.auth.AuthenticationService;
import com.api.server.auth.RegisterRequest;
import com.api.server.user.Role;
import com.api.server.user.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ServerApplication {

	private final AuthenticationService authenticationService;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository repository) {
		return args -> {

			var admin = RegisterRequest.builder()
					.firstname("admin")
					.lastname("test")
					.email("admin@test.com")
					.password("admin")
					.build();

			var customer1 = RegisterRequest.builder()
					.firstname("gago")
					.lastname("test")
					.email("gago@test.com")
					.password("gago")
					.build();

			var customer2 = RegisterRequest.builder()
					.firstname("ulaga")
					.lastname("test")
					.email("ulaga2@test.com")
					.password("ulaga")
					.build();

			var customer3 = RegisterRequest.builder()
					.firstname("gutom")
					.lastname("test")
					.email("gutom3@test.com")
					.password("gutom")
					.build();

			authenticationService.register(admin, Role.ADMIN);

			authenticationService.register(customer1, Role.CUSTOMER);
			authenticationService.register(customer2, Role.CUSTOMER);
			authenticationService.register(customer3, Role.CUSTOMER);
		};
	}

}
