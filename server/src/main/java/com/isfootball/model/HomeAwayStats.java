package com.isfootball.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa las estadísticas de un objeto en local y visitante.
 */
@Getter
@Setter
public class HomeAwayStats implements Serializable{

	private static final long serialVersionUID = 2809674769030201990L;

	private String home;
    private String away;
}
