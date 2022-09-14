package it.progettofinale.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.progettofinale.model.Customer;
import it.progettofinale.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class MyCustomerFileReader {
	
	public static List<Customer> read(FileReader file) throws IOException{
		CSVReader reader = new CSVReader(file, ',','\'',1);
		List<Customer> customerList= new ArrayList<>();
		String[] record = null;
		while((record= reader.readNext())!=null) {
			for (int i = 0; i < record.length; i++) {
				log.info("record[" + i + "] = " + record[i]);
			}
			Customer customer= new Customer();
			customer.setCompanyName(record[0]);
			customer.setVtaNumber(Long.parseLong(record[1]));
			customer.setEmail(record[2]);
			customer.setPec(record[3]);
			customer.setPhoneNumber(record[4]);
			customerList.add(customer);
			
		}
		reader.close();
		return customerList;
	}

}
