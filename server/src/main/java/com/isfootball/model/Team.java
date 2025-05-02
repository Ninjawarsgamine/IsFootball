package com.isfootball.model;

import java.io.Serializable;
import java.util.List;

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
	private Venue venue;

	private List<Competition>competitions;
	private List<TeamCompetitionStatistics>teamCompetitionStatistics;

	private Coach coach;

	private List<Player>players;

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
	
	public Venue getVenue() {
		return venue;
	}
	
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	public List<Competition> getCompetitions() {
		return competitions;
	}
	
	public void setCompetitions(List<Competition> competitions) {
		this.competitions = competitions;
	}
	
	public List<TeamCompetitionStatistics> getTeamCompetitionStatistics() {
		return teamCompetitionStatistics;
	}
	
	public void setTeamCompetitionStatistics(List<TeamCompetitionStatistics> teamCompetitionStatistics) {
		this.teamCompetitionStatistics = teamCompetitionStatistics;
	}

	public Coach getCoach() {
		return coach;
	}
	
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}