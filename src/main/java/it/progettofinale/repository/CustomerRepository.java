package it.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long>{

}
