package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la información sobre un estadio de fútbol.
 */
@Getter
@Setter
public class Venue implements Serializable {
    
    private static final long serialVersionUID = -5261183434233293290L;
    
	private Integer id;
    private String name;
    private String image;
    private String city;
    private Integer capacity;
    private String address;
}
