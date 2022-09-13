package it.progettofinale.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c WHERE c.companyName=: '%name%'")
	Optional<Customer> findByCompanyName(String name);

	Optional<Customer> findByVtaNumber(Long vtaNumber);

	Optional<List<Customer>> findByRevenue(Double revenue);

	Optional<List<Customer>> findByRegistrationDate(Date registration);

	Optional<List<Customer>> findByLastContactDate(Date lastContact);
}
