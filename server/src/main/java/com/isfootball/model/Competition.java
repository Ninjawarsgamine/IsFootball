package com.isfootball.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta es una clase que contiene los datos de una competición. Usamos "Serializable" para
 * que los objetos de la clase puedan ser almacenados en Redis.
 */
@Getter
@Setter
public class Competition {
	
	private Integer id;
	private String name;
	private String type;
	private String logo;
	private Country country;
	private Integer season;
	private List<TeamCompetitionStatistics> teamsCompetitionStatistics;
}