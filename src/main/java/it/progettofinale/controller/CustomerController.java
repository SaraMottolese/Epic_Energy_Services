package it.progettofinale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.progettofinale.model.Customer;
import it.progettofinale.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerservice;
	
	@PostMapping("/add")
	//@ApiResponse(responseCode = "200", description = "Cliente aggiunto correttamente")
	public ResponseEntity <Customer> add(@RequestBody Customer customer){
		Customer addCustomer= customerservice.add(customer);
		return new ResponseEntity<Customer>(addCustomer, HttpStatus.CREATED);
	}
	

}
