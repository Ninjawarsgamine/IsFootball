package com.isfootball.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * DTO para la información sencilla de un jugador pero algo más que básica.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerSimpleDTO implements Serializable {
    
    private static final long serialVersionUID = 8522410210662354105L;
	
	private Integer id;
	private String name;
	private String position;
    private CountryDTO nationality;
	private String photo;
}
