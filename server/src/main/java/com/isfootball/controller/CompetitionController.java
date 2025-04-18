package com.isfootball.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isfootball.model.Competition;
import com.isfootball.service.CompetitionService;

@RestController
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
	 * @return Un objeto "Competition" con los datos de cada competición.
	 */
	@GetMapping("/api/competitionAllData/{id}")
	public Competition getCompetitionAllDataById(@PathVariable String id) {
		return competitionService.getCompetitionAllDataById(Integer.valueOf(id));
	}	
    
	/**
	 * Endpoint REST para obtener los datos básicos de una competición por su nombre.
	 * 
	 * @param name El nombre de la competición.
	 * @return Un objeto "Competition" con los datos básicos de cada competición.
	 */
	@GetMapping("/api/competitionByName/{name}")
	public Competition getCompetitionByName(@PathVariable String name) {
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
	@GetMapping("/api/competitions/{name}")
	public List<Competition> getCompetitionsByName(@PathVariable String name) {
		return competitionService.getCompetitionsByName(name);
	}
	
	 /**
     * Endpoint para obtener competiciones por una lista de IDs.
     * 
     * @param ids Un array de IDs de competiciones.
     * @return Una lista de competiciones correspondientes a esos IDs.
     */
	@PostMapping("/api/competitions/by-ids")
	public List<Competition> getListCompetitionsByIds(@RequestBody Integer[]ids){
		return competitionService.getListCompetitionsByIds(ids);
	}
}
