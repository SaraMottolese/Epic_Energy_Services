package it.progettofinale.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.County;

public interface CountyRepository extends JpaRepository<County, Long>{
	
	Optional<County> findById(Long id);
	
	Optional<County> findByName(String name);
	
	Optional<County> findByNameContains(String name);

	
}
