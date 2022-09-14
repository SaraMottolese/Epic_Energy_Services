package it.progettofinale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.progettofinale.security.model.User;
import it.progettofinale.security.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	

}
