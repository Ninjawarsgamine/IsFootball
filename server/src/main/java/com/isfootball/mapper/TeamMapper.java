package com.isfootball.mapper;

import com.isfootball.dto.TeamBasicDTO;
import com.isfootball.dto.TeamDTO;
import com.isfootball.dto.TeamSimpleDTO;
import com.isfootball.dto.TeamSquadDTO;
import com.isfootball.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Este Mapper convierte Team a sus respectivos DTOs.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class, PlayerBasicMapper.class })
public interface TeamMapper {

    /**
     * Mapea Team a TeamBasicDTO.
     * 
     * @param team la entidad Team.
     * @return TeamBasicDTO con los datos básicos de un equipo.
     */
    @Mapping(source = "id",        target = "id")
    @Mapping(source = "name",      target = "name")
    @Mapping(source = "logo",      target = "logo")
    TeamBasicDTO toTeamBasicDTO(Team team);

    /**
     * Mappea Team a TeamSimpleDTO.
     * 
     * @param team la entidad Team.
     * @return TeamSimpleDTO con la información simple de un equipo.
     */
    @Mapping(source = "id",        target = "id")
    @Mapping(source = "name",      target = "name")
    @Mapping(source = "logo",      target = "logo")
    @Mapping(source = "country",   target = "country") // Usamos el CountryMapper
    TeamSimpleDTO toTeamSimpleDTO(Team team);

    /**
     * Mappea Team a TeamDTO.
     * 
     * @param team la entidad Team.
     * @return TeamDTO con todos los campos detallados.
     */
    @Mapping(source = "id",        target = "id")
    @Mapping(source = "name",      target = "name")
    @Mapping(source = "logo",      target = "logo")
    @Mapping(source = "code",      target = "code")
    @Mapping(source = "country",   target = "country") // Usamos el CountryMapper
    @Mapping(source = "founded",   target = "founded")
    @Mapping(source = "venue",     target = "venue")  // Venue se mapea automáticamente
    TeamDTO toTeamDTO(Team team);

    /**
     * Mappea Team a TeamSquadDTO.
     * 
     * @param team la entidad Team.
     * @return TeamSquadDTO que contiene el coach y la lista de jugadores.
     */
    @Mapping(source = "coach",     target = "coach")  // El coach se mapea directamente
    @Mapping(source = "players",   target = "players") // Usamos el PlayerBasicMapper para mapear la lista de jugadores
    TeamSquadDTO toTeamSquadDTO(Team team);

    /**
     * Mappea una lista de Team a una lista de TeamDTO.
     * 
     * @param teams la lista de equipos.
     * @return la lista de TeamDTO.
     */
    List<TeamDTO> toTeamDTOList(List<Team> teams);

    /**
     * Mappea una lista de Team a una lista de TeamBasicDTO.
     * 
     * @param teams la lista de equipos.
     * @return la lista de TeamBasicDTO.
     */
    List<TeamBasicDTO> toTeamBasicDTOList(List<Team> teams);
}
