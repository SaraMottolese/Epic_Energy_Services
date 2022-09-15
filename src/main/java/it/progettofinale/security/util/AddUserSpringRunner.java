package it.progettofinale.security.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import it.progettofinale.security.model.Role;
import it.progettofinale.security.model.Roles;
import it.progettofinale.security.model.User;
import it.progettofinale.security.repository.RoleRepository;
import it.progettofinale.security.repository.UserRepository;

@Component
public class AddUserSpringRunner implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

		Role role = new Role();
		role.setRoleName(Roles.ROLE_ADMIN);
		Role roleUser = new Role();
		roleUser.setRoleName(Roles.ROLE_USER);
		User user = new User();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setUserName("admin");
		user.setPassword(bCrypt.encode("admin"));
		user.setEmail("admin@domain.com");
		user.setRoles(roles);
		user.setActive(true);

		roleRepository.save(role);
		roleRepository.save(roleUser);
		userRepository.save(user);

	}

}
