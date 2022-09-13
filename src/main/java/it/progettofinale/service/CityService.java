package it.progettofinale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.progettofinale.model.City;
import it.progettofinale.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public void add(City c) {
		cityRepository.save(c);
	}

	public List<City> findAll() {
		return cityRepository.findAll();
	}

	public Optional<City> findById(Long id) {
		return cityRepository.findById(id);
	}

	public Optional<List<City>> findByCountyName(String name) {
		return cityRepository.findByCountyName(name);
	}
	
	public Optional<City> findByName(String name){
		return cityRepository.findByName(name);
	}
}
