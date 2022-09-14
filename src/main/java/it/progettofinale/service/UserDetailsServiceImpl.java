package it.progettofinale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.progettofinale.security.model.User;
import it.progettofinale.security.repository.UserRepository;
import it.progettofinale.security.service.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	//e' transactional
	//si trova l'utente, me lo carico dal mio db se e' presente lancio la buiold altrimenti lancio l'eccezione
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserName(userName);

		if (user.isPresent()) {
			return UserDetailsImpl.build(user.get());
		} else {
			throw new UsernameNotFoundException("User Not Found with username: " + userName);
		}
	}

}
