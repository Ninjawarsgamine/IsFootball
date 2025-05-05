package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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
}
