package it.progettofinale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.progettofinale.model.County;
import it.progettofinale.repository.CountyRepository;

@Service
public class CountyService {
	@Autowired
	private CountyRepository countyRepository;

	public void add(County c) {
		countyRepository.save(c);
		
	}

}
