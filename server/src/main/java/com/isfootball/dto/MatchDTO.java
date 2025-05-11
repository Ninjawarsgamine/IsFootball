package com.isfootball.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatchDTO implements Serializable {

    private static final long serialVersionUID = 4404468095495224185L;
    
	private Integer id;
    private String date;
    private CompetitionBasicDTO competition;
    private String competitionRound;
    
    private TeamBasicDTO teamHome;
    private TeamBasicDTO teamAway;

    private Integer goalsHome;
    private Integer goalsAway;

    private Integer penaltiesHome;
    private Integer penaltiesAway;

    private String matchLong;
    private Integer elapsed;
}