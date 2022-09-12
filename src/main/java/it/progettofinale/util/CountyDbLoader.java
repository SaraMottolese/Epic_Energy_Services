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

import it.progettofinale.model.County;
import it.progettofinale.service.CountyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountyDbLoader implements CommandLineRunner{
	
	@Autowired
	private CountyService countyService;
	
	@Value("${progettofinale.countypath}")
	private String countyPath;

	@Override
	public void run(String... args) throws Exception {
		log.info("caricamento dei dati dal file province-italiane.csv");
		FileReader fileReader= new FileReader(countyPath);
		try {
		List<County> counties= new ArrayList<>();
		counties= MyCountyFileReader.read(fileReader);
		for (County c: counties)
			countyService.save(c);
		}catch (FileNotFoundException e) {
			log.error("file al path " + countyPath + " non trovato");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException");
			e.printStackTrace();
		}
		log.info("caricamento delle province italiane completato");
	}

}
