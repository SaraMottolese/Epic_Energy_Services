package it.progettofinale.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	Page<City> findAll(Pageable page);

	Optional<City> findById(Long id);

	Optional<City> findByNameContains(String name);
	
	@Query("select c from city c where c.name= :name")
	Optional<City> findByName(String name);

	
	@Query("SELECT c FROM city c WHERE c.county_name=: '%name%'")
	Optional<List<City>> findByCountyName(String name);

}
