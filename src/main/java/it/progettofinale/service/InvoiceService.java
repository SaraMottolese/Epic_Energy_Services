package it.progettofinale.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.progettofinale.exception.InvoiceException;
import it.progettofinale.model.Customer;
import it.progettofinale.model.Invoice;
import it.progettofinale.model.PaymentStatus;
import it.progettofinale.repository.CustomerRepository;
import it.progettofinale.repository.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public Invoice add(Invoice invoice) {
		Optional<Customer> customerResult = customerRepository
				.findByCompanyNameContains(invoice.getCustomer().getCompanyName());
		if (customerResult.isPresent()) {
			invoice.setCustomer(customerResult.get());
			Optional<List<Invoice>> invoices = invoiceRepository.findByCustomerId(invoice.getCustomer().getId());
			if (invoices != null) {
				List<Invoice> invoiceList = invoices.get();
				for (Invoice i : invoiceList) {
					if (i.getNumber() == invoice.getNumber())
						throw new InvoiceException("Esiste gia' una fattura con questo numero");
				}
			}
			return invoiceRepository.save(invoice);
		} else
			throw new InvoiceException(
					"Impossibile salvare la fattura. Non esistono clienti con questa ragione sociale");
	}

	public void delete(Long id) {
		invoiceRepository.deleteById(id);
	}

	/*
	 * come nella realta' questo metodo permette di camiare solo lo stato della
	 * fattura, non tutti gli altri campi
	 */
	public Invoice update(Invoice invoice) {
		Optional<Invoice> invoiceResult = invoiceRepository.findById(invoice.getId());
		if (invoiceResult.isPresent()) {
			Invoice invoiceUpdate = invoiceResult.get();
			invoiceUpdate.setState(invoice.getState());
			return invoiceRepository.save(invoiceUpdate);
		} else
			throw new InvoiceException("Fattura non presente nel database");
	}

	public Page<Invoice> findAll(Pageable page) {
		return invoiceRepository.findAll(page);
	}

	/***************** FILTRI RICERCA *****************/

	public Page<List<Invoice>> findByCustomerId(Long id, Pageable page) {
		return invoiceRepository.findByCustomerId(id, page);
	}

	public Page<List<Invoice>> findByState(PaymentStatus status, Pageable page) {
		return invoiceRepository.findByState(status, page);
	}
	
	public Page<List<Invoice>> findByIssuingDate(Date date, Pageable page){
		return invoiceRepository.findByIssuingDate(date, page);
	}
	
	public Page<List<Invoice>> findByAmountBetween(Double min, Double max, Pageable page){
		return invoiceRepository.findByAmountBetween(min, max, page);
	}

	public Page<List<Invoice>> findByYear(Integer year, Pageable page){
		return invoiceRepository.findByYear(year, page);
	}
}
