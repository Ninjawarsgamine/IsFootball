package com.isfootball.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la limitar los campos de "PlayerCompetitionStatistics".
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerCompetitionStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -8829795723104408731L;

	private CompetitionBasicDTO competition;

    private TeamBasicDTO team;

    private Integer gamesAppearences;
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
}
