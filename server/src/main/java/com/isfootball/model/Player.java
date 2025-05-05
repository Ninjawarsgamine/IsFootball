package com.isfootball.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que almacena los datos de un jugador.
 */

@Getter
@Setter
public class Player implements Serializable {
    
    private static final long serialVersionUID = -8973618725339650477L;

	private Integer id;
	private String name;

	private String firstname;
	private String lastname;

	private String position;
	private Team playerTeam;

	private Country nationality;
	private Integer age;
	private String birthday;
	private String height;
	private String weight;
	private String photo;
	private Boolean isInjured;

}
