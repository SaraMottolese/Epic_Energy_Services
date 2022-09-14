package it.progettofinale.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opencsv.CSVReader;

import it.progettofinale.model.County;
import it.progettofinale.service.CountyService;

public class MyCountyFileReader {
	
	
	public static List<County> read(FileReader file) throws IOException{
		CSVReader reader = new CSVReader(file, ',', '\'', 1);
		List<County> counties= new ArrayList<>();
		String [] record = null;
		while ((record = reader.readNext())!= null) {
			County county = new County();
			county.setAbbreviation(record[0]);
			county.setName(record[1]);
			county.setRegion(record[2]);
			counties.add(county);
		}
		reader.close();
		return counties;
		
	}

}
