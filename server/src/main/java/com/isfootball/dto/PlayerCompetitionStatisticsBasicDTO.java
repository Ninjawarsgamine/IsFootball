package com.isfootball.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la limitar los campos de las estadísticas de un jugador en una competición
 * a los más básicos.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerCompetitionStatisticsBasicDTO implements Serializable {

    private static final long serialVersionUID = 2599606625378085308L;
    
	private PlayerBasicDTO player;
    private TeamBasicDTO team;
    private Integer gamesAppearences;
    private Integer totalGoals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
}
