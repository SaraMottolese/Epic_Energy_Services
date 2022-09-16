package it.progettofinale.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progettofinale.security.model.LoginRequest;
import it.progettofinale.security.model.LoginResponse;
import it.progettofinale.security.model.Role;
import it.progettofinale.security.model.Roles;
import it.progettofinale.security.model.SignupRequest;
import it.progettofinale.security.model.User;
import it.progettofinale.security.repository.RoleRepository;
import it.progettofinale.security.service.UserDetailsImpl;
import it.progettofinale.security.service.UserService;
import it.progettofinale.security.util.JwtUtils;
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/login")
	@Operation(summary = "login", description = "Il metodo permette l'autenticazione")
	@ApiResponse(responseCode = "200", description = "autenticazione avvenuta")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		LoginResponse loginResponse = new LoginResponse();

		loginResponse.setToken(token);
		loginResponse.setRoles(roles);

		return ResponseEntity.ok(loginResponse);
	}

	@PostMapping("/signupform")
	@Operation(summary = "registrazione", description = "Il metodo permette di aggiungere un nuovo User")
	@ApiResponse(responseCode = "200", description = "User aggiunto")
	public ResponseEntity<User> addUser(@RequestBody SignupRequest signUpRequest) {
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setUserName(signUpRequest.getUsername());
		String password = BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt());
		user.setPassword(password);
		user.setName(signUpRequest.getName());
		user.setSurname(signUpRequest.getSurname());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		// Verifica l'esistenza dei Role
		if (signUpRequest.getRole().contains("admin")) {
			Role userRole = roleRepository.findByRoleName(Roles.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
			roles.add(userRole);
		}

		Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
		roles.add(userRole);

		
		user.setRoles(roles);
		userService.add(user);
		authenticateUser(new LoginRequest(signUpRequest.getUsername(), signUpRequest.getPassword()));
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	


}
