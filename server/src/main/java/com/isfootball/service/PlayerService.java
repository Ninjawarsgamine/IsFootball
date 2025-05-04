package com.isfootball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.model.Country;
import com.isfootball.model.Player;
import com.isfootball.utils.Utils;

@Service
public class PlayerService {
     @Value("${api.key}")
	private String apiKey;
	
	@Value("${api.host}")
	private String apiHost;
	
	@Value("${season}")
	private String season;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	/**
	 * Es el constructor de "PlayerService".
	 */
	public PlayerService() {
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
	}
	
	/**
	 * Función que sirve para realizar una petición a la API externa.
	 * 
	 * @param url La URL con la que se va a hacer la petición.
	 * @return El resultado de la petición en un objeto Java.
	 */
	private JsonNode doRequest(String url) {
		HttpHeaders headers=new HttpHeaders();
		headers.set("x-rapidapi-key", apiKey);
	    headers.set("x-rapidapi-host", apiHost);
	
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    
	    String jsonResponse=response.getBody();
	    try {
	    	JsonNode responseBody=objectMapper.readTree(jsonResponse);
	    	JsonNode responseData=responseBody.path("response");
	    	return responseData;
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}

    /**
     * Función que devuelve devuelve una lista de jugadores cuyo nombre/apellido coincida con
     * el especificado.
     * @param playerName Es el nombre/apellido que se va a utilizar para la búsqueda.
     * @return Una lista de jugadores cuyo nombre/apellido coincida con el especificado.
     */
    @Cacheable("playersByName")
    public List<Player>getPlayersByName(String playerName){
        List<Player>players=new ArrayList<>();

        playerName=Utils.decodeSpaces(playerName);
		if(playerName.length()<3) {
			return null;
		}

        String url="https://"+apiHost+"/players/profiles?search="+playerName;
        JsonNode responseData=doRequest(url);
        System.out.println(responseData);
        if(responseData!=null && responseData.isArray()){
            try {
                for(JsonNode playerData: responseData) {
                   Player player=new Player();
                    
                    JsonNode playerInfo=playerData.path("player");
                    player.setId(playerInfo.get("id").asInt());
                    player.setName(playerInfo.get("name").asText());
                    
                    Country nationality=new Country();
                    nationality.setName(playerInfo.get("nationality").asText());
                    player.setNationality(nationality);
                    
                    player.setPosition(playerInfo.path("position").asText());
                    player.setPhoto(playerInfo.path("photo").asText());

                    players.add(player);
                }
                return players;
            }catch(Exception e) {
                e.printStackTrace();
            }
    
        }
        return null;
    }
}
