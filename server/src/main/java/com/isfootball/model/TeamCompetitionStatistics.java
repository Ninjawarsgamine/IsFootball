package com.isfootball.model;

import java.io.Serializable;

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
	private String form;
	
	private MatchesStatistics all;
	private MatchesStatistics home;
	private MatchesStatistics away;

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

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public MatchesStatistics getAll() {
		return all;
	}

	public void setAll(MatchesStatistics all) {
		this.all = all;
	}

	public MatchesStatistics getHome() {
		return home;
	}

	public void setHome(MatchesStatistics home) {
		this.home = home;
	}

	public MatchesStatistics getAway() {
		return away;
	}

	public void setAway(MatchesStatistics away) {
		this.away = away;
	}

	/**
	 * Clase interna que representa la información básica de los partidos.
	 */
	public static class MatchesStatistics implements Serializable {
		
		private static final long serialVersionUID = -944765279883568006L;
		
		private Integer matchesPlayed;
		private Integer matchesWon;
		private Integer matchesDrawn;
		private Integer matchesLost;
		private Integer goalsFor;
		private Integer goalsAgainst;

		public Integer getMatchesPlayed() {
			return matchesPlayed;
		}

		public void setMatchesPlayed(Integer matchesPlayed) {
			this.matchesPlayed = matchesPlayed;
		}

		public Integer getMatchesWon() {
			return matchesWon;
		}

		public void setMatchesWon(Integer matchesWon) {
			this.matchesWon = matchesWon;
		}

		public Integer getMatchesDrawn() {
			return matchesDrawn;
		}

		public void setMatchesDrawn(Integer matchesDrawn) {
			this.matchesDrawn = matchesDrawn;
		}

		public Integer getMatchesLost() {
			return matchesLost;
		}

		public void setMatchesLost(Integer matchesLost) {
			this.matchesLost = matchesLost;
		}

		public Integer getGoalsFor() {
			return goalsFor;
		}

		public void setGoalsFor(Integer goalsFor) {
			this.goalsFor = goalsFor;
		}

		public Integer getGoalsAgainst() {
			return goalsAgainst;
		}

		public void setGoalsAgainst(Integer goalsAgainst) {
			this.goalsAgainst = goalsAgainst;
		}
	}
}

