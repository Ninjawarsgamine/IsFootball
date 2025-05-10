package com.isfootball.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isfootball.dto.PlayerDTO;
import com.isfootball.dto.PlayerSimpleDTO;
import com.isfootball.service.PlayerService;

@RestController
@RequestMapping("/api")
public class PlayerController {

    	private final PlayerService playerService;
	
    /**
     * Constructor del controlador donde Spring inyecta automáticamente el servicio.
     * 
     * @param competitionService Es el servicio.
     */
    @Autowired
	public PlayerController(PlayerService playerService) {
	        this.playerService = playerService;
	}

	   /**
	 * Endpoint REST para obtener la información de un jugador con un ID especificado.
	 * 
	 * @param id El id del jugador que se va a utilizar para realizar la búsqueda.
	 * @return Un objeto "Player" con toda la información de un jugador.
	 */
	@GetMapping("/players/{id}")
	public PlayerDTO getPlayerById(@PathVariable String id){
		return playerService.getPlayerById(Integer.valueOf(id));
	}

    /**
	 * Endpoint REST para obtener una lista de jugadores que coincidan con un
	 * nombre/apellido especificado.
	 * 
	 * @param name El nombre/apellido del jugador que se va a utilizar para realizar 
	 * la búsqueda.
	 * @return Lista de jugadores que coincidan con un nombre/apellido especificado.
	 */
	@GetMapping("/playersSearch/{name}")
	public List<PlayerSimpleDTO> getPlayersByName(@PathVariable String name) {
		return playerService.getPlayersByName(name);
	}
}
