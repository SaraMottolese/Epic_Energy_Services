package it.progettofinale.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.progettofinale.exception.AddressException;
import it.progettofinale.exception.CustomerException;
import it.progettofinale.model.Address;
import it.progettofinale.model.City;
import it.progettofinale.model.Customer;
import it.progettofinale.repository.AddressRepository;
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
	@Autowired
	private AddressRepository addressRepository;

	/***************** CRUD *****************/

	/*
	 * Il metodo controlla (prima dell'inserimento) se il nome della societa' e'
	 * gia' esistente nel db. Se esiste allora viene lanciato un errore altrimenti
	 * il record viene salvato. Inoltre controlla anche se i campi sono null. I
	 * campi come companyName, vtaNumber non possono essere null, gli altri si.
	 * 
	 * Per l'inserimento degli indirizzi controllo che il comune sia presente nel db
	 * (che viene popolato tramite il file csv se il comune e' presente allora viene
	 * inserito altrimenti viene generato un errore
	 */
	public Customer add(Customer customer) {
		Customer newCustomer = new Customer();
		Optional<Customer> customerResult = customerRepository.findByVtaNumber(customer.getVtaNumber());
		if (!customerResult.isPresent()) {
			if (customer.getCompanyName() != null)
				newCustomer.setCompanyName(customer.getCompanyName());
			if (customer.getVtaNumber() != null)
				newCustomer.setVtaNumber(customer.getVtaNumber());
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setPec(customer.getPec());
			newCustomer.setPhoneNumber(customer.getPhoneNumber());
			newCustomer.setContact(customer.getContact());

			Address operationalAddress = customer.getOperationalHeadquartersAddress();
			City city = operationalAddress.getCity();
			Optional<City> cityDb = cityRepository.findByName(city.getName());
			if (cityDb.isPresent()) {
				City cityResult = cityDb.get();
				operationalAddress.setCity(cityResult);
				newCustomer.setOperationalHeadquartersAddress(operationalAddress);
			} else
				throw new AddressException("Comune non trovato");

			Address registeredAddress = customer.getOperationalHeadquartersAddress();
			City city1 = registeredAddress.getCity();
			Optional<City> cityDb1 = cityRepository.findByName(city1.getName());
			if (cityDb.isPresent()) {
				City cityResult = cityDb.get();
				registeredAddress.setCity(cityResult);
				newCustomer.setRegisteredOfficeAddress(registeredAddress);
			} else
				throw new AddressException("Comune non trovato");
			newCustomer.setRegistrationDate(customer.getRegistrationDate());
			newCustomer.setLastContactDate(customer.getLastContactDate());
			newCustomer.setRevenue(customer.getRevenue());
			newCustomer.setInvoices(customer.getInvoices());
			return customerRepository.save(newCustomer);
		} else
			throw new CustomerException("Cliente gia' presente nel database");
	}

	public Customer update(Customer customer) {
		Optional<Customer> customerResult = customerRepository.findById(customer.getId());
		if (customerResult.isPresent()) {
			Customer customerUpdate = customerResult.get();
			customerUpdate.setCompanyName(customer.getCompanyName());
			customerUpdate.setVtaNumber(customer.getVtaNumber());
			customerUpdate.setEmail(customer.getEmail());
			customerUpdate.setPec(customer.getPec());
			customerUpdate.setPhoneNumber(customer.getPhoneNumber());
			customerUpdate.setContact(customer.getContact());
			Address operationalAddress = customer.getOperationalHeadquartersAddress();
			City city = operationalAddress.getCity();
			Optional<City> cityDb = cityRepository.findByName(city.getName());
			if (cityDb.isPresent()) {
				City cityResult = cityDb.get();
				operationalAddress.setCity(cityResult);
				customerUpdate.setOperationalHeadquartersAddress(operationalAddress);
			} else
				throw new AddressException("Comune non trovato");

			Address registeredAddress = customer.getOperationalHeadquartersAddress();
			City city1 = registeredAddress.getCity();
			Optional<City> cityDb1 = cityRepository.findByName(city1.getName());
			if (cityDb.isPresent()) {
				City cityResult = cityDb.get();
				registeredAddress.setCity(cityResult);
				customerUpdate.setRegisteredOfficeAddress(registeredAddress);
			} else
				throw new AddressException("Comune non trovato");
			customerUpdate.setRegistrationDate(customer.getRegistrationDate());
			customerUpdate.setLastContactDate(customer.getLastContactDate());
			customerUpdate.setRevenue(customer.getRevenue());
			customerUpdate.setInvoices(customer.getInvoices());
			return customerRepository.save(customerUpdate);
		} else
			throw new CustomerException(
					"Impossibile aggiornare il record del cliente in quanto non presente nel database");

	}

	public void delete(Long id) {
		Optional<Customer> customerResult = customerRepository.findById(id);
		if (customerResult.isPresent())
			customerRepository.deleteById(id);
		else
			throw new CustomerException(
					"Impossibile eliminare il record del cliente in quanto non presente nel database");
	}

	public Page<Customer> findAll(Pageable page) {
		return customerRepository.findAll(page);
	}

	/***************** FILTRI RICERCA *****************/

	public Customer findByCompanyName(String name) {
		Optional<Customer> customerResult = customerRepository.findByCompanyNameContains(name);
		if (customerResult.isPresent()) {
			Customer customer = customerResult.get();
			return customer;
		} else
			throw new CustomerException("Cliente non trovato");
	}

	public Customer findByVtaNumber(Long vtaNumber) {
		Optional<Customer> customerResult = customerRepository.findByVtaNumber(vtaNumber);
		if (customerResult.isPresent()) {
			Customer customer = customerResult.get();
			return customer;
		} else
			throw new CustomerException("Cliente non trovato");
	}

	public Page<Customer> findByRevenue(Pageable page, Double revenue) {
		Page<Customer> customerResult = customerRepository.findByRevenue(page, revenue);
		if (customerResult != null)
			return customerResult;
		else
			throw new CustomerException("Nessun cliente trovato");
	}

	public Optional<List<Customer>> findByRegistrationDate(Date registration) {
		return customerRepository.findByRegistrationDate(registration);
	}
	/***************** ORDINE RISULTATI *****************/

}
