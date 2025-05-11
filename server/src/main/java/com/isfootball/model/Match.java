package com.isfootball.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Match {

	private Integer id;

    private String date;
    
    private Competition competition;
    private String competitionRound;

    private Team teamHome;
    private Team teamAway;

    private Integer goalsHome;
    private Integer goalsAway;

    private Integer penaltiesHome;
    private Integer penaltiesAway;

    private String matchLong;
    private Integer elapsed;
}
