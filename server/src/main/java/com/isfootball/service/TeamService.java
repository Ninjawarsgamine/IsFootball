package com.isfootball.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
import com.isfootball.model.Match;
import com.isfootball.model.Player;
import com.isfootball.model.Team;
import com.isfootball.model.Venue;
import com.isfootball.utils.Utils;

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
	    	//Convertimos la respuesta en un objeto de Java.	
	    	JsonNode responseData=responseBody.path("response");
	    	return responseData;
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}

	/**
	 * Función que obtiene los datos básicos de un equipo según un ID especificado.
	 * @param teamId El ID del equipo que se va a buscar.
	 * @return Un objeto Team con la información básica de un equipo que coincida con 
	 * el ID especificado.
	 */
	@Cacheable("teamByNameAndById")
	public Team getTeamByNameAndId(String teamName, Integer teamId){
		teamName=Utils.decodeSpaces(teamName);
		//Quitamos la codificación del nombre para que sea entendible por la API.
		
		List<Team>teamsByName=getTeamsByName(teamName);
		Team team=new Team();

		try{
			for(Team teamInfo: teamsByName){	
				if(teamInfo.getId().equals(teamId)){
					team.setId(teamInfo.getId());
					team.setName(teamInfo.getName());
					team.setLogo(teamInfo.getLogo());
					team.setFounded(teamInfo.getFounded());
					String urlCountry="https://"+apiHost+"/countries?name="+teamInfo.getCountry().getName();
					JsonNode countryData=doRequest(urlCountry).get(0);
					Country country=new Country();
					country.setFlag(countryData.path("flag").asText());
					team.setCountry(country);
	
					Venue venueInfo=teamInfo.getVenue();
					Venue venue=new Venue();
					venue.setId(venueInfo.getId());
					venue.setName(venueInfo.getName());
					venue.setImage(venueInfo.getImage());
					venue.setAddress(venueInfo.getAddress());
					venue.setCity(venueInfo.getCity());
					venue.setCapacity(venueInfo.getCapacity());
					team.setVenue(venue);
					return team;
				}
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Función que devuelve una lista de equipos que coincidan con un nombre espcificado.
	 * @param teamName El nombre del que se va a utilizar para realizar 
	 * la búsqueda.
	 * @return Lista de equipos coincidentes con el nombre especificado.
	 */
	@Cacheable("teamsByName")
	public List<Team>getTeamsByName(String teamName){
		List<Team> teams=new ArrayList<>();
		if(teamName.length()<3){
			return null;
		}
		String url="https://"+apiHost+"/teams?search="+teamName;
		JsonNode responseData=doRequest(url);
		try{
			for(JsonNode teamData: responseData){
				JsonNode teamInfo=teamData.path("team");
				Team team=new Team();
				team.setId(teamInfo.path("id").asInt());
				team.setName(teamInfo.path("name").asText());
				team.setLogo(teamInfo.path("logo").asText());
				team.setFounded(teamInfo.path("founded").asInt());

				Country country=new Country();
				country.setName(teamInfo.path("country").asText());
				team.setCountry(country);

				JsonNode venueData=teamData.path("venue");
				Venue venue=new Venue();
				venue.setId(venueData.path("id").asInt());
				venue.setName(venueData.path("name").asText());
				venue.setAddress(venueData.path("address").asText());
				venue.setImage(venueData.path("image").asText());
				venue.setCity(venueData.path("city").asText());
				venue.setCapacity(venueData.path("capacity").asInt());
				team.setVenue(venue);

				teams.add(team);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return teams;
	}

	@Cacheable("teamMatches")
	public List<Match>getTeamMatches(Integer teamId){
		List<Match> teamMatches=new ArrayList<>();

		TimeZone timeZone=TimeZone.getDefault(); 
		String timeZoneId=timeZone.getID();
		String url="https://"+apiHost+"/fixtures?season="+season+"&team="+teamId+
		"&timezone="+timeZoneId;
		JsonNode responseData=doRequest(url);
		if(responseData!=null && responseData.isArray()){
			try{
				for(JsonNode matchData: responseData){
					Match match=new Match();
					JsonNode matchInfo=matchData.path("fixture");
					match.setId(matchInfo.path("id").asInt());
	
					String matchDate=matchInfo.path("date").asText();
					ZonedDateTime dateTime = ZonedDateTime.parse(matchDate);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy · HH:mm", Locale.getDefault());

					String matchDateWithFormat = dateTime.format(formatter);
					match.setDate(matchDateWithFormat);
	
					JsonNode competitionInfo=matchData.path("league");
					Competition competition=new Competition();
					competition.setId(competitionInfo.path("id").asInt());
					competition.setName(competitionInfo.path("name").asText());
					competition.setLogo(competitionInfo.path("logo").asText());
					match.setCompetition(competition);
					
					match.setCompetitionRound(competitionInfo.path("round").asText());

					JsonNode teamHomeInfo=matchData.path("teams").path("home");
					Team teamHome=new Team();
					teamHome.setId(teamHomeInfo.path("id").asInt());
					teamHome.setName(teamHomeInfo.path("name").asText());
					teamHome.setLogo(teamHomeInfo.path("logo").asText());
					match.setTeamHome(teamHome);
	
					JsonNode teamAwayInfo=matchData.path("teams").path("away");
					Team teamAway=new Team();
					teamAway.setId(teamAwayInfo.path("id").asInt());
					teamAway.setName(teamAwayInfo.path("name").asText());
					teamAway.setLogo(teamAwayInfo.path("logo").asText());
					match.setTeamAway(teamAway);
	
					JsonNode goalsInfo=matchData.path("goals");
					match.setGoalsHome(goalsInfo.path("home").asInt());
					match.setGoalsAway(goalsInfo.path("away").asInt());
	
					teamMatches.add(match);
				}
				return teamMatches;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	/**
	 * Función que obtiene una lista de jugadores de un equipo determinado.
	 * 
	 * @param teamId Es el ID del equipo del que se van a sacar los jugadores
	 * @return Una lista de jugadores de un equipo determinado.
	 */
	public List<Player> getTeamPlayers(Integer teamId){
		List<Player>teamPlayers=new ArrayList<>();

		String url="https://"+apiHost+"/players?team="+teamId+"&season="+season;
		JsonNode responseData=doRequest(url);
		try {
			if(responseData!=null && responseData.isArray()){
				for(JsonNode playerData: responseData){
					JsonNode playerInfo=playerData.path("player");

					Player player=new Player();
					player.setId(playerInfo.path("id").asInt());
					player.setName(playerInfo.path("name").asText());
					player.setPhoto(playerInfo.path("photo").asText());
					player.setPosition(playerInfo.path("position").asText());

					Country country=new Country();
					country.setName(playerInfo.path("nationality").asText());

					teamPlayers.add(player);
				}
				return teamPlayers;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
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
