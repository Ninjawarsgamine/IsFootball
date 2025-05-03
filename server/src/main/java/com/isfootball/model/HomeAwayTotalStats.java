package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que representa las estadísticas de un objeto en local, visitante y local
 */
public class HomeAwayTotalStats implements Serializable{
	
    private static final long serialVersionUID = -3118834275183372337L;
    
	private String home;
    private String away;
    private String total;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}