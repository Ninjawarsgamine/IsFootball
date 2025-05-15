package com.isfootball.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la información sencilla de un equipo pero algo más que básica.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamSimpleDTO implements Serializable {
    
    private static final long serialVersionUID = 8086858437306656928L;
    
	private Integer id;
    private String name;
    private String logo;
    private CountryDTO country;
}
