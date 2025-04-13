package com.isfootball.model;

import java.io.Serializable;

/**
 * Esta es una clase que contiene los datos de una competición. Usamos "Serializable" para
 * que los objetos de la clase puedan ser almacenados en Redis.
 */
public class Competition implements Serializable {
	
	
	private Integer id;
	private String name;
	private String type;
	private String logo;
	private Country country;
	private Integer season;

	/**
	 * Constructor para crear una instancia de la competición.
	 *
	 * @param id      El identificador único de la competición.
	 * @param name    El nombre de la competición.
	 * @param type    El tipo de la competición (por ejemplo, liga, copa).
	 * @param logo    La URL del logo de la competición.
	 * @param country El país donde se lleva a cabo la competición.
	 * @param season  El año de la temporada de la competición.
	 */
	public Competition(Integer id, String name, String type, String logo, Country country, Integer season) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.logo = logo;
		this.country = country;
		this.season = season;
	}

	/**
	 * Constructor para crear una instancia de la competición vacía.
	 */
	public Competition(){
	
	}

	/**
	 * Obtiene el identificador de la competición.
	 *
	 * @return El identificador de la competición.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Establece el identificador de la competición.
	 *
	 * @param id El nuevo identificador de la competición.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de la competición.
	 *
	 * @return El nombre de la competición.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre de la competición.
	 *
	 * @param name El nuevo nombre de la competición.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el tipo de la competición (liga o copa).
	 *
	 * @return El tipo de la competición.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Establece el tipo de la competición.
	 *
	 * @param type El nuevo tipo de la competición (por ejemplo, liga o copa).
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Obtiene el logo de la competición.
	 *
	 * @return La URL del logo de la competición.
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * Establece la URL del logo de la competición.
	 *
	 * @param logo La nueva URL del logo.
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * Obtiene el objeto "Country" asociado a la competición.
	 *
	 * @return El objeto "Country" de la competición.
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Establece el país de la competición.
	 *
	 * @param country El nuevo país asociado a la competición.
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Obtiene el año de la temporada de la competición.
	 *
	 * @return El año de la temporada.
	 */
	public Integer getSeason() {
		return season;
	}

	/**
	 * Establece el año de la temporada de la competición.
	 *
	 * @param season El nuevo año de la temporada.
	 */
	public void setSeason(Integer season) {
		this.season = season;
	}
}