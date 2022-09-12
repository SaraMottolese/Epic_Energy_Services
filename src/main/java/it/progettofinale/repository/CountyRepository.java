package it.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.City;

public interface CountyRepository extends JpaRepository<City, Long>{

}
