package com.isfootball.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.model.Competition;
import com.isfootball.model.Country;
import com.isfootball.utils.Utils;
@Service
public class CompetitionService {
	@Value("${api.key}")
	private String apiKey;
	
	@Value("${api.host}")
	private String apiHost;
	
	@Value("${season}")
	private String season;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	// Sirve para convertir objetos JSON en objetos Java.

	/**
	 * Es el constructor de "CompetitionService".
	 */
	public CompetitionService() {
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
	}
	
	/**
	 * Función que devuelve todos los datos de una competición en base a su ID.
	 * 
	 * @param competitionId Id de la competición.
	 * @return Los datos de la competición con el ID especificado.
	 */
	public Competition getCompetitionById(Integer competitionId) {
		if(competitionId==null) {
			throw new IllegalArgumentException("The competition id is null.");
		}
		if(!(competitionId instanceof Integer)) {
			throw new IllegalArgumentException("The competition id is not a Integer.");
		}
		String url="https://"+apiHost+"/leagues?id="+competitionId+"+&season="+season;
		HttpHeaders headers=new HttpHeaders();
		headers.set("x-rapidapi-key", apiKey);
	    headers.set("x-rapidapi-host", apiHost);
	    
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    
	    String jsonResponse=response.getBody();
	    
	    try {
	    	JsonNode responseBody=objectMapper.readTree(jsonResponse);
			JsonNode competitionAllData=responseBody.path("response").get(0);

			Competition competition=new Competition();

			JsonNode competitionInfo=competitionAllData.path("league");
			competition.setId(competitionInfo.get("id").asInt());
			competition.setName(competitionInfo.get("name").asText());
			competition.setType(competitionInfo.get("type").asText());
			competition.setLogo(competitionInfo.get("logo").asText());

			JsonNode competitionCountry=competitionAllData.path("country");
			Country country=new Country();
			country.setName(competitionCountry.path("name").asText());
			country.setCode(competitionCountry.path("code").asText());
			country.setFlag(competitionCountry.path("flag").asText());
			
			JsonNode competitionSeason=competitionAllData.path("season");
			competition.setSeason(competitionSeason.path("year").asInt());
			
	    	return competition;
	    }catch(Exception e) {
			e.printStackTrace();
	    	return null;
	    }
	}
	
	/**
	 * Función que devuelve los datos de una competición por su nombre.
	 * 
	 * @return Un objeto "Competition" con la información de la competición 
	 * deseada.
	 */
	public Competition getCompetitionByName(String competitionName) {
		
		competitionName=Utils.encodeSpaces(competitionName);
		if(competitionName==null) {
			throw new IllegalArgumentException("The competition name is null.");
		}
		
		String url="https://"+apiHost+"/leagues?name="+competitionName+"+&season="+season;
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
			JsonNode competitionAllData=responseBody.path("response").get(0);
			//Sacamos el primer resultado.

			Competition competition=new Competition();
			//Objeto "Competition" que vamos a devolver.

			JsonNode competitionInfo=competitionAllData.path("league");
			competition.setId(competitionInfo.get("id").asInt());
			competition.setName(competitionInfo.get("name").asText());
			competition.setType(competitionInfo.get("type").asText());
			competition.setLogo(competitionInfo.get("logo").asText());

			JsonNode competitionCountry=competitionAllData.path("country");
			Country country=new Country();
			country.setName(competitionCountry.path("name").asText());
			country.setCode(competitionCountry.path("code").asText());
			country.setFlag(competitionCountry.path("flag").asText());
			
			JsonNode competitionSeason=competitionAllData.path("season");
			competition.setSeason(competitionSeason.path("year").asInt());
			
	    	return competition;
	    }catch(Exception e) {
			e.printStackTrace();
	    	return null;
	    }
	}
}
