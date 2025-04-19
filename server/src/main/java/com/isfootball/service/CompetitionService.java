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
import com.isfootball.model.Team;
import com.isfootball.model.TeamCompetitionStatistics;
import com.isfootball.model.TeamCompetitionStatistics.MatchesStatistics;
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
	 * Función que obtiene todos los datos de una competición.
	 * @param competitionId Id de la competición.
	 * @return Los datos de la competición con el ID especificado.
	 */
	@Cacheable("competitionAllDataById")
	public Competition getCompetitionAllDataById(Integer competitionId){
		Competition competition=new Competition();
		String url="https://"+apiHost+"/standings?league="+competitionId+"&season="+season;
		JsonNode competitionData=doRequest(url);
		
		if(competitionData!=null){
			JsonNode competitionAllData=competitionData.get(0).path("league");
			competition.setId(competitionAllData.path("id").asInt());
			competition.setName(competitionAllData.path("name").asText());
			competition.setLogo(competitionAllData.path("logo").asText());
			Country country=new Country();
			country.setName(competitionAllData.path("country").asText());
			country.setFlag(competitionAllData.path("flag").asText());
			competition.setCountry(country);

			competition.setSeason(competitionAllData.path("season").asInt());
			
			List<TeamCompetitionStatistics> competitionTeamsStatistics=new ArrayList<>();
			
			for(JsonNode competitionTeamStatisticsData: competitionAllData.path("standings").get(0)) {
				TeamCompetitionStatistics competitionTeamStatistics=new TeamCompetitionStatistics();
				
				competitionTeamStatistics.setRank(competitionTeamStatisticsData.path("rank").asInt());
				
				Team team=new Team();
				team.setId(competitionTeamStatisticsData.path("team").path("id").asInt());
				team.setName(competitionTeamStatisticsData.path("team").path("name").asText());
				team.setLogo(competitionTeamStatisticsData.path("team").path("logo").asText());
				competitionTeamStatistics.setTeam(team);

				competitionTeamStatistics.setPoints(competitionTeamStatisticsData.path("points").asInt());
				competitionTeamStatistics.setGoalsDiff(competitionTeamStatisticsData.path("goalsDiff").asInt());
				competitionTeamStatistics.setForm(competitionTeamStatisticsData.path("form").asText());
				
				MatchesStatistics competitionTeamAllMatches=new MatchesStatistics();
				competitionTeamAllMatches.setMatchesPlayed(competitionTeamStatisticsData.path("all").path("played").asInt());
				competitionTeamAllMatches.setMatchesWon(competitionTeamStatisticsData.path("all").path("win").asInt());
				competitionTeamAllMatches.setMatchesDrawn(competitionTeamStatisticsData.path("all").path("draw").asInt());
				competitionTeamAllMatches.setMatchesLost(competitionTeamStatisticsData.path("all").path("lose").asInt());
				competitionTeamAllMatches.setGoalsFor(competitionTeamStatisticsData.path("all").path("goals").path("for").asInt());
				competitionTeamAllMatches.setGoalsAgainst(competitionTeamStatisticsData.path("all").path("goals").path("against").asInt());
				
				MatchesStatistics competitionTeamHomeMatches = new MatchesStatistics();
				competitionTeamHomeMatches.setMatchesPlayed(competitionTeamStatisticsData.path("home").path("played").asInt());
				competitionTeamHomeMatches.setMatchesWon(competitionTeamStatisticsData.path("home").path("win").asInt());
				competitionTeamHomeMatches.setMatchesDrawn(competitionTeamStatisticsData.path("home").path("draw").asInt());
				competitionTeamHomeMatches.setMatchesLost(competitionTeamStatisticsData.path("home").path("lose").asInt());
				competitionTeamHomeMatches.setGoalsFor(competitionTeamStatisticsData.path("home").path("goals").path("for").asInt());
				competitionTeamHomeMatches.setGoalsAgainst(competitionTeamStatisticsData.path("home").path("goals").path("against").asInt());

				MatchesStatistics competitionTeamAwayMatches = new MatchesStatistics();
				competitionTeamAwayMatches.setMatchesPlayed(competitionTeamStatisticsData.path("away").path("played").asInt());
				competitionTeamAwayMatches.setMatchesWon(competitionTeamStatisticsData.path("away").path("win").asInt());
				competitionTeamAwayMatches.setMatchesDrawn(competitionTeamStatisticsData.path("away").path("draw").asInt());
				competitionTeamAwayMatches.setMatchesLost(competitionTeamStatisticsData.path("away").path("lose").asInt());
				competitionTeamAwayMatches.setGoalsFor(competitionTeamStatisticsData.path("away").path("goals").path("for").asInt());
				competitionTeamAwayMatches.setGoalsAgainst(competitionTeamStatisticsData.path("away").path("goals").path("against").asInt());

				competitionTeamStatistics.setAll(competitionTeamAllMatches);
				competitionTeamStatistics.setHome(competitionTeamHomeMatches);
				competitionTeamStatistics.setAway(competitionTeamAwayMatches);

				competitionTeamsStatistics.add(competitionTeamStatistics);
			}	
			competition.setTeamsCompetitionStatistics(competitionTeamsStatistics);
		}
		return competition;
	}
	
	/**
	 * Función que devuelve los datos básicos de una competición por su nombre.
	 * 
	 * @return Un objeto "Competition" con la información de la competición 
	 * deseada.
	 */
	@Cacheable ("competitionByName")
	public Competition getCompetitionByName(String competitionName) {
		competitionName=Utils.decodeSpaces(competitionName);

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
