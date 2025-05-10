package com.isfootball.mapper;

import com.isfootball.dto.PlayerBasicDTO;
import com.isfootball.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Este Mapper convierte Player a PlayerBasicDTO.
 * Se hace para evitar que haya referencias circulares en el mapeo.
 */
@Mapper(componentModel = "spring")
public interface PlayerBasicMapper {

    /**
     * Mapea Player a PlayerBasicDTO.
     */
    @Mapping(source = "id",       target = "id")
    @Mapping(source = "name",     target = "name")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "photo",    target = "photo")
    PlayerBasicDTO toPlayerBasicDTO(Player player);

    /**
     * Mapea una lista de Player a una lista de PlayerBasicDTO.
     */
    List<PlayerBasicDTO> toPlayerBasicDTOList(List<Player> players);
}
