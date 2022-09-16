package it.progettofinale.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import it.progettofinale.model.Customer;
import it.progettofinale.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component // indica che la classe e' un bean
public class CustomerDbLoader implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;
	
	@Value("${progettofinale.customerpath}")
	private String customerPath;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("caricamento dei clienti");
		FileReader fileReader= new FileReader(customerPath);
		try {
			List<Customer> customerList= new ArrayList<>();
		customerList=MyCustomerFileReader.read(fileReader);
		for(Customer c: customerList)
			customerService.add(c);
		}catch(FileNotFoundException e) {
			log.error("file al path " + customerPath + " non trovato");
			e.printStackTrace();
		}catch (IOException e) {
			log.error("IOException");
			e.printStackTrace();
		}
		log.info("caricamento dei clienti completato");

	}

}
