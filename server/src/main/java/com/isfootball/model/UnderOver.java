package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa las estadísticas de goles por encima ("over") y por debajo ("under")
 * de una cierta cantidad de goles en un partido.
 */
@Getter
@Setter
public class UnderOver implements Serializable {
    
    private static final long serialVersionUID = 784131928234617660L;
    
	private Integer over;
    private Integer under;
}
