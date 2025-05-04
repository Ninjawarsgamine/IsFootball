package com.isfootball.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isfootball.model.Coach;
import com.isfootball.model.Competition;
import com.isfootball.model.Match;
import com.isfootball.model.Player;
import com.isfootball.model.Team;
import com.isfootball.model.TeamCompetitionStatistics;
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
	 * Endpoint REST para obtener todos los datos de un equipo por su id.
	 * 
	 * @param id El ID del equipo.
	 * @return Un objeto "Team" con los datos de un equipo que tenga un ID especificado.
	 */
	@GetMapping("/api/teamByNameAndId/{name}/{id}")
	public Team getTeamByNameAndId(@PathVariable String name, @PathVariable String id) {
		return teamService.getTeamByNameAndId(name, Integer.valueOf(id));
	}	

	 /**
	 * Endpoint REST para obtener todos los partidos de un equipo por su id.
	 * 
	 * @param id El ID del equipo.
	 * @return Una lista con todos los partidos de un equipo que tenga un ID especificado.
	 */
	@GetMapping("/api/teamMatches/{id}")
	public List<Match> getTeamMatches(@PathVariable String id) {
		return teamService.getTeamMatches(Integer.valueOf(id));
	}	

  	/**
	 * Endpoint REST para obtener una lista de equipos que coincidan con un
	 * nombre especificado.
	 * 
	 * @param name El nombre del equipo que se va a utilizar para realizar 
	 * la búsqueda.
	 * @return Lista de competiciones que coincidan con un nombre especificado.
	 */
	@GetMapping("/api/teams/{name}")
	public List<Team> geTeamsByName(@PathVariable String name) {
		return teamService.getTeamsByName(name);
	}
	
  	/**
	 * Endpoint REST para obtener una lista con todas las competiciones que disputa un equipo 
	 * con un ID especificado.
	 * 
	 * @param id El ID del equipo.
	 * la búsqueda.
	 * @return Lista de competiciones que disputa el equipo con un ID especificado.
	 */
	@GetMapping("/api/teamCompetitions/{id}")
	List<Competition> getTeamCompetitions(@PathVariable String id){
		return teamService.getTeamCompetitions(Integer.valueOf(id));
	}
	
	/**
	 * Endpoint REST para obtener la información del entrenador de un equipo con un ID especificado.
	 * 
	 * @param id El ID del equipo.
	 * @return Un objeto "Coach" con los datos del entrenador de un equipo con un ID especificado.
	 */
	@GetMapping("/api/teamCoach/{id}")
	public Coach getTeamCoach(@PathVariable String id) {
		return teamService.getTeamCoach(Integer.valueOf(id));
	}	

	/**
	 * Endpoint REST para obtener todos los jugadores de un equipo por su id.
	 * 
	 * @param id El ID del equipo.
	 * @return Una lista con todos los jugadores de un equipo con un ID especificado.
	 */
	@GetMapping("/api/teamPlayers/{id}")
	public List<Player> getTeamPlayers(@PathVariable String id) {
		return teamService.getTeamPlayers(Integer.valueOf(id));
	}	

    /**
	 * Endpoint REST para obtener todos los datos de una competición por su id.
	 * 
	 * @param id El ID de la competición.
	 * @return Un objeto "Competition" con los datos de cada competición.
	 */
	@GetMapping("/api/competitionTeams/{id}")
	public List<Team> getCompetitionTeams(@PathVariable String id) {
		return teamService.getCompetitionTeams(Integer.valueOf(id));
	}	

	/**
	 * Endpoint REST para obtener todos los datos de un equipo con un ID especificado de una
	 *  competición con un ID especificado.
	 * 
	 * @param teamId El ID del equipo.
	 * @param competitionId El ID de la competición.
	 * @return Un objeto "Competition" con los datos de cada competición.
	 */
	@GetMapping("/api/teamCompetitionStatistics/{teamId}/{competitionId}")
	public TeamCompetitionStatistics getTeamCompetitionStatistics(@PathVariable String teamId,
	 @PathVariable String competitionId) {
		return teamService.getTeamCompetitionStatistics(Integer.valueOf(teamId),
		Integer.valueOf(competitionId));
	}
}
