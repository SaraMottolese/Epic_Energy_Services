package it.progettofinale.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import it.progettofinale.model.Invoice;
import it.progettofinale.model.PaymentStatus;
import it.progettofinale.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
@SecurityRequirement(name = "bearerAuth")
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "aggiungi fattura", description = "Il metodo permette di aggiungere una nuova fattura")
	@ApiResponse(responseCode = "200", description = "fattura creata")
	public ResponseEntity<Invoice> add(@RequestBody Invoice invoice){
		Invoice addInvoice= invoiceService.add(invoice);
		return new ResponseEntity<Invoice>(addInvoice,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "emodifica fattura", description = "Il metodo permette di modificare una fattura")
	@ApiResponse(responseCode = "200", description = "fattura modificata")
	public ResponseEntity<Invoice> update(@RequestBody Invoice invoice){
		Invoice invoiceUpdate= invoiceService.update(invoice);
		return new ResponseEntity<Invoice>(invoiceUpdate,HttpStatus.OK);
	}
	
	/*
	 * ho creato il metodo ma in realta' non e' possibile eliminare delle fatture
	 * una volta emesse, possono essere stornate al massimo
	 */
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "elimina fattura", description = "Il metodo permette di eliminare una fattura conoscendo l'id")
	@ApiResponse(responseCode = "200", description = "fattura eliminata")
	public ResponseEntity<String> delete(@PathVariable Long id){
		invoiceService.delete(id);
		return new ResponseEntity<String>("Fattura eliminata correttamente", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista fatture", description = "Il metodo restituisce la lista di fatture presenti nel database")
	@ApiResponse(responseCode = "200", description = "lista restituita")
	public ResponseEntity<Page<Invoice>> findAll(Pageable page){
		Page<Invoice> invoiceList= invoiceService.findAll(page);
		return new ResponseEntity<Page<Invoice>>(invoiceList, HttpStatus.OK);
	}
	
	/***************** FILTRI RICERCA *****************/
	
	@GetMapping("/companyInvoice/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova fattura da id cliente", description = "Il metodo restituisce la lista delle fatture conoscendo l'id del cliente")
	@ApiResponse(responseCode = "200", description = "lista restituita")
	public ResponseEntity<Page<List<Invoice>>> findByCustomerId(@PathVariable Long id, Pageable page) {
		Page<List<Invoice>> invoiceList= invoiceService.findByCustomerId(id, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList, HttpStatus.OK);
	}
	
	@GetMapping("/state")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista fatture per stato", description = "Il metodo restituisce la lista di fatture per il loro stato di pagamento")
	@ApiResponse(responseCode = "200", description = "lista restituita")
	public ResponseEntity<Page<List<Invoice>>> findByState (@RequestParam PaymentStatus status, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByState(status, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	@GetMapping("/issuingDate")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova da data di emissione", description = "Il metodo restituisce la lista delle fatture emesse in un determinato giorno")
	@ApiResponse(responseCode = "200", description = "lista restituita")
	public ResponseEntity<Page<List<Invoice>>> findByIssuingDate (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByIssuingDate(date, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	@GetMapping("/range")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova da range dell'importo", description = "Il metodo restituisce la lista delle fatture in un range di importo")
	@ApiResponse(responseCode = "200", description = "lista restituita")
	public ResponseEntity<Page<List<Invoice>>> findByAmountBetween (@RequestParam Double min, @RequestParam Double max, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByAmountBetween(min, max, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	@GetMapping("/year")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova da anno emissione", description = "Il metodo restituisce la lista delle fatture emesse in un determinato anno")
	@ApiResponse(responseCode = "200", description = "lista restituita")
	public ResponseEntity<Page<List<Invoice>>> findByYear (@RequestParam Integer year, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByYear(year, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	
	

}
