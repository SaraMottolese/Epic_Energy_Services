package it.progettofinale.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
	Optional<Customer> findByCompanyNameContains(String name);

	Optional<Customer> findByVtaNumber(Long vtaNumber);

	Page<Customer> findByRevenue (Pageable page, Double fatturato);

	Optional<List<Customer>> findByRegistrationDate(Date registration);

	Optional<List<Customer>> findByLastContactDate(Date lastContact);
}
