package it.progettofinale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long>{
	
	Optional<Customer> findByCompanyName(String name);
	Optional<Customer>findByVtaNumber(Long vtaNumber);
}
