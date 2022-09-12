package it.progettofinale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.progettofinale.exception.CustomerException;
import it.progettofinale.model.Customer;
import it.progettofinale.repository.CityRepository;
import it.progettofinale.repository.CountyRepository;
import it.progettofinale.repository.CustomerRepository;
import it.progettofinale.repository.InvoiceRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CountyRepository countyRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired 
	private InvoiceRepository invoiceRepository;
	
	/***************** CRUD *****************/
	
	/*
	 * Il metodo controlla (prima dell'inserimento) se il nome della societa' e' gia' esistente nel db. 
	 * Se esiste allora viene lanciato un errore altrimenti il record viene salvato.
	 * Inoltre controlla anche se i campi sono null. I campi come companyName, vtaNumber non possono essere null, gli altri si.
	 */
	private Customer add(Customer customer) {
		Customer newCustomer= new Customer();
		Optional <Customer> customerResult= customerRepository.findByVtaNumber(customer.getVtaNumber());
		if(customerResult.isPresent()) {
			if(customer.getCompanyName()!= null)
				newCustomer.setCompanyName(customer.getCompanyName());
			if(customer.getVtaNumber()!= null)
				newCustomer.setVtaNumber(customer.getVtaNumber());
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setPec(customer.getPec());
			newCustomer.setPhoneNumber(customer.getPhoneNumber());
			newCustomer.setContact(customer.getContact());
			newCustomer.setOperationalHeadquartersAddress(customer.getOperationalHeadquartersAddress());
			newCustomer.setRegisteredOfficeAddress(customer.getRegisteredOfficeAddress());
			newCustomer.setRegistrationDate(customer.getRegistrationDate());
			newCustomer.setLastContactDate(customer.getLastContactDate());
			newCustomer.setRevenue(customer.getRevenue());
			newCustomer.setInvoices(customer.getInvoices());
			return customerRepository.save(newCustomer);
		}else
			throw new CustomerException("Cliente gia' presente nel database");
	}
}
