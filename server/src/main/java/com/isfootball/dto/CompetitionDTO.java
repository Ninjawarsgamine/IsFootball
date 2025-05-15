package com.isfootball.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la limitar los campos de "Competition".
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompetitionDTO implements Serializable{
	
    private static final long serialVersionUID = -2907463370814944177L;
	
	private Integer id;
	private String name;
	private String type;
	private String logo;
	private CountryDTO country;
    private List<TeamCompetitionStatisticsBasicDTO> teamsCompetitionStatistics;
}
