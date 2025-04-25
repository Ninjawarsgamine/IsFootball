package com.isfootball.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isfootball.model.Team;
import com.isfootball.service.TeamService;

@RestController
public class TeamController {
     private final TeamService teamService;

    /**
     * Constructor del controlador donde Spring inyecta automáticamente el servicio.
     * 
     * @param teamService Es el servicio que maneja las operaciones relacionadas con los equipos.
     */
    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
	 * Endpoint REST para obtener todos los datos de una competición por su id.
	 * 
	 * @param id El id de la competición.
	 * @return Un objeto "Competition" con los datos de cada competición.
	 */
	@GetMapping("/api/competitionTeams/{id}")
	public List<Team> getCompetitionTeams(@PathVariable String id) {
		return teamService.getCompetitionTeams(Integer.valueOf(id));
	}	

}
