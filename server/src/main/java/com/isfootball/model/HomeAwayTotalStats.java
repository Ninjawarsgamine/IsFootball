package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa las estadísticas de un objeto en local, visitante y local
 */
@Getter
@Setter
public class HomeAwayTotalStats implements Serializable{
	
    private static final long serialVersionUID = -3118834275183372337L;
    
	private String home;
    private String away;
    private String total;
}