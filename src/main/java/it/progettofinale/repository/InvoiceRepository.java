package it.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.progettofinale.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
