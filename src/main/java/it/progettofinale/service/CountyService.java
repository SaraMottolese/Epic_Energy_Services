package it.progettofinale.service;

import java.util.List;
import java.util.Optional;

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
	
	public List<County> findAll(){
		return countyRepository.findAll();
	}
	
	public Optional<County> findById(Long id) {
		return countyRepository.findById(id);
	}
	
	public Optional<County> findByName(String name){
		return countyRepository.findByName(name);
	}

}
