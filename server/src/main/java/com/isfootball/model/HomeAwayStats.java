package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que representa las estadísticas de un objeto en local y visitante.
 */
public class HomeAwayStats implements Serializable{

	private static final long serialVersionUID = 2809674769030201990L;

	private String home;
    private String away;

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
}
