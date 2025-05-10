package com.isfootball.mapper;

import com.isfootball.dto.MatchDTO;
import com.isfootball.model.Match;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Este Mapper convierte Match a su respectivo DTO.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class, TeamMapper.class})
public interface MatchMapper {

    /**
     * Mapea Match a MatchDTO.
     *zz
     * @param match La entidad Match que contiene la información del partido.
     * @return MatchDTO con los datos del partido.
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "referee", target = "referee"),
        @Mapping(source = "date", target = "date"),
        @Mapping(source = "competition", target = "competition"), // Mapeo de competition usando CompetitionMapper
        @Mapping(source = "competitionRound", target = "competitionRound"),
        @Mapping(source = "timeZoneId", target = "timeZoneId"),
        @Mapping(source = "teamHome", target = "teamHome"), // Mapeo de teamHome usando TeamMapper
        @Mapping(source = "teamAway", target = "teamAway"), // Mapeo de teamAway usando TeamMapper
        @Mapping(source = "goalsHome", target = "goalsHome"),
        @Mapping(source = "goalsAway", target = "goalsAway"),
        @Mapping(source = "matchLong", target = "matchLong"),
        @Mapping(source = "elapsed", target = "elapsed"),
        @Mapping(source = "matchShort", target = "matchShort"),
        @Mapping(source = "venue", target = "venue")
    })
    MatchDTO toMatchDTO(Match match);

     /**
     * Mapea una lista de Match a una lista de MatchDTO.
     *
     * @param matches la lista de entidades Match
     * @return una lista de MatchDTO con los datos de los partidos.
     */
    List<MatchDTO> toMatchDTOList(List<Match> matches);
}
