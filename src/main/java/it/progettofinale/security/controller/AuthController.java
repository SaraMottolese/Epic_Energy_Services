package it.progettofinale.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.progettofinale.exception.CrmException;
import it.progettofinale.security.model.LoginRequest;
import it.progettofinale.security.model.LoginResponse;
import it.progettofinale.security.model.Role;
import it.progettofinale.security.model.Roles;
import it.progettofinale.security.model.SignupRequest;
import it.progettofinale.security.model.SignupResponse;
import it.progettofinale.security.model.User;
import it.progettofinale.security.repository.RoleRepository;
import it.progettofinale.security.repository.UserRepository;
import it.progettofinale.security.service.UserDetailsImpl;
import it.progettofinale.security.util.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
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

	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody SignupRequest signUpRequest) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		// Vedo se lo Username e l'Email non siano gia in uso
		if (userRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Errore: Username già in uso!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Errore: Email già in uso!"));
		}
		// Crea un nuovo user criptando la password
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setUserName(signUpRequest.getUsername());
		user.setPassword(bCrypt.encode(signUpRequest.getPassword()));
		user.setName(signUpRequest.getName());
		user.setUserName(signUpRequest.getSurname());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		// Vedo se il role inserito sia corretto
		if (strRoles.isEmpty()) {
			Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER)
					.orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
			roles.add(userRole);
		} else {
			for (String role : strRoles) {
				if (role.equals("")) {
					Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER)
							.orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
					roles.add(userRole);

				} else if (role.equalsIgnoreCase("admin")) {
					Role adminRole = roleRepository.findByRoleName(Roles.ROLE_ADMIN)
							.orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
					roles.add(adminRole);

				} else if (role.equalsIgnoreCase("user")) {
					Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER)
							.orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
					roles.add(userRole);
				} else {
					throw new CrmException("Puoi inserire soltanto role 'admin' o 'user'!");
				}
			}

		}

		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
	}

}
