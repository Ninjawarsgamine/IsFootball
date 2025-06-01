package com.isfootball.mapper;

import com.isfootball.dto.TeamCompetitionStatisticsBasicDTO;
import com.isfootball.dto.TeamCompetitionStatisticsDTO;
import com.isfootball.model.TeamCompetitionStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Este mapper convierte TeamCompetitionStatistics a sus respectivos DTOs.
 */
@Mapper(componentModel = "spring")
public interface TeamCompetitionStatisticsMapper {

    /**
     * Mappea TeamCompetitionStatistics a TeamCompetitionStatisticsBasicDTO.
     *
     * @param stats la entidad TeamCompetitionStatistics que se desea mappear.
     * @return TeamCompetitionStatisticsBasicDTO con los datos básicos de estadísticas por equipo.
     */
    @Mappings({
        @Mapping(source = "team", target = "team"),
        @Mapping(source = "rank", target = "rank"),
        @Mapping(source = "points", target = "points"),
        @Mapping(source = "goalsDiff", target = "goalsDiff"),
        @Mapping(source = "group", target = "group"),
        @Mapping(source = "form", target = "form"),
        @Mapping(source = "matchesPlayed", target = "matchesPlayed"),
        @Mapping(source = "matchesWon", target = "matchesWon"),
        @Mapping(source = "matchesDrawn", target = "matchesDrawn"),
        @Mapping(source = "matchesLost", target = "matchesLost"),
        @Mapping(source = "goalsFor", target = "goalsFor"),
        @Mapping(source = "goalsAgainst", target = "goalsAgainst")
    })
    TeamCompetitionStatisticsBasicDTO toTeamCompetitionStatisticsBasicDTO(TeamCompetitionStatistics stats);

    /**
     * Mappea una lista de TeamCompetitionStatistics a una lista de TeamCompetitionStatisticsBasicDTO.
     *
     * @param statsList Lista de entidades TeamCompetitionStatistics.
     * @return Lista de TeamCompetitionStatisticsBasicDTO.
     */
    List<TeamCompetitionStatisticsBasicDTO> toTeamCompetitionStatisticsBasicDTOList(List<TeamCompetitionStatistics> statsList);

    /**
     * Mappea TeamCompetitionStatistics a TeamCompetitionStatisticsDTO.
     *
     * @param stats La entidad TeamCompetitionStatistics que se desea mappear.
     * @return TeamCompetitionStatisticsDTO con todos los campos detallados.
     */
    @Mappings({
        @Mapping(source = "matchesPlayed", target = "matchesPlayed"),
        @Mapping(source = "matchesWon", target = "matchesWon"),
        @Mapping(source = "matchesDrawn", target = "matchesDrawn"),
        @Mapping(source = "matchesLost", target = "matchesLost"),
        @Mapping(source = "goalsFor", target = "goalsFor"),
        @Mapping(source = "goalsAgainst", target = "goalsAgainst"),
        @Mapping(source = "biggestStreakWins", target = "biggestStreakWins"),
        @Mapping(source = "biggestStreakDraws", target = "biggestStreakDraws"),
        @Mapping(source = "biggestStreakLoses", target = "biggestStreakLoses"),
        @Mapping(source = "biggestWins", target = "biggestWins"),
        @Mapping(source = "biggestLoses", target = "biggestLoses"),
        @Mapping(source = "biggestGoalsFor", target = "biggestGoalsFor"),
        @Mapping(source = "biggestGoalsAgainst", target = "biggestGoalsAgainst"),
        @Mapping(source = "cleanSheet", target = "cleanSheet"),
        @Mapping(source = "failedToScore", target = "failedToScore"),
        @Mapping(source = "totalPenalties", target = "totalPenalties"),
        @Mapping(source = "penaltiesScored", target = "penaltiesScored"),
        @Mapping(source = "penaltiesMissed", target = "penaltiesMissed"),
        @Mapping(source = "lineups", target = "lineups"),
        @Mapping(source = "cardsYellow", target = "cardsYellow"),
        @Mapping(source = "cardsRed", target = "cardsRed")
    })
    TeamCompetitionStatisticsDTO toTeamCompetitionStatisticsDTO(TeamCompetitionStatistics stats);

    /**
     * Mappea una lista de TeamCompetitionStatistics a una lista de TeamCompetitionStatisticsDTO.
     *
     * @param statsList Lista de entidades TeamCompetitionStatistics.
     * @return Lista de TeamCompetitionStatisticsDTO con todos los campos detallados.
     */
    List<TeamCompetitionStatisticsDTO> toTeamCompetitionStatisticsDTOList(List<TeamCompetitionStatistics> statsList);
}
