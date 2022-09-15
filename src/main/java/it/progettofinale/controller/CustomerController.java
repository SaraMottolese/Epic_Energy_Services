package it.progettofinale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progettofinale.model.Customer;
import it.progettofinale.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

	@Autowired
	private CustomerService customerservice;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "aggiungi cliente", description = "Il metodo permette di aggiungere un nuovo cliente")
	@ApiResponse(responseCode = "200", description = "cliente creato con successo")
	public ResponseEntity<Customer> add(@RequestBody Customer customer) {
		Customer addCustomer = customerservice.add(customer);
		return new ResponseEntity<Customer>(addCustomer, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "update cliente", description = "Il metodo permette di modificare un cliente")
	@ApiResponse(responseCode = "200", description = "cliente modificato")
	public ResponseEntity<Customer> update(@RequestBody Customer customer) {
		Customer customerUpdate = customerservice.update(customer);
		return new ResponseEntity<Customer>(customerUpdate, HttpStatus.OK);
	}

	@GetMapping("/getall")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista clienti", description = "Il metodo ritorna la lista di tutti i clienti")
	@ApiResponse(responseCode = "200", description = "lista clienti ritornata")
	public ResponseEntity<Page<Customer>> getAll(Pageable page) {
		Page<Customer> customerList = customerservice.findAll(page);
		return new ResponseEntity<Page<Customer>>(customerList, HttpStatus.OK);
	}

	/*
	 * cancellando un cliente verranno cancellate anche tutte le fatture relative ad esso
	 */
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "elimina cliente", description = "Il metodo permette di eliminare un cliente conoscendo l'id"
			+ "cancellando un cliente vengono elliminate anche le relative fatture")
	@ApiResponse(responseCode = "200", description = "contatto eliminato")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		customerservice.delete(id);
		return new ResponseEntity<String>("Cliente eliminato correttamente", HttpStatus.OK);
	}

	/***************** FIND BY ****************/

	@GetMapping("/companyName")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova cliente da nome", description = "Il metodo permette cercare un cliente conoscendo il company name")
	@ApiResponse(responseCode = "200", description = "cliente trovato")
	public ResponseEntity<Customer> findByCompanyName(@RequestParam String name) {
		Customer customerFound = customerservice.findByCompanyName(name);
		return new ResponseEntity<Customer>(customerFound, HttpStatus.OK);
	}

	@GetMapping("/vtaNumber/{vtaNumeber}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova cliente da partita iva", description = "Il metodo permette di cercare un cliente conoscendo il numero di partita iva")
	@ApiResponse(responseCode = "200", description = "cliente trovato")
	public ResponseEntity<Customer> findByVtaNumber(@PathVariable Long vtaNumeber) {
		Customer customerFound = customerservice.findByVtaNumber(vtaNumeber);
		return new ResponseEntity<Customer>(customerFound, HttpStatus.OK);
	}

	@GetMapping("/revenue")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova lista clienti da revenue", description = "Il metodo permette di trovare una lista di clienti che hanno un determinato revenue")
	@ApiResponse(responseCode = "200", description = "contatti trovati")
	public ResponseEntity<Page<Customer>> findByRevenue(@RequestParam Double revenue, Pageable page) {
		Page<Customer> customerList = customerservice.findByRevenue(page, revenue);
		return new ResponseEntity<Page<Customer>>(customerList, HttpStatus.OK);
	}

}
