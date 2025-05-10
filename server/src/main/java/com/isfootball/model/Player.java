package com.isfootball.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que almacena los datos de un jugador.
 */

@Getter
@Setter
public class Player {

	private Integer id;
	private String name;

	private String firstname;
	private String lastname;

	private String position;
	private Team playerTeam;

	private Country nationality;
	private Integer age;
	private String birthday;
	private String height;
	private String weight;
	private String photo;
	private Boolean isInjured;

	private List<PlayerCompetitionStatistics>playerCompetitionStatistics;

}
