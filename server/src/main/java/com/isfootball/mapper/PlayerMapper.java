package com.isfootball.mapper;

import com.isfootball.dto.PlayerDTO;
import com.isfootball.dto.PlayerSimpleDTO;
import com.isfootball.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Este Mapper convierte Player a sus respectivos DTOs.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        TeamMapper.class, 
        CountryMapper.class, 
        PlayerCompetitionStatisticsMapper.class
    }
)
public interface PlayerMapper {

    /**
     * Mapea Player a PlayerSimpleDTO.
     */
    @Mapping(source = "id",           target = "id")
    @Mapping(source = "name",         target = "name")
    @Mapping(source = "position",     target = "position")
    @Mapping(source = "photo",        target = "photo")
    @Mapping(source = "nationality",  target = "nationality")
    PlayerSimpleDTO toPlayerSimpleDTO(Player player);

    /**
     * Mapea Player a PlayerDTO.
     */
    @Mapping(source = "id",                          target = "id")
    @Mapping(source = "name",                        target = "name")
    @Mapping(source = "firstname",                   target = "firstname")
    @Mapping(source = "lastname",                    target = "lastname")
    @Mapping(source = "position",                    target = "position")
    @Mapping(source = "playerTeam",                  target = "playerTeam")    
    @Mapping(source = "nationality",                 target = "nationality")
    @Mapping(source = "age",                         target = "age")
    @Mapping(source = "birthday",                    target = "birthday")
    @Mapping(source = "height",                      target = "height")
    @Mapping(source = "weight",                      target = "weight")
    @Mapping(source = "photo",                       target = "photo")
    @Mapping(source = "isInjured",                   target = "isInjured")
    @Mapping(source = "playerCompetitionStatistics", target = "playerCompetitionStatistics")
    PlayerDTO toPlayerDTO(Player player);

    /**
     * Mapea una lista de Player a una lista de PlayerSimpleDTO.
     */
    List<PlayerSimpleDTO> toPlayerSimpleDTOList(List<Player> players);

    /**
     * Mapea una lista de Player a una lista de PlayerDTO.
     */
    List<PlayerDTO> toPlayerDTOList(List<Player> players);
}
