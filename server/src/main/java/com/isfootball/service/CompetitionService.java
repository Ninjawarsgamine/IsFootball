package com.isfootball.service;

import java.util.ArrayList;
import java.util.Arrays;
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
	 * Función que devuelve todos los datos de una competición en base a su ID.
	 * 
	 * @param competitionId Id de la competición.
	 * @return Los datos de la competición con el ID especificado.
	 */
	@Cacheable ("competitionById")
	public Competition getCompetitionById(Integer competitionId) {
		if(competitionId==null) {
			throw new IllegalArgumentException("The competition id is null.");
		}
		if(!(competitionId instanceof Integer)) {
			throw new IllegalArgumentException("The competition id is not a Integer.");
		}
		String url="https://"+apiHost+"/leagues?id="+competitionId+"&season="+season;
	
	    JsonNode responseData=doRequest(url);
	    try {
			JsonNode competitionAllData=responseData.get(0);

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
			competition.setCountry(country);
			
			JsonNode competitionSeason=competitionAllData.path("seasons");
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
	@Cacheable ("competitionByName")
	public Competition getCompetitionByName(String competitionName) {
		competitionName=Utils.decodeSpaces(competitionName);
		if(competitionName==null) {
			throw new IllegalArgumentException("The competition name is null.");
		}
		
		String url="https://"+apiHost+"/leagues?name="+competitionName+"&season="+season;
		JsonNode responseData=doRequest(url);
	    
	    try {
			JsonNode competitionAllData=responseData.get(0);
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
			competition.setCountry(country);
			
			JsonNode competitionSeason=competitionAllData.path("seasons");
			competition.setSeason(competitionSeason.path("year").asInt());
			
	    	return competition;
	    }catch(Exception e) {
			e.printStackTrace();
	    	return null;
	    }
	}
	
	/**
	 * Función que devuelve una lista de competiciones que coincidan con un nombre espcificado.
	 * @param competitionName El nombre de la competición que se va a utilizar  para realizar 
	 * la búsqueda.
	 * @return Lista de competiciones coincidentes con el nombre especificado.
	 */
	@Cacheable ("competitionsByName")
	public List<Competition> getCompetitionsByName(String competitionName){
		List<Competition> competitions=new ArrayList<>();
		competitionName=Utils.decodeSpaces(competitionName);
		if(competitionName.length()<3) {
			return null;
		}
		String url="https://"+apiHost+"/leagues?search="+competitionName;
		JsonNode responseData=doRequest(url);
		try {
			for(JsonNode competitionAllData: responseData) {
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
				competition.setCountry(country);
				
				JsonNode competitionSeason=competitionAllData.path("seasons");
				
				for(JsonNode s: competitionSeason) {
					if(s.path("year").asText().equals(season)) {
						competition.setSeason(competitionSeason.path("year").asInt());
						competitions.add(competition);
						break;
					}
					//Solo añadimos la competición a la lista si es de la temporada 2023-2024.
				}
			}
			
			return competitions;
	    }catch(Exception e) {
			e.printStackTrace();
	    	return null;
	    }
	}
	
	/**
	 * Función que devuelve una lista de competiciones cuyos IDs coincidan con los especificados. El
	 * resultado se guarda en el caché, para reducir el número de peticiones a la API.
	 * @param ids Un array de IDs de competiciones a filtrar. 
	 * @return Una lista de competiciones que coinciden con los IDs proporcionados.
	 */
	@Cacheable("competitionsListByIds")
	public List<Competition> getListCompetitionsByIds(Integer[]ids){
		List<Competition> competitions=new ArrayList<>();
		String url="https://"+apiHost+"/leagues?season="+season;
		JsonNode allCompetitions= doRequest(url);
		if(allCompetitions!=null && allCompetitions.isArray()) {
			List <Integer>idsList=Arrays.asList(ids);
			for(JsonNode c: allCompetitions) {
				if(idsList.contains(c.path("league").get("id").asInt())) {
					Competition competition=new Competition();

					JsonNode competitionInfo=c.path("league");
					competition.setId(competitionInfo.get("id").asInt());
					competition.setName(competitionInfo.get("name").asText());
					competition.setType(competitionInfo.get("type").asText());
					competition.setLogo(competitionInfo.get("logo").asText());

					JsonNode competitionCountry=c.path("country");
					Country country=new Country();
					country.setName(competitionCountry.path("name").asText());
					country.setCode(competitionCountry.path("code").asText());
					country.setFlag(competitionCountry.path("flag").asText());
					competition.setCountry(country);
					
					JsonNode competitionSeason=c.path("seasons");
					competition.setSeason(competitionSeason.path("year").asInt());
					competitions.add(competition);
				}

				if(competitions.size()==idsList.size()){
					break;
				}
				//Si ya se han encontrado todas las ligas, se frena el bucle.
			}
		}
		return competitions;
	}
}
