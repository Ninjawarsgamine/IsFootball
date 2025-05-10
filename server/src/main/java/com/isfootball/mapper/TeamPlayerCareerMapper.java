package com.isfootball.mapper;

import com.isfootball.dto.TeamPlayerCareerDTO;

import com.isfootball.model.TeamPlayerCareer;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Este Mapper convierte TeamPlayerCareer a su respectivo DTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface TeamPlayerCareerMapper {

    /**
     * Mappea TeamPlayerCareer aTeamPlayerCareerDTO
     * 
     * @param stats La entidad TeamPlayerCareer que contiene los datos.
     * @return Un objeto TeamPlayerCareerDTO con los datos del equipo limitados.
     */
    @Mapping(source = "team", target = "team")
    @Mapping(source = "seasons", target = "seasons")
    TeamPlayerCareerDTO toTeamPlayerCareerDTO(TeamPlayerCareer stats);

     /**
     * Mappea una lista de TeamPlayerCareer a una lista de TeamPlayerCareerDTO
     * 
     * @param statsList Lista de entidades TeamPlayerCareer.
     * @return Lista de DTOs TeamPlayerCareerDTO.
     */
    List<TeamPlayerCareerDTO> toTeamPlayerCareerDTOList(List<TeamPlayerCareer> statsList);
}
