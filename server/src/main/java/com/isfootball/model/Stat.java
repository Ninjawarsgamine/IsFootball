package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una estadística con un valor total y su porcentaje asociado.
 */
@Getter
@Setter
public class Stat implements Serializable {
	
    private static final long serialVersionUID = 2331477067712420641L;
    
	private Integer total;
    private String percentage;
}
