package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que almacena los datos de un jugador en una competición.
 */
public class PlayerCompetitionStatistics implements Serializable {

	private static final long serialVersionUID = 1465999267560170169L;

	private Player player;
	//Es necesario para poder usarla en "Competition".

	private Competition competition;

	private Team team;
	
	private Integer gamesAppearances;
	private Integer gamesLineups;
	private Integer gamesMinutes;
	private Integer position;
	private Double gamesRating;
	private boolean isCaptain;

	private Integer substitutesIn;
	private Integer substitutesOut;
	private Integer substitutesBench;

	private Integer totalShots;
	private Integer shotsOn;

	private Integer totalGoals;
	private Integer concededGoals;
	private Integer assists;
	private Integer saves;

	private Integer totalPasses;
	private Integer keyPasses;
	private Integer accuracyPasses;

	private Integer totalTackles;
	private Integer blocksTackles;
	private Integer interceptionsTackles;

	private Integer totalDuels;
	private Integer duelsWon;

	private Integer dribblesAttempts;
	private Integer dribblesSuccess;

	private Integer foulsDrawn;
	private Integer foulsCommitted;

	private Integer yellowCards;
	private Integer yellowRedCards;
	private Integer redCards;

	private Integer penaltiesWon;
	private Integer penaltiesCommited;
	private Integer penaltiesScored;
	private Integer penaltiesMissed;
	private Integer penaltiesSaved;
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Competition getCompetition() {
		return competition;
	}
	
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Team getTeam(){
		return this.team;
	}

	public void setTeam(Team team){
		this.team=team;
	}

	public Integer getGamesAppearances() {
		return gamesAppearances;
	}
	
	public void setGamesAppearances(Integer gamesAppearances) {
		this.gamesAppearances = gamesAppearances;
	}
	
	public Integer getGamesLineups() {
		return gamesLineups;
	}
	
	public void setGamesLineups(Integer gamesLineups) {
		this.gamesLineups = gamesLineups;
	}
	
	public Integer getGamesMinutes() {
		return gamesMinutes;
	}
	
