package it.progettofinale.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.security.model.Role;
import it.progettofinale.security.model.Roles;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(Roles role);
}
