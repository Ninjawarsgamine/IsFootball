package com.isfootball.mapper;

import com.isfootball.dto.MatchDTO;
import com.isfootball.model.Match;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Este mapper convierte Match a su respectivo DTO.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class, TeamMapper.class})
public interface MatchMapper {

    /**
     * Mappea Match a MatchDTO.
     *
     * @param match La entidad Match que contiene la información del partido.
     * @return MatchDTO con los datos del partido.
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "date", target = "date"),
        @Mapping(source = "competition", target = "competition"),
        @Mapping(source = "competitionRound", target = "competitionRound"),
        @Mapping(source = "teamHome", target = "teamHome"),
        @Mapping(source = "teamAway", target = "teamAway"),
        @Mapping(source = "goalsHome", target = "goalsHome"),
        @Mapping(source = "goalsAway", target = "goalsAway"),
        @Mapping(source = "penaltiesHome", target = "penaltiesHome"),
        @Mapping(source = "penaltiesAway", target = "penaltiesAway"),
        @Mapping(source = "matchLong", target = "matchLong"),
        @Mapping(source = "elapsed", target = "elapsed")
    })
    MatchDTO toMatchDTO(Match match);

    /**
     * Mappea una lista de Match a una lista de MatchDTO.
     *
     * @param matches la lista de entidades Match
     * @return una lista de MatchDTO con los datos de los partidos.
     */
    List<MatchDTO> toMatchDTOList(List<Match> matches);
}
