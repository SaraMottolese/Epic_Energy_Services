package it.progettofinale.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.progettofinale.model.City;
import it.progettofinale.service.CityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CityDbLoader implements CommandLineRunner{
	
	@Autowired
	private CityService cityService;
	
	@Value("${progettofinale.citypath}")
	private String cityPath;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("caricamento dei dati dal file comuni-italiani.csv");
		FileReader fileReader= new FileReader(cityPath);
		try {
		List<City> cities= new ArrayList<>();
		cities= MyCityFileReader.read(fileReader);
		for (City c: cities)
			cityService.save(c);
		}catch (FileNotFoundException e) {
			log.error("file al path " + cityPath + " non trovato");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException");
			e.printStackTrace();
		}
		log.info("caricamento dei comuni italiani completato");
	}

}
