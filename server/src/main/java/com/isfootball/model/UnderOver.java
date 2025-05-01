package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que representa las estadísticas de goles por encima ("over") y por debajo ("under")
 * de una cierta cantidad de goles en un partido.
 */
public class UnderOver implements Serializable {
    
    private static final long serialVersionUID = 784131928234617660L;
    
	private Integer over;
    private Integer under;
    
    public Integer getOver() {
        return over;
    }

    public void setOver(Integer over) {
        this.over = over;
    }

    public Integer getUnder() {
        return under;
    }

    public void setUnder(Integer under) {
        this.under = under;
    }
}
