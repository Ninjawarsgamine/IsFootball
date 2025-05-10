package com.isfootball.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase representa uno de los equipos en los que ha estado un jugador. Contiene el equipo y
 * una lista con todas las temporadas en las que el jugador ha estado en el equipo.
 */
@Getter
@Setter
public class TeamPlayerCareer {
    
    private Team team=new Team();
    private List<Integer>seasons;
}
