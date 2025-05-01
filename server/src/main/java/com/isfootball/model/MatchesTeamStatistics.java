package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que representa las estadísticas de un equipo en cuanto a partidos.
 * Contiene datos separados por condición de local, visitante y total.
 */
public class MatchesTeamStatistics implements Serializable{
	
    private static final long serialVersionUID = -3118834275183372337L;
    
	private Integer home;
    private Integer away;
    private Integer total;

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getAway() {
        return away;
    }

    public void setAway(Integer away) {
        this.away = away;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}