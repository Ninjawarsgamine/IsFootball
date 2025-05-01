package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que representa una estadística con un valor total y su porcentaje asociado.
 */
public class Stat implements Serializable {
	
    private static final long serialVersionUID = 2331477067712420641L;
    
	private Integer total;
    private String percentage;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
