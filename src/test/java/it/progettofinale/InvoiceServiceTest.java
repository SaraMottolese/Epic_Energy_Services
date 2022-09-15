package it.progettofinale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.progettofinale.model.Customer;
import it.progettofinale.model.Invoice;
import it.progettofinale.model.PaymentStatus;
import it.progettofinale.service.CustomerService;
import it.progettofinale.service.InvoiceService;

@SpringBootTest
public class InvoiceServiceTest {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired 
	private CustomerService customerService;
	
	
	@Test
	void test() {
		Customer customer= new Customer();
		customer.setCompanyName("Ciccio Pasticcio");
		customerService.add(customer);
		
		Invoice invoice= new Invoice();
		invoice.setAmount(189.99);
		invoice.setCustomer(customer);
		invoice.setNumber(3L);
		invoice.setState(PaymentStatus.PAGATA);
		invoice.setYear(2022);
		
		invoiceService.add(invoice);
		assertEquals(invoiceService.findById(1L).get().getAmount(), 189.99);
		
	}

}
