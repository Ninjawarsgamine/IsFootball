package com.isfootball.mapper;

import com.isfootball.dto.PlayerCompetitionStatisticsDTO;
import com.isfootball.dto.PlayerCompetitionStatisticsBasicDTO;
import com.isfootball.model.PlayerCompetitionStatistics;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Este Mapper convierte PlayerCompetitionStatistics a sus respectivos DTOs.
 */
@Mapper(componentModel = "spring", uses = { PlayerMapper.class, TeamMapper.class, CompetitionMapper.class })
public interface PlayerCompetitionStatisticsMapper {
    
    /**
     * Mappea PlayerCompetitionStatistics a PlayerCompetitionStatisticsDTO
     * 
     * @param stats La entidad PlayerCompetitionStatistics que contiene los datos del jugador y la competencia.
     * @return Un objeto PlayerCompetitionStatisticsDTO con los datos del jugador y la competencia.
     */

    @Mapping(target = "team", source = "team")
    @Mapping(target = "competition", source = "competition")
    @Mapping(target = "gamesAppearences", source = "gamesAppearences")
    @Mapping(target = "gamesLineups", source = "gamesLineups")
    @Mapping(target = "gamesMinutes", source = "gamesMinutes")
    @Mapping(target = "position", source = "position")
    @Mapping(target = "gamesRating", source = "gamesRating")
    @Mapping(target = "substitutesIn", source = "substitutesIn")
    @Mapping(target = "substitutesOut", source = "substitutesOut")
    @Mapping(target = "substitutesBench", source = "substitutesBench")
    @Mapping(target = "totalShots", source = "totalShots")
    @Mapping(target = "shotsOn", source = "shotsOn")
    @Mapping(target = "totalGoals", source = "totalGoals")
    @Mapping(target = "concededGoals", source = "concededGoals")
    @Mapping(target = "assists", source = "assists")
    @Mapping(target = "saves", source = "saves")
    @Mapping(target = "totalPasses", source = "totalPasses")
    @Mapping(target = "keyPasses", source = "keyPasses")
    @Mapping(target = "accuracyPasses", source = "accuracyPasses")
    @Mapping(target = "totalTackles", source = "totalTackles")
    @Mapping(target = "blocksTackles", source = "blocksTackles")
    @Mapping(target = "interceptionsTackles", source = "interceptionsTackles")
    @Mapping(target = "totalDuels", source = "totalDuels")
    @Mapping(target = "duelsWon", source = "duelsWon")
    @Mapping(target = "dribblesAttempts", source = "dribblesAttempts")
    @Mapping(target = "dribblesSuccess", source = "dribblesSuccess")
    @Mapping(target = "foulsDrawn", source = "foulsDrawn")
    @Mapping(target = "foulsCommitted", source = "foulsCommitted")
    @Mapping(target = "yellowCards", source = "yellowCards")
    @Mapping(target = "yellowRedCards", source = "yellowRedCards")
    @Mapping(target = "redCards", source = "redCards")
    @Mapping(target = "penaltiesWon", source = "penaltiesWon")
    @Mapping(target = "penaltiesCommited", source = "penaltiesCommited")
    @Mapping(target = "penaltiesScored", source = "penaltiesScored")
    @Mapping(target = "penaltiesMissed", source = "penaltiesMissed")
    @Mapping(target = "penaltiesSaved", source = "penaltiesSaved")
    PlayerCompetitionStatisticsDTO toPlayerCompetitionStatisticsDTO(PlayerCompetitionStatistics stats);

    /**
     * Mappea PlayerCompetitionStatistics a PlayerCompetitionStatisticsBasicDTO
     * 
     * @param stats La entidad PlayerCompetitionStatistics que contiene los datos básicos del jugador y la competencia.
     * @return Un objeto PlayerCompetitionStatisticsBasicDTO con los datos básicos del jugador y la competencia.
     */
    @Mapping(target = "player", source = "player")
    @Mapping(target = "team", source = "team")
    @Mapping(target = "gamesAppearences", source = "gamesAppearences")
    @Mapping(target = "totalGoals", source = "totalGoals")
    @Mapping(target = "yellowCards", source = "yellowCards")
    @Mapping(target = "redCards", source = "redCards")
    PlayerCompetitionStatisticsBasicDTO toPlayerCompetitionStatisticsBasicDTO(PlayerCompetitionStatistics stats);

    /**
     * Mappea una lista de PlayerCompetitionStatistics a una lista de PlayerCompetitionStatisticsDTO.
     */
    List<PlayerCompetitionStatisticsBasicDTO> toPlayerCompetitionStatisticsBasicDTOList(List<PlayerCompetitionStatistics> statsList);
}
