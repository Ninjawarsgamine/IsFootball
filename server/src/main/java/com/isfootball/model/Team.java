package com.isfootball.model;

import java.io.Serializable;
/**
 * Clase que almacena los datos básicos de un equipo.
 */
public class Team implements Serializable  {

	private static final long serialVersionUID = 8487498115745206707L;
	
	private Integer id;
	private String name;
	private String logo;
	private String code;
	private Country country;
	private Integer founded;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Integer getFounded() {
		return founded;
	}
	
	public void setFounded(Integer founded) {
		this.founded = founded;
	}
	
}