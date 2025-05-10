package com.isfootball.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que almacena los datos básicos de un equipo.
 */
@Getter
@Setter
public class Team {
	
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
}