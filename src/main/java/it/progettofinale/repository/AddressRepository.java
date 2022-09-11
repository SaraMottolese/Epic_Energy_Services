package it.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
