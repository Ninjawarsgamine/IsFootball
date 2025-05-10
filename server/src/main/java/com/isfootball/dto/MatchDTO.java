package com.isfootball.dto;

import java.io.Serializable;

import com.isfootball.model.Venue;

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
    private String referee;
    private String date;
    private CompetitionBasicDTO competition;
    private String competitionRound;
    private String timeZoneId;
    
    private TeamBasicDTO teamHome;
    private TeamBasicDTO TeamAway;

    private Integer goalsHome;
    private Integer goalsAway;

    private String matchLong;
    private Integer elapsed;
    private String matchShort;

    private Venue venue;
}
