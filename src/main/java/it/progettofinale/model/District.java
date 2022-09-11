package it.progettofinale.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String abbreviation;
	String name;
	String region;

}
