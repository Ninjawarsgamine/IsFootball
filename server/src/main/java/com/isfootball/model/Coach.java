package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la información de un entrenador de un equipo.
 */
@Getter
@Setter
public class Coach implements Serializable {

    private static final long serialVersionUID = -2826680478285504605L;
    
	private Integer id;
    private String name;
    private String photo;
    private Country nationality;
}
