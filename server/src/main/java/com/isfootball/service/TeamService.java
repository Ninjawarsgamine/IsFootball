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
import com.isfootball.model.Team;

@Service
public class TeamService {
  @Value("${api.key}")
	private String apiKey;
	
	@Value("${api.host}")
	private String apiHost;
	
	@Value("${season}")
	private String season;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	/**
	 * Es el constructor de "TeamService".
	 */
	public TeamService() {
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
	    //Añadimos los headers.
	    
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    //"HttpEntity" encapsula tanto los encabezados HTTP como el cuerpo
	    //de una solicitud o respuesta.
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    //Hacemos la solicitud usando "restTemplate".
	    String jsonResponse=response.getBody();
	    try {
	    	JsonNode responseBody=objectMapper.readTree(jsonResponse);
	    	//Convertimos  la respuesta en un objeto de Java.	
	    	JsonNode responseData=responseBody.path("response");
	    	return responseData;
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}

    /**
     * Función que devuelve una lista de todos los equipos de una competición con un ID especificado.
     * 
     * @param competitionId EL ID de la competición.
     * @return Una lista con la información básica de todos los equipos de una competición.
     */
    @Cacheable ("competitionTeams")
    public List<Team> getCompetitionTeams(Integer competitionId){
		List<Team>competitionTeams=new ArrayList<>();
		if(competitionId==null) {
			throw new IllegalArgumentException("The competition id is null.");
		}
		if(!(competitionId instanceof Integer)) {
			throw new IllegalArgumentException("The competition id is not a Integer.");
		}
		String url="https://"+apiHost+"/teams?league="+competitionId+"&season="+season;
        JsonNode responseData=doRequest(url);
		System.out.println(responseData);
        for(JsonNode teamData:responseData){
            Team team=new Team();
            team.setId(teamData.path("team").path("id").asInt());
            team.setName(teamData.path("team").path("name").asText());
            team.setLogo(teamData.path("team").path("logo").asText());
            team.setCode(teamData.path("team").path("code").asText());
            competitionTeams.add(team);
        }
        return competitionTeams;
	}
}
