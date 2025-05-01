package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que recoge la información sobre las alineaciones de un equipo en una competición
 * o en un partido.
 */
public class Lineup implements Serializable{

    private String formation;
    private Integer matchesPlayed;
    
    public String getFormation() {
        return formation;
    }
    
    public void setFormation(String formation) {
        this.formation = formation;
    }
    
    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }
    
    public void setMatchesPlayed(Integer matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
    

}
