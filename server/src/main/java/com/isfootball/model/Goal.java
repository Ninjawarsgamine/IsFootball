package com.isfootball.model;

import java.io.Serializable;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
/**
 * Clase que representa la información sobre los goles de un equipo.
 */
@Getter
@Setter
public class Goal implements Serializable{

    private static final long serialVersionUID = 4990606117528824808L;
    
    private HomeAwayTotalStats distribution;
    
    private HomeAwayTotalStats average;

    private Map<String,Stat> minutes;

    private Map<String, UnderOver> underOver;
}
