package com.isfootball.model;

import java.io.Serializable;
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

	private MatchesTeamStatistics matchesPlayed;
	
	private  MatchesTeamStatistics matchesWon;
	private  MatchesTeamStatistics matchesDrawn;
	private  MatchesTeamStatistics matchesLost;

	private Goal goalsFor;
	private Goal goalsAgainst;


	private Integer biggestStreakWins;
	private Integer biggestStreakDraws;
	private Integer biggestSreakLoses;

	private Integer biggestWinsHome;
	private Integer biggestWinsAway;
	private Integer biggestLosesHome;
	private Integer biggestLosesAway;

	private Integer biggestGoalsForHome;
	private Integer biggestGoalsForAway;
	private Integer biggestGoalsAgainstHome;
	private Integer biggestGoalsAgainstAway;

	private Integer cleanSheetHome;
	private Integer cleanSheetAway;
	private Integer cleanSheetTotal;

	private Integer failedToScoreHome;
	private Integer failedToScoreAway;
	private Integer failedToScoreTotal;

	private Integer totalPenalties;
	private Stat penaltiesScored;
	private Stat penaltiesMissed;

	private Lineup[] lineups;
	
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
	
	public MatchesTeamStatistics getMatchesPlayed() {
		return matchesPlayed;
	}
	
	public void setMatchesPlayed(MatchesTeamStatistics matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}
	
	public MatchesTeamStatistics getMatchesWon() {
		return matchesWon;
	}
	
	public void setMatchesWon(MatchesTeamStatistics matchesWon) {
		this.matchesWon = matchesWon;
	}
	
	public MatchesTeamStatistics getMatchesDrawn() {
		return matchesDrawn;
	}
	
	public void setMatchesDrawn(MatchesTeamStatistics matchesDrawn) {
		this.matchesDrawn = matchesDrawn;
	}
	
	public MatchesTeamStatistics getMatchesLost() {
		return matchesLost;
	}
	
	public void setMatchesLost(MatchesTeamStatistics matchesLost) {
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
	
	public Integer getBiggestSreakLoses() {
		return biggestSreakLoses;
	}
	
	public void setBiggestSreakLoses(Integer biggestSreakLoses) {
		this.biggestSreakLoses = biggestSreakLoses;
	}
	
	public Integer getBiggestWinsHome() {
		return biggestWinsHome;
	}
	
	public void setBiggestWinsHome(Integer biggestWinsHome) {
		this.biggestWinsHome = biggestWinsHome;
	}
	
	public Integer getBiggestWinsAway() {
		return biggestWinsAway;
	}
	
	public void setBiggestWinsAway(Integer biggestWinsAway) {
		this.biggestWinsAway = biggestWinsAway;
	}
	
	public Integer getBiggestLosesHome() {
		return biggestLosesHome;
	}
	
	public void setBiggestLosesHome(Integer biggestLosesHome) {
		this.biggestLosesHome = biggestLosesHome;
	}
	
	public Integer getBiggestLosesAway() {
		return biggestLosesAway;
	}
	
	public void setBiggestLosesAway(Integer biggestLosesAway) {
		this.biggestLosesAway = biggestLosesAway;
	}
	
	public Integer getBiggestGoalsForHome() {
		return biggestGoalsForHome;
	}
	
	public void setBiggestGoalsForHome(Integer biggestGoalsForHome) {
		this.biggestGoalsForHome = biggestGoalsForHome;
	}
	
	public Integer getBiggestGoalsForAway() {
		return biggestGoalsForAway;
	}
	
	public void setBiggestGoalsForAway(Integer biggestGoalsForAway) {
		this.biggestGoalsForAway = biggestGoalsForAway;
	}
	
	public Integer getBiggestGoalsAgainstHome() {
		return biggestGoalsAgainstHome;
	}
	
	public void setBiggestGoalsAgainstHome(Integer biggestGoalsAgainstHome) {
		this.biggestGoalsAgainstHome = biggestGoalsAgainstHome;
	}
	
	public Integer getBiggestGoalsAgainstAway() {
		return biggestGoalsAgainstAway;
	}
	
	public void setBiggestGoalsAgainstAway(Integer biggestGoalsAgainstAway) {
		this.biggestGoalsAgainstAway = biggestGoalsAgainstAway;
	}
	
	public Integer getCleanSheetHome() {
		return cleanSheetHome;
	}
	
	public void setCleanSheetHome(Integer cleanSheetHome) {
		this.cleanSheetHome = cleanSheetHome;
	}
	
	public Integer getCleanSheetAway() {
		return cleanSheetAway;
	}
	
	public void setCleanSheetAway(Integer cleanSheetAway) {
		this.cleanSheetAway = cleanSheetAway;
	}
	
	public Integer getCleanSheetTotal() {
		return cleanSheetTotal;
	}
	
	public void setCleanSheetTotal(Integer cleanSheetTotal) {
		this.cleanSheetTotal = cleanSheetTotal;
	}
	
	public Integer getFailedToScoreHome() {
		return failedToScoreHome;
	}
	
	public void setFailedToScoreHome(Integer failedToScoreHome) {
		this.failedToScoreHome = failedToScoreHome;
	}
	
	public Integer getFailedToScoreAway() {
		return failedToScoreAway;
	}
	
	public void setFailedToScoreAway(Integer failedToScoreAway) {
		this.failedToScoreAway = failedToScoreAway;
	}
	
	public Integer getFailedToScoreTotal() {
		return failedToScoreTotal;
	}
	
	public void setFailedToScoreTotal(Integer failedToScoreTotal) {
		this.failedToScoreTotal = failedToScoreTotal;
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
	
	public Lineup[] getLineups() {
		return lineups;
	}
	
	public void setLineups(Lineup[] lineups) {
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

