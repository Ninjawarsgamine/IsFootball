package com.isfootball.model;

import java.io.Serializable;
/**
 * Clase que almacena los datos de un jugador.
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = -8973618725339650477L;

	private Integer id;
	private String name;
	private String position;
	private Country nationality;
	private Integer age;
	private String birthday;
	private Integer height;
	private Integer weight;
	private String photo;
	private Boolean isInjured;
	
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
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public Country getNationality() {
		return nationality;
	}
	
	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Boolean getIsInjured() {
		return isInjured;
	}
	
	public void setIsInjured(Boolean isInjured) {
		this.isInjured = isInjured;
	}
	
}
