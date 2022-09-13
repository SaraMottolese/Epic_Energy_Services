package it.progettofinale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
	Optional<Address> findByCity_Id(Long id);

}
