package com.isfootball.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Esta clase representa las estadísticas de un equipo dentro de una competición específica,
 * como una liga o un torneo tipo copa.
 */
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

	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Integer getRank() {
		return rank;
	}
	
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public Integer getGoalsDiff() {
		return goalsDiff;
	}
	
	public void setGoalsDiff(Integer goalsDiff) {
		this.goalsDiff = goalsDiff;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getForm() {
		return form;
	}
	
	public void setForm(String form) {
		this.form = form;
	}
	
	public HomeAwayTotalStats getMatchesPlayed() {
		return matchesPlayed;
	}
	
	public void setMatchesPlayed(HomeAwayTotalStats matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}
	
	public HomeAwayTotalStats getMatchesWon() {
		return matchesWon;
	}
	
	public void setMatchesWon(HomeAwayTotalStats matchesWon) {
		this.matchesWon = matchesWon;
	}
	
	public HomeAwayTotalStats getMatchesDrawn() {
		return matchesDrawn;
	}
	
	public void setMatchesDrawn(HomeAwayTotalStats matchesDrawn) {
		this.matchesDrawn = matchesDrawn;
	}
	
	public HomeAwayTotalStats getMatchesLost() {
		return matchesLost;
	}
	
	public void setMatchesLost(HomeAwayTotalStats matchesLost) {
		this.matchesLost = matchesLost;
	}
	
	public Goal getGoalsFor() {
		return goalsFor;
	}
	
	public void setGoalsFor(Goal goalsFor) {
		this.goalsFor = goalsFor;
	}
	
	public Goal getGoalsAgainst() {
		return goalsAgainst;
	}
	
	public void setGoalsAgainst(Goal goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
	
	public Integer getBiggestStreakWins() {
		return biggestStreakWins;
	}
	
	public void setBiggestStreakWins(Integer biggestStreakWins) {
		this.biggestStreakWins = biggestStreakWins;
	}
	
	public Integer getBiggestStreakDraws() {
		return biggestStreakDraws;
	}
	
	public void setBiggestStreakDraws(Integer biggestStreakDraws) {
		this.biggestStreakDraws = biggestStreakDraws;
	}
	
	public Integer getBiggestStreakLoses() {
		return biggestStreakLoses;
	}
	
	public void setBiggestStreakLoses(Integer biggestSreakLoses) {
		this.biggestStreakLoses = biggestSreakLoses;
	}
	
	public HomeAwayStats getBiggestWins() {
		return biggestWins;
	}
	
	public void setBiggestWins(HomeAwayStats biggestWins) {
		this.biggestWins = biggestWins;
	}
	
	public HomeAwayStats getBiggestLoses() {
		return biggestLoses;
	}
	
	public void setBiggestLoses(HomeAwayStats biggestLoses) {
		this.biggestLoses = biggestLoses;
	}
	
	public HomeAwayStats getBiggestGoalsFor() {
		return biggestGoalsFor;
	}
	
	public void setBiggestGoalsFor(HomeAwayStats biggestGoalsFor) {
		this.biggestGoalsFor = biggestGoalsFor;
	}
	
	public HomeAwayStats getBiggestGoalsAgainst() {
		return biggestGoalsAgainst;
	}
	
	public void setBiggestGoalsAgainst(HomeAwayStats biggestGoalsAgainst) {
		this.biggestGoalsAgainst = biggestGoalsAgainst;
	}
	
	public HomeAwayTotalStats getCleanSheet() {
		return cleanSheet;
	}
	
	public void setCleanSheet(HomeAwayTotalStats cleanSheet) {
		this.cleanSheet = cleanSheet;
	}
	
	public HomeAwayTotalStats getFailedToScore() {
		return failedToScore;
	}
	
	public void setFailedToScore(HomeAwayTotalStats failedToScore) {
		this.failedToScore = failedToScore;
	}
	
	public Integer getTotalPenalties() {
		return totalPenalties;
	}
	
	public void setTotalPenalties(Integer totalPenalties) {
		this.totalPenalties = totalPenalties;
	}
	
	public Stat getPenaltiesScored() {
		return penaltiesScored;
	}
	
	public void setPenaltiesScored(Stat penaltiesScored) {
		this.penaltiesScored = penaltiesScored;
	}
	
	public Stat getPenaltiesMissed() {
		return penaltiesMissed;
	}
	
	public void setPenaltiesMissed(Stat penaltiesMissed) {
		this.penaltiesMissed = penaltiesMissed;
	}

	public List<Lineup> getLineups() {
		return lineups;
	}
	
	public void setLineups(List<Lineup> lineups) {
		this.lineups = lineups;
	}
	
	public Map<String, Stat> getCardsYellow() {
		return cardsYellow;
	}
	
	public void setCardsYellow(Map<String, Stat> cardsYellow) {
		this.cardsYellow = cardsYellow;
	}
	
	public Map<String, Stat> getCardsRed() {
		return cardsRed;
	}
	
	public void setCardsRed(Map<String, Stat> cardsRed) {
		this.cardsRed = cardsRed;
	}
}

