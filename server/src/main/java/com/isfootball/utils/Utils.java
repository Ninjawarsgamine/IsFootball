package com.isfootball.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.config.AppConfig;
import com.isfootball.model.Competition;
import com.isfootball.model.Country;
import com.isfootball.model.HomeAwayStats;
import com.isfootball.model.HomeAwayTotalStats;
import com.isfootball.model.Match;
import com.isfootball.model.Player;
import com.isfootball.model.Team;

public class Utils {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AppConfig appConfig;

    @Autowired
    public Utils(RestTemplate restTemplate, ObjectMapper objectMapper, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.appConfig = appConfig;
    }
    
 /**
	 * Función que sirve para realizar una petición a la API externa.
	 * 
	 * @param url La URL con la que se va a hacer la petición.
	 * @return El resultado de la petición en un objeto Java.
	 */
	public JsonNode getAllRequestResponse(String url) {
		HttpHeaders headers=new HttpHeaders();
		headers.set("x-rapidapi-key", appConfig.getApiKey());
	    headers.set("x-rapidapi-host", appConfig.getApiHost());
	    
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    String jsonResponse=response.getBody();
	    try {
	    	JsonNode responseBody=objectMapper.readTree(jsonResponse);
	    	return responseBody;
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}

    /**
	 * Función que sirve para realizar una petición a la API externa y obtener el objeto "response".
	 * 
	 * @param url La URL con la que se va a hacer la petición.
	 * @return El resultado de la petición en un objeto Java.
	 */
	public JsonNode doRequest(String url) {
		HttpHeaders headers=new HttpHeaders();
		headers.set("x-rapidapi-key", appConfig.getApiKey());
	    headers.set("x-rapidapi-host", appConfig.getApiHost());
	    
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
	 * Función que adapta textos de una URL a un texto normal oconvirtiendo "%20" en espacios normales
	 *  y recortando los espacios laterales.
	 * 
	 * @param text El texto codificado desde una URL que vamos a adaptar
	 * @return "text" pero con los "%20" cambiados por " " y los espacios laterales recortados.
	 */
	public String decodeSpaces(String text) {
		if(text==null) {
			return null;
		}
		return text.trim().replace("%20", " ");
	}

    /**
     * Función que convierte los datos de un JsonNode a un objeto "Competition" pero solo con
     * los datos básicos.
     * 
     * @param competitionInfo Es la parte de un JSON que tiene la información básica de 
     * una competición.
     * @return Un objeto "Competition" con la información básica de una competición.
     */
    public Competition parseCompetitionBasic(JsonNode competitionInfo){
        Competition competition=new Competition();
        competition.setId(competitionInfo.get("id").asInt());
        competition.setName(competitionInfo.get("name").asText());
        competition.setLogo(competitionInfo.get("logo").asText());
        return competition;
    }

    /**
     * Función que convierte los datos de un JsonNode a un objeto "Competition" pero solo con
     * los datos básicos.
     * 
     * @param competitionInfo Es la parte de un JSON que tiene la información básica de 
     * una competición.
     * @return Un objeto "Competition" con la información básica de una competición.
     */
    public Competition parseCompetitionSimple(JsonNode competitionInfo){
        Competition competition=new Competition();
        competition.setId(competitionInfo.get("id").asInt());
        competition.setName(competitionInfo.get("name").asText());
        competition.setType(competitionInfo.get("type").asText());
        competition.setLogo(competitionInfo.get("logo").asText());
        
        return competition;
    }

    /**
     * Función que convierte los datos de un JsonNode a un objeto "Country".
     * 
     * @param countryInfo Es la parte de un JSON que tiene la información de un "Country".
     * @return Un objeto "Country" con la información básica de un país.
     */
    public Country parseCountry(JsonNode countryInfo){
        Country country=new Country();
        country.setName(countryInfo.path("name").asText());
        country.setCode(countryInfo.path("code").asText());
        country.setFlag(countryInfo.path("flag").asText());
        
        return country;
    }
    /**
     * Función que convierte los datos de un JsonNode a un objeto "Team" pero solo con
     * los datos básicos.
     * 
     * @param teamInfo Es la parte de un JSON que tiene la información básica de un equipo.
     * @return Un objeto "Team" con la información básica de un equipo.
     */
    public Team parseTeamBasic(JsonNode teamInfo){
        Team team=new Team();
        team.setId(teamInfo.path("id").asInt());
        team.setName(teamInfo.path("name").asText());
        team.setLogo(teamInfo.path("logo").asText());
        return team;
    }

    /**
     * Función que convierte los datos de un JsonNode a un objeto "Team" pero solo con
     * los datos simples.
     * 
     * @param teamInfo Es la parte de un JSON que tiene la información sencilla de un equipo.
     * @return Un objeto "Team" con la información sencilla de un equipo.
     */
    public Team parseTeamSimple(JsonNode teamInfo){
        Team team=new Team();
        team.setId(teamInfo.path("id").asInt());
        team.setName(teamInfo.path("name").asText());
        team.setLogo(teamInfo.path("logo").asText());
        team.setFounded(teamInfo.path("founded").asInt());

        Country country=new Country();
        country.setName(teamInfo.path("country").asText());
        team.setCountry(country);
        return team;
    }

    /**
     * Función que convierte los datos de un JsonNode a un objeto "Player" pero solo con
     * los datos básicos.
     * 
     * @param playerInfo Es la parte de un JSON que tiene la información básica de un jugador.
     * @return Un objeto "Player" con la información básica de un jugador.
     */
    public Player parsePlayerBasic(JsonNode playerInfo){
        Player player=new Player();
        player.setId(playerInfo.path("id").asInt());
        player.setName(playerInfo.path("name").asText());
        player.setPhoto(playerInfo.path("photo").asText());
        return player;
    }

    /**
     * Esta función convierte los datos de un JsonNode a un objeto "HomeAwayTotalStats".
     * 
     * @param matchesInfo Es la parte de un JSON que tiene datos de partidos que dentro tienen 
     * "home", "away" y "total".
     * @return Un objeto "HomeAwayTotalStats" con los datos de un JSON.
     */
    public HomeAwayTotalStats parseHomeAwayTotalStats(JsonNode matchesInfo){
        HomeAwayTotalStats homeAwayTotalStats=new HomeAwayTotalStats();
        homeAwayTotalStats.setHome(matchesInfo.path("home").asText());
        homeAwayTotalStats.setAway(matchesInfo.path("away").asText());
        homeAwayTotalStats.setTotal(matchesInfo.path("total").asText());
        return homeAwayTotalStats;
    }

	/**
     * Esta función convierte los datos de un JsonNode a un objeto "HomeAwayStats".
     * 
     * @param matchesInfo Es la parte de un JSON que tiene datos de partidos que dentro tienen 
     * "home" y "away".
     * @return Un objeto "HomeAwayStats" con los datos de un JSON.
     */
    public HomeAwayStats parseHomeAwayStats(JsonNode matchesInfo){
        HomeAwayStats homeAwayStats=new HomeAwayStats();
        homeAwayStats.setHome(matchesInfo.path("home").asText());
        homeAwayStats.setAway(matchesInfo.path("away").asText());
        return homeAwayStats;
    }

    /**
     * Esta función convierte los datos de un JsonNode a un objeto "Match".
     * 
     * @param matchData Es la parte de un JSON que tiene datos de un partido.
     * 
     * @return Un objeto "Match" con los datos de un JSON.
     */
    public Match parseMatch(JsonNode matchData){
        Match match=new Match();

        JsonNode matchInfo=matchData.path("fixture");
        match.setId(matchInfo.path("id").asInt());

        String matchDate=matchInfo.path("date").asText();
        ZonedDateTime dateTime = ZonedDateTime.parse(matchDate);
        match.setZonedDateTime(dateTime);
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
        
        JsonNode matchStatusInfo=matchInfo.path("status");

        List<String> matchNotInGameStatus = Arrays.asList("Match Finished", "Match Cancelled", 
        "Match Abandoned", "Match Postponed");

        if(matchNotInGameStatus.contains(matchStatusInfo.path("long").asText())){
            match.setMatchLong(matchStatusInfo.path("long").asText());
        }else{
            match.setElapsed(matchStatusInfo.path("elapsed").asInt());
        }

        JsonNode penaltiesInfo=matchData.path("score").path("penalty");
        if(penaltiesInfo.path("home")!=null){
            match.setPenaltiesHome(penaltiesInfo.path("home").asInt());
            match.setPenaltiesAway(penaltiesInfo.path("away").asInt());
        }
        //Si el partido está activo, se devuelve el minuto en el que está y si no su estado.
        return match;
    }
}
