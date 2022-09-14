package it.progettofinale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	Optional<Contact> findById (Long id);
	

}
