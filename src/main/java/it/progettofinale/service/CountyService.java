package it.progettofinale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<County> findAll(Pageable page){
		return countyRepository.findAll(page);
	}
	
	public Optional<County> findById(Long id) {
		return countyRepository.findById(id);
	}
	
	public Optional<County> findByName(String name){
		return countyRepository.findByName(name);
	}
	
	public Optional<County> findByNameContains(String name){
		return countyRepository.findByNameContains(name);
	}

}
