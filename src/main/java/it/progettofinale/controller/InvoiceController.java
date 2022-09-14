package it.progettofinale.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import it.progettofinale.model.Invoice;
import it.progettofinale.model.PaymentStatus;
import it.progettofinale.service.InvoiceService;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@PostMapping("/add")
	public ResponseEntity<Invoice> add(@RequestBody Invoice invoice){
		Invoice addInvoice= invoiceService.add(invoice);
		return new ResponseEntity<Invoice>(addInvoice,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Invoice> update(@RequestBody Invoice invoice){
		Invoice invoiceUpdate= invoiceService.update(invoice);
		return new ResponseEntity<Invoice>(invoiceUpdate,HttpStatus.OK);
	}
	
	/*
	 * ho creato il metodo ma in realta' non e' possibile eliminare delle fatture
	 * una volta emesse, possono essere stornate al massimo
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		invoiceService.delete(id);
		return new ResponseEntity<String>("Fattura eliminata correttamente", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<Invoice>> findAll(Pageable page){
		Page<Invoice> invoiceList= invoiceService.findAll(page);
		return new ResponseEntity<Page<Invoice>>(invoiceList, HttpStatus.OK);
	}
	
	/***************** FILTRI RICERCA *****************/
	
	@GetMapping("/companyInvoice/{id}")
	public ResponseEntity<Page<List<Invoice>>> findByCustomerId(@PathVariable Long id, Pageable page) {
		Page<List<Invoice>> invoiceList= invoiceService.findByCustomerId(id, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList, HttpStatus.OK);
	}
	
	@GetMapping("/state")
	public ResponseEntity<Page<List<Invoice>>> findByState (@RequestParam PaymentStatus status, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByState(status, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	@GetMapping("/issuingDate")
	public ResponseEntity<Page<List<Invoice>>> findByIssuingDate (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByIssuingDate(date, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	@GetMapping("/range")
	public ResponseEntity<Page<List<Invoice>>> findByAmountBetween (@RequestParam Double min, @RequestParam Double max, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByAmountBetween(min, max, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	@GetMapping("/year")
	public ResponseEntity<Page<List<Invoice>>> findByYear (@RequestParam Integer year, Pageable page){
		Page<List<Invoice>> invoiceList= invoiceService.findByYear(year, page);
		return new ResponseEntity<Page<List<Invoice>>>(invoiceList,HttpStatus.OK);
	}
	
	
	

}
