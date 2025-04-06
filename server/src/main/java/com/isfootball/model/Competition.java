package com.isfootball.model;

/**
 * Esta es una clase que contiene los datos de una competición.
 */
public class Competition {
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
	 * Obtiene el identificador de la competición.
	 * 
	 * @return El identificador de la competición.
	 */
	public Integer getId() {
		return id;
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
	 * Obtiene el tipo de la competición (liga o copa).
	 *
	 * @return El tipo de la competición.
	 */
	public String getType() {
		return type;
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
	 * Obtiene el objeto "Country" asociado a la competición.
	 *
	 * @return El objeto "Country" de la competición.
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Obtiene el año de la temporada de la competición.
	 *
	 * @return El año de la temporada.
	 */
	public Integer getSeason() {
		return season;
	}

}
