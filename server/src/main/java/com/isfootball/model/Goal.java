package com.isfootball.model;

import java.io.Serializable;

import java.util.Map;
/**
 * Clase que representa la información sobre los goles de un equipo.
 */
public class Goal implements Serializable{

    private static final long serialVersionUID = 4990606117528824808L;
    
    private HomeAwayTotalStats distribution;
    
    private HomeAwayTotalStats average;

    private Map<String,Stat> minutes;

    private Map<String, UnderOver> underOver;


    public HomeAwayTotalStats getDistribution() {
        return distribution;
    }
    
    public void setDistribution(HomeAwayTotalStats distribution) {
        this.distribution = distribution;
    }
    
    public HomeAwayTotalStats getAverage() {
        return average;
    }
    
    public void setAverage(HomeAwayTotalStats average) {
        this.average = average;
    }
    
    public Map<String, Stat> getMinutes() {
        return minutes;
    }
    
    public void setMinutes(Map<String, Stat> minutes) {
        this.minutes = minutes;
    }
    
    public Map<String, UnderOver> getUnderCover() {
        return underOver;
    }
    
    public void setUnderOver(Map<String, UnderOver> underOver) {
        this.underOver = underOver;
    }
}
