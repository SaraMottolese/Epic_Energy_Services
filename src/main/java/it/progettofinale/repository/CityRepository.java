package it.progettofinale.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	Optional<City> findById(Long id);

	@Query("SELECT c FROM city c WHERE c.name=: '%name%'")
	Optional<City> findByName(String name);
	
	@Query("SELECT c FROM city c WHERE c.county_name=: '%name%'")
	Optional<List<City>> findByCountyName(String name);

}
