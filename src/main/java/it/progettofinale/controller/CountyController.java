package it.progettofinale.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.progettofinale.model.County;
import it.progettofinale.service.CountyService;

@RestController
@RequestMapping("/api/county")
public class CountyController {
	
	@Autowired
	private CountyService countyService;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<County>> getAll(Pageable page){
		Page<County> countyList= countyService.findAll(page);
		return new ResponseEntity<Page<County>>(countyList, HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<County> getById(@PathVariable Long id){
		Optional<County> countyResult=countyService.findById(id);
		County county=countyResult.get();
		return new ResponseEntity<County>(county, HttpStatus.OK);
	}
	
	@GetMapping("/getByName")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<County> getByName(@RequestParam String name){
		Optional<County> countyResult= countyService.findByName(name);
		County county= countyResult.get();
		return new ResponseEntity<County>(county, HttpStatus.OK);
	}
	

}
