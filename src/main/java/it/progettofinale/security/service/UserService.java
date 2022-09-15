package it.progettofinale.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Optional<User> findByUserName(String username){
		return userRepository.findByUserName(username);
	}
	
	public Page<User> findAll(Pageable page){
		return userRepository.findAll(page);
	}
	
	public User add(User user) {
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean existsByUserName(String username) {
		return userRepository.existsByUserName(username);
	}

	

}
