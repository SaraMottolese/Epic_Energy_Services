package it.progettofinale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.progettofinale.model.Customer;
import it.progettofinale.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerservice;

	@PostMapping("/add")
	// @ApiResponse(responseCode = "200", description = "Cliente aggiunto
	// correttamente")
	public ResponseEntity<Customer> add(@RequestBody Customer customer) {
		Customer addCustomer = customerservice.add(customer);
		return new ResponseEntity<Customer>(addCustomer, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Customer> update(@RequestBody Customer customer) {
		Customer customerUpdate = customerservice.update(customer);
		return new ResponseEntity<Customer>(customerUpdate, HttpStatus.OK);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Customer>> getAll() {
		List<Customer> customerList = customerservice.findAll();
		return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		customerservice.delete(id);
		return new ResponseEntity<String>("Libro eliminato correttamente", HttpStatus.OK);
	}

	/***************** FIND BY ****************/
	
	@GetMapping("/companyName")
	public ResponseEntity<Customer> findByCompanyName(@RequestParam String name){
		Customer customerFound= customerservice.findByCompanyName(name);
			return new ResponseEntity<Customer>(customerFound,HttpStatus.OK);
	}
	
	@GetMapping("/vtaNumber/{vtaNumeber}")
	public ResponseEntity<Customer> findByCompanyName(@PathVariable Long vtaNumeber){
		Customer customerFound= customerservice.findByVtaNumber(vtaNumeber);
			return new ResponseEntity<Customer>(customerFound,HttpStatus.OK);
	}
	
	@GetMapping("/revenue")
	public ResponseEntity <Page<Customer>> findByRevenue(@RequestParam Double revenue, Pageable page){
		Page<Customer> customerList= customerservice.findByRevenue(page, revenue);
		return new ResponseEntity <Page<Customer>> (customerList, HttpStatus.OK);
	}

}
