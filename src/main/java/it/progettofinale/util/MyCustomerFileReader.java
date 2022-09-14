package it.progettofinale.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import it.progettofinale.model.Customer;

public class MyCustomerFileReader {
	
	public static List<Customer> read(FileReader file) throws IOException{
		CSVReader reader = new CSVReader(file, ',','\'',1);
		List<Customer> customerList= new ArrayList<>();
		String[] record = null;
		while((record= reader.readNext())!=null) {
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
