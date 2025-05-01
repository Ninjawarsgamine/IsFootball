package com.isfootball.model;

import java.io.Serializable;

import java.util.Map;
/**
 * Clase que representa la información sobre los goles de un equipo.
 */
public class Goal implements Serializable{

    private static final long serialVersionUID = 4990606117528824808L;
    
	private Integer home;
    private Integer away;
    private Integer total;
    
	private Integer totalAvarage;
	private Integer averageHome;
	private Integer averageAway;

    private Map<String,Stat>minutes;

    private Map<String, UnderOver>undercover;

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

    public Integer getTotalAvarage() {
        return totalAvarage;
    }

    public void setTotalAvarage(Integer totalAvarage) {
        this.totalAvarage = totalAvarage;
    }

    public Integer getAverageHome() {
        return averageHome;
    }

    public void setAverageHome(Integer averageHome) {
        this.averageHome = averageHome;
    }

    public Integer getAverageAway() {
        return averageAway;
    }

    public void setAverageAway(Integer averageAway) {
        this.averageAway = averageAway;
    }

    public Map<String, Stat> getMinutes() {
        return minutes;
    }

    public void setMinutes(Map<String, Stat> minutes) {
        this.minutes = minutes;
    }

    public Map<String, UnderOver> getUndercover() {
        return undercover;
    }

    public void setUndercover(Map<String, UnderOver> undercover) {
        this.undercover = undercover;
    }
}
