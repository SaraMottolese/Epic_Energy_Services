package it.progettofinale.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Invoice;
import it.progettofinale.model.PaymentStatus;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
	
	Page<List<Invoice>> findByCustomerId(Long id, Pageable page);
	
	Optional<List<Invoice>> findByCustomerId(Long id);

	Page<List<Invoice>> findByState(PaymentStatus status, Pageable page);
	
	Page<List<Invoice>> findByIssuingDate(Date date, Pageable page);
	
	Page<List<Invoice>> findByAmountBetween(Double min, Double max, Pageable page);
	
	Page<List<Invoice>> findByYear(Integer year, Pageable page);
}
