package com.isfootball.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase representa las estadísticas de un equipo dentro de una competición específica,
 * como una liga o un torneo tipo copa.
 */
@Getter
@Setter
public class TeamCompetitionStatistics implements Serializable {
	
	private static final long serialVersionUID = -363856162064710463L;
	
	private Team team;

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

