package it.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.City;

public interface CityRepository extends JpaRepository <City, Long>{

}
