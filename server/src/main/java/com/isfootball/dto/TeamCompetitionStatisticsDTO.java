package com.isfootball.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.isfootball.model.Goal;
import com.isfootball.model.HomeAwayStats;
import com.isfootball.model.HomeAwayTotalStats;
import com.isfootball.model.Lineup;
import com.isfootball.model.Stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la limitar los campos de "TeamCompetitionStatistics".
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamCompetitionStatisticsDTO implements Serializable{

	private static final long serialVersionUID = 433973492947696804L;

	private  HomeAwayTotalStats matchesPlayed;
	
	private  HomeAwayTotalStats matchesWon;
	private  HomeAwayTotalStats matchesDrawn;
	private  HomeAwayTotalStats matchesLost;

	private Goal goalsFor;
	private Goal goalsAgainst;


	private Integer biggestStreakWins;
	private Integer biggestStreakDraws;
	private Integer biggestStreakLoses;

	private HomeAwayStats biggestWins;
	private HomeAwayStats biggestLoses;

	private HomeAwayStats biggestGoalsFor;
	private HomeAwayStats biggestGoalsAgainst;

	private HomeAwayTotalStats cleanSheet;

	private HomeAwayTotalStats failedToScore;

	private Integer totalPenalties;
	private Stat penaltiesScored;
	private Stat penaltiesMissed;

	private List<Lineup> lineups;
	
	private Map<String, Stat> cardsYellow;

	private Map<String, Stat> cardsRed;
}
