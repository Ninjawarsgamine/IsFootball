package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que recoge la información sobre las alineaciones de un equipo en una competición
 * o en un partido.
 */
@Getter
@Setter
public class Lineup implements Serializable{

    private String formation;
    private Integer matchesPlayed;
}
