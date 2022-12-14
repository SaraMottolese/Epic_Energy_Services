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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progettofinale.model.City;
import it.progettofinale.service.CityService;
@RestController
@RequestMapping("/api/city")
@SecurityRequirement(name = "bearerAuth")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	
	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista dei comuni", description = "Il metodo ritorna indietro la lista completa dei comuni"
			+ "Questi comuni vengono caricati nel database da un file csv")
	@ApiResponse(responseCode = "200", description = "Lista dei comuni acquisita")
	public ResponseEntity<Page<City>> getAll(Pageable page){
		Page<City> cityList= cityService.findAll(page);
		return new ResponseEntity<Page<City>>(cityList, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova comune da id", description = "Il metodo ritorna l'oggetto comune ricercato tramite id")
	@ApiResponse(responseCode = "200", description = "comune trovato")
	public ResponseEntity<City> getById(@PathVariable Long id){
		Optional<City> cityResult=cityService.findById(id);
		City city= cityResult.get();
		return new ResponseEntity<City>(city, HttpStatus.OK);
	}
	
	@GetMapping("/getByName")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "ltrova comune da nome", description = "Il metodo ritorna indietro l'oggetto comune trovato tramite nome esatto"
			+ "Questi comuni vengono caricati nel database da un file csv")
	@ApiResponse(responseCode = "200", description = "Lista dei comuni acquisita")
	public ResponseEntity<City> getByName(@RequestParam String name){
		Optional<City> cityResult=cityService.findByExtactName(name);
		City city= cityResult.get();
		return new ResponseEntity<City>(city, HttpStatus.OK);
	}
	
	
}
