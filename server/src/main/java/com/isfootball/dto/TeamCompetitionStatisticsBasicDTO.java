package com.isfootball.dto;

import java.io.Serializable;

import com.isfootball.model.Goal;
import com.isfootball.model.HomeAwayTotalStats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TeamCompetitionStatisticsBasicDTO implements Serializable{

    private static final long serialVersionUID = 604100695023158756L;

    private TeamBasicDTO team;
    
	private Integer rank;

    private Integer points;

    private Integer goalsDiff;

    private String group;

    private String form;

    private  HomeAwayTotalStats matchesPlayed;
	
	private  HomeAwayTotalStats matchesWon;
	private  HomeAwayTotalStats matchesDrawn;
	private  HomeAwayTotalStats matchesLost;

	private Goal goalsFor;
	private Goal goalsAgainst;
}
