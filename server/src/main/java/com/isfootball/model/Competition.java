package com.isfootball.model;

import java.io.Serializable;
import java.util.List;

/**
 * Esta es una clase que contiene los datos de una competición. Usamos "Serializable" para
 * que los objetos de la clase puedan ser almacenados en Redis.
 */
public class Competition implements Serializable {

	private static final long serialVersionUID = -8398489205371161696L;
	
	private Integer id;
	private String name;
	private String type;
	private String logo;
	private Country country;
	private Integer season;
	private List<TeamCompetitionStatistics> teamsCompetitionStatistics;
	
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
		if(id==null) {
			throw new IllegalArgumentException("The competition id is null.");
		}
		if(!(id instanceof Integer)) {
			throw new IllegalArgumentException("The competition id is not a Integer.");
		}
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
		if(name==null) {
			throw new IllegalArgumentException("The competition name is null.");
		}
		if(name.length()<3) {
			throw new IllegalArgumentException("The competition name must have at least 3 characters.");
		}
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
	
	/**
	 * Obtiene la lista de estadísticas de los equipos en la competición.
	 * 
	 * @return La lista de estadísticas de los equipos en la competición.
	 */
	public List<TeamCompetitionStatistics> getTeamsCompetitionStatistics() {
		return teamsCompetitionStatistics;
	}
	
	/**
	 * Establece la lista de estadísticas de los equipos en la competición.
	 * 
	 * @param teamsCompetitionStatistics La lista de estadísticas de los equipos en la competición.
	 */
	public void setTeamsCompetitionStatistics(List<TeamCompetitionStatistics> teamsCompetitionStatistics) {
		this.teamsCompetitionStatistics = teamsCompetitionStatistics;
	}
}