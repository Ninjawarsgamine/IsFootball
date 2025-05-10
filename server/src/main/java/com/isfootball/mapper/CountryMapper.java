package com.isfootball.mapper;

import com.isfootball.dto.CountryDTO;
import com.isfootball.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/**
 * Este Mapper convierte Country a su respectivo DTO.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper {

    /**
     * Mapea Country a CountryDTO
     *
     * @param country La entidad Country que contiene la información del país.
     * @return Un objeto CountryDTO con los datos del país.
     */
    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "flag", target = "flag")
    CountryDTO toCountryDTO(Country country);

}