	public void setGamesMinutes(Integer gamesMinutes) {
		this.gamesMinutes = gamesMinutes;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	public Double getGamesRating() {
		return gamesRating;
	}
	
	public void setGamesRating(Double gamesRating) {
		this.gamesRating = gamesRating;
	}
	
	public boolean isCaptain() {
		return isCaptain;
	}
	
	public void setCaptain(boolean isCaptain) {
		this.isCaptain = isCaptain;
	}
	
	public Integer getSubstitutesIn() {
		return substitutesIn;
	}
	
	public void setSubstitutesIn(Integer substitutesIn) {
		this.substitutesIn = substitutesIn;
	}
	
	public Integer getSubstitutesOut() {
		return substitutesOut;
	}
	
	public void setSubstitutesOut(Integer substitutesOut) {
		this.substitutesOut = substitutesOut;
	}
	
	public Integer getSubstitutesBench() {
		return substitutesBench;
	}
	
	public void setSubstitutesBench(Integer substitutesBench) {
		this.substitutesBench = substitutesBench;
	}
	
	public Integer getTotalShots() {
		return totalShots;
	}
	
	public void setTotalShots(Integer totalShots) {
		this.totalShots = totalShots;
	}
	
	public Integer getShotsOn() {
		return shotsOn;
	}
	
	public void setShotsOn(Integer shotsOn) {
		this.shotsOn = shotsOn;
	}
	
	public Integer getTotalGoals() {
		return totalGoals;
	}
	
	public void setTotalGoals(Integer totalGoals) {
		this.totalGoals = totalGoals;
	}
	
	public Integer getConcededGoals() {
		return concededGoals;
	}
	
	public void setConcededGoals(Integer concededGoals) {
		this.concededGoals = concededGoals;
	}
	
	public Integer getAssists() {
		return assists;
	}
	
	public void setAssists(Integer assists) {
		this.assists = assists;
	}
	
	public Integer getSaves() {
		return saves;
	}
	
	public void setSaves(Integer saves) {
		this.saves = saves;
	}
	
	public Integer getTotalPasses() {
		return totalPasses;
	}
	
	public void setTotalPasses(Integer totalPasses) {
		this.totalPasses = totalPasses;
	}
	
	public Integer getKeyPasses() {
		return keyPasses;
	}
	
	public void setKeyPasses(Integer keyPasses) {
		this.keyPasses = keyPasses;
	}
	
	public Integer getAccuracyPasses() {
		return accuracyPasses;
	}
	
	public void setAccuracyPasses(Integer accuracyPasses) {
		this.accuracyPasses = accuracyPasses;
	}
	
	public Integer getTotalTackles() {
		return totalTackles;
	}
	
	public void setTotalTackles(Integer totalTackles) {
		this.totalTackles = totalTackles;
	}
	
	public Integer getBlocksTackles() {
		return blocksTackles;
	}
	
	public void setBlocksTackles(Integer blocksTackles) {
		this.blocksTackles = blocksTackles;
	}
	
	public Integer getInterceptionsTackles() {
		return interceptionsTackles;
	}
	
	public void setInterceptionsTackles(Integer interceptionsTackles) {
		this.interceptionsTackles = interceptionsTackles;
	}
	
	public Integer getTotalDuels() {
		return totalDuels;
	}
	
	public void setTotalDuels(Integer totalDuels) {
		this.totalDuels = totalDuels;
	}
	
	public Integer getDuelsWon() {
		return duelsWon;
	}
	
	public void setDuelsWon(Integer duelsWon) {
		this.duelsWon = duelsWon;
	}
	public Integer getDribblesAttempts() {
		return dribblesAttempts;
	}
	
	public void setDribblesAttempts(Integer dribblesAttempts) {
		this.dribblesAttempts = dribblesAttempts;
	}
	
	public Integer getDribblesSuccess() {
		return dribblesSuccess;
	}
	
	public void setDribblesSuccess(Integer dribblesSuccess) {
		this.dribblesSuccess = dribblesSuccess;
	}
	
	public Integer getFoulsDrawn() {
		return foulsDrawn;
	}
	
	public void setFoulsDrawn(Integer foulsDrawn) {
		this.foulsDrawn = foulsDrawn;
	}
	
	public Integer getFoulsCommitted() {
		return foulsCommitted;
	}
	
	public void setFoulsCommitted(Integer foulsCommitted) {
		this.foulsCommitted = foulsCommitted;
	}
	
	public Integer getYellowCards() {
		return yellowCards;
	}
	
	public void setYellowCards(Integer yellowCards) {
		this.yellowCards = yellowCards;
	}
	
	public Integer getYellowRedCards() {
		return yellowRedCards;
	}
	
	public void setYellowRedCards(Integer yellowRedCards) {
		this.yellowRedCards = yellowRedCards;
	}
	
	public Integer getRedCards() {
		return redCards;
	}
	
	public void setRedCards(Integer redCards) {
		this.redCards = redCards;
	}
	
	public Integer getPenaltiesWon() {
		return penaltiesWon;
	}
	
	public void setPenaltiesWon(Integer penaltiesWon) {
		this.penaltiesWon = penaltiesWon;
	}
	
	public Integer getPenaltiesCommited() {
		return penaltiesCommited;
	}
	
	public void setPenaltiesCommited(Integer penaltiesCommited) {
		this.penaltiesCommited = penaltiesCommited;
	}
	
	public Integer getPenaltiesScored() {
		return penaltiesScored;
	}
	
	public void setPenaltiesScored(Integer penaltiesScored) {
		this.penaltiesScored = penaltiesScored;
	}
	
	public Integer getPenaltiesMissed() {
		return penaltiesMissed;
	}
	
	public void setPenaltiesMissed(Integer penaltiesMissed) {
		this.penaltiesMissed = penaltiesMissed;
	}
	
	public Integer getPenaltiesSaved() {
		return penaltiesSaved;
	}
	
	public void setPenaltiesSaved(Integer penaltiesSaved) {
		this.penaltiesSaved = penaltiesSaved;
	}
}
