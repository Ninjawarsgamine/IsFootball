package com.isfootball.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isfootball.dto.CompetitionDTO;
import com.isfootball.dto.CompetitionSimpleDTO;
import com.isfootball.dto.MatchDTO;
import com.isfootball.dto.PlayerCompetitionStatisticsBasicDTO;
import com.isfootball.service.CompetitionService;

@RestController
@RequestMapping("/api")
public class CompetitionController {
	
	private final CompetitionService competitionService;
	
    /**
     * Constructor del controlador donde Spring inyecta automáticamente el servicio.
     * 
     * @param competitionService Es el servicio.
     */
    @Autowired
	public CompetitionController(CompetitionService competitionService) {
	        this.competitionService = competitionService;
	 }
	
    /**
	 * Endpoint REST para obtener todos los datos de una competición por su id.
	 * 
	 * @param id El id de la competición.
	 * @return Un objeto "CompetitionSimpleDTO" con los datos de cada competición.
	 */
	@GetMapping("/competitionAllData/{id}")
	public CompetitionDTO getCompetitionAllDataById(@PathVariable String id) {
		return competitionService.getCompetitionAllDataById(Integer.valueOf(id));
	}	
    
	/**
	 * Endpoint REST para obtener los datos básicos de una competición por su nombre.
	 * 
	 * @param name El nombre de la competición.
	 * @return Un objeto "CompetitionSimpleDTO" con los datos básicos de cada competición.
	 */
	@GetMapping("/competitionByName/{name}")
	public CompetitionSimpleDTO getCompetitionByName(@PathVariable String name) {
		return competitionService.getCompetitionByName(name);
	}
	
	/**
	 * Endpoint REST para obtener una lista de competiciones que coincidan con un
	 * nombre de competición especificado.
	 * 
	 * @param name El nombre de la competición que se va a utilizar  para realizar 
	 * la búsqueda.
	 * @return Lista de competiciones que coincidan con un nombre de 
	 * competición especificado.
	 */
	@GetMapping("/competitions/{name}")
	public List<CompetitionSimpleDTO> getCompetitionsByName(@PathVariable String name) {
		return competitionService.getCompetitionsByName(name);
	}
	
	 /**
     * Endpoint para obtener competiciones por una lista de IDs.
     * 
     * @param ids Un array de IDs de competiciones.
     * @return Una lista de competiciones correspondientes a esos IDs.
     */
	@PostMapping("/competitions/by-ids")
	public List<CompetitionSimpleDTO> getListCompetitionsByIds(@RequestBody Integer[]ids){
		return competitionService.getListCompetitionsByIds(ids);
	}

	/**
	 * Endpoint REST para obtener una lista de jugadores que sean los máximos goleadores de una
	 * competición especificada junto a sus estadísticas.
	 * 
	 * @param id Es el ID de la comeptición de la que se van a sacar los máximos goleadores.
	 * @return Lista de jugadores que sean los máximos goleadores de la competición especificada 
	 * junto a sus estadísticas.
	 */
	@GetMapping("/competitions/{competitionId}/top-scorers")
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopScorers(@PathVariable String competitionId){
		return competitionService.getCompetitionTopScorers(Integer.valueOf(competitionId));
	}
	
	/**
	 * Endpoint REST para obtener una lista de jugadores que sean los máximos asistentes de una
	 * competición especificada junto a sus estadísticas.
	 * 
	 * @param id Es el ID de la competición de la que se van a sacar los máximos asistentes
	 * @return Lista de jugadores que sean los máximos asistentes de la competición especificada 
	 * junto a sus estadísticas.
	 */
	@GetMapping("/competitions/{competitionId}/top-assists-providers")
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopAssistsProviders(@PathVariable Integer competitionId){
		return competitionService.getCompetitionTopAssistsProviders(Integer.valueOf(competitionId));
	}

	/**
	 * Endpoint REST para obtener una lista de jugadores que más tarjetas amarillas hayan recibido
	 * en una competición especificada junto a sus estadísticas.
	 * 
	 * @param id Es el ID de la comeptición de la que se van a sacar los jugadores que jugadores que 
	 * más tarjetas amarillas hayan recibido.
	 * @return Lista de jugadores los jugadores que jugadores que más tarjetas amarillas hayan 
	 * recibido en la competición especificada junto a sus estadísticas.
	 */
	@GetMapping("/competitions/{competitionId}/top-yellow-cards")
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopYellowCards(@PathVariable Integer competitionId){
		return competitionService.getCompetitionTopYellowCards(Integer.valueOf(competitionId));
	}
	
	/**
	 * Endpoint REST para obtener una lista de jugadores que más tarjetas rojas hayan recibido
	 * en una competición especificada junto a sus estadísticas.
	 * 
	 * @param competitionId Es el ID de la comeptición de la que se van a sacar los jugadores que 
	 * más tarjetas rojas hayan recibido.
	 * @return Lista de jugadores los jugadores que jugadores que más tarjetas rojas hayan 
	 * recibido en la competición especificada junto a sus estadísticas.
	 */
	@GetMapping("/competitions/{competitionId}/top-red-cards")
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopRedCards(@PathVariable Integer competitionId){
		return competitionService.getCompetitionTopRedCards(Integer.valueOf(competitionId));
	}

	/**
	 * Endpoint REST para obtener una lista con las rondas de cada competición.
	 * 
	 * @param competitionId Es el ID de la competición de la que se van a sacar lss rondas.
	 * @return Lista de rondas de las que se compone una competición.
	 */
	@GetMapping("/competitions/{competitionId}/rounds")
	public List<String>getCompetitionAllRounds(@PathVariable Integer competitionId){
		return competitionService.getCompetitionAllRounds(Integer.valueOf(competitionId));
	}
	
	/**
	 * Endpoint REST para obtener una lista de los partidos de una ronda de una competición 
	 * especificada junto a los datos del sumario de los partidos.
	 * @param competitionId El ID de la competición de la que se van a sacar los partidos.
	 * @param round El ID de la ronda de la que se van a sacar los partidos.
	 * @return Una lista de los partidos de una ronda de una competición  especificada junto a los
	 * datos del sumario de los partidos.
	 */
	@GetMapping("/competitions/{competitionId}/round-matches-summary")
	public List<MatchDTO>getCompetitionRoundMatchesSummary( @PathVariable Integer competitionId, @RequestParam String round){
		return competitionService.getCompetitionRoundMatchesSummary(competitionId, round);
	}
}
