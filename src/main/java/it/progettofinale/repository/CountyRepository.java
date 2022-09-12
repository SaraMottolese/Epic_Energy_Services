package it.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.County;
import it.progettofinale.model.Customer;

public interface CountyRepository extends JpaRepository<County, Long>{

	
}
