package com.isfootball.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la limitar los campos de "Player".
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerDTO implements Serializable{

    private static final long serialVersionUID = -4126731854358726995L;
	
	private Integer id;
	private String name;

	private String firstname;
	private String lastname;

	private String position;
	private TeamBasicDTO playerTeam;

	private CountryDTO nationality;
	private Integer age;
	private String birthday;
	private String height;
	private String weight;
	private String photo;
	private Boolean isInjured;
	private List<PlayerCompetitionStatisticsDTO>playerCompetitionStatistics;
}
