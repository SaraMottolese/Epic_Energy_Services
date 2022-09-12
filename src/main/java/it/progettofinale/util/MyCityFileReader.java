package it.progettofinale.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import it.progettofinale.model.City;
import lombok.extern.slf4j.Slf4j;

public class MyCityFileReader {
	
	public static List<City> read(FileReader file) throws IOException{
		CSVReader reader = new CSVReader(file, ';', '\'', 1);
		List<City> cities = new ArrayList<>();
		String [] record = null;
		while((record= reader.readNext())!=null) {
			City city= new City();
			city.setCountyCode(Long.parseLong(record[0]));
			city.setName(record[2]);
			city.setProgressiveCode(Long.parseLong(record[1]));
			city.setCountyName(record[3]);
			cities.add(city);
		}
		reader.close();
		return cities;
	}

}
