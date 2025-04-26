package com.isfootball.model;

import java.io.Serializable;

public class Match implements Serializable{

    private static final long serialVersionUID = 4404468095495224185L;
	private Integer id;
    private String referee;
    private String date;

    private Competition competition;
    private String competitionRound;
    private String timeZoneId;
    
    private Team teamHome;
    private Team TeamAway;

    private Integer goalsHome;
    private Integer goalsAway;

    private String matchLong;
    private Integer elapsed;
    private String matchShort;
    private Venue venue;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReferee() {
		return referee;
	}
	public void setReferee(String referee) {
		this.referee = referee;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Competition getCompetition() {
		return competition;
	}
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	public String getCompetitionRound() {
		return competitionRound;
	}
	public void setCompetitionRound(String competitionRound) {
		this.competitionRound = competitionRound;
	}
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	public Team getTeamHome() {
		return teamHome;
	}
	public void setTeamHome(Team teamHome) {
		this.teamHome = teamHome;
	}
	public Team getTeamAway() {
		return TeamAway;
	}
	public void setTeamAway(Team teamAway) {
		TeamAway = teamAway;
	}
	public Integer getGoalsHome() {
		return goalsHome;
	}
	public void setGoalsHome(Integer goalsHome) {
		this.goalsHome = goalsHome;
	}
	public Integer getGoalsAway() {
		return goalsAway;
	}
	public void setGoalsAway(Integer goalsAway) {
		this.goalsAway = goalsAway;
	}
	public String getMatchLong() {
		return matchLong;
	}
	public void setMatchLong(String matchLong) {
		this.matchLong = matchLong;
	}
	public Integer getElapsed() {
		return elapsed;
	}
	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}
	public String getMatchShort() {
		return matchShort;
	}
	public void setMatchShort(String matchShort) {
		this.matchShort = matchShort;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
    //TimeZone timeZone=TimeZone.getDefault(); System.out.println(timeZone.getID());
}
