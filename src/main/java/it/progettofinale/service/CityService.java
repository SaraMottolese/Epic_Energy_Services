package it.progettofinale.service;

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

}
