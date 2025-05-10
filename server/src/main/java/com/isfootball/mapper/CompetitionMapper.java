package com.isfootball.mapper;

import com.isfootball.dto.CompetitionBasicDTO;
import com.isfootball.dto.CompetitionDTO;
import com.isfootball.dto.CompetitionSimpleDTO;
import com.isfootball.model.Competition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

/**
 * Este Mapper convierte Competition a sus respectivos DTOs.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface CompetitionMapper {

    /**
     * Mapea Competition a CompetitionBasicDTO.
     *
     * @param competition la entidad Competition que se desea mapear.
     * @return CompetitionBasicDTO con los datos básicos de la competición.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "logo", target = "logo")
    CompetitionBasicDTO toCompetitionBasicDTO(Competition competition);

    /**
     * Mapea Competition a CompetitionSimpleDTO.
     *
     * @param competition la entidad Competition que se desea mapear.
     * @return CompetitionSimpleDTO con datos simplificados de la competición.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "logo", target = "logo")
    @Mapping(source = "country", target = "country") // Este mapeo usará el CountryMapper automáticamente
    CompetitionSimpleDTO toCompetitionSimpleDTO(Competition competition);

    /**
     * Mapea Competition a CompetitionDTO.
     *
     * @param competition la entidad Competition que se desea mapear.
     * @return CompetitionDTO con todos los campos de la competición.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "logo", target = "logo")
    @Mapping(source = "country", target = "country") // Este mapeo usará el CountryMapper automáticamente
    @Mapping(source = "teamsCompetitionStatistics", target = "teamsCompetitionStatistics")
    CompetitionDTO toCompetitionDTO(Competition competition);

    /**
     * Mapea Una lista de Competition a una lista de CompetitionSimpleDTO.
     *
     * @param competitions La lista de Competition que se desea mapear
     * @return List<CompetitionSimpleDTO> La lista de CompetitionSimpleDTO con los datos simplificados de cada competición
     */
    List<CompetitionSimpleDTO> toCompetitionSimpleDTOList(List<Competition> competitions);
}
