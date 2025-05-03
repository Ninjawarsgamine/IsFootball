package com.isfootball.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.isfootball.model.Goal;
import com.isfootball.model.Match;
import com.isfootball.model.MatchesTeamStatistics;
import com.isfootball.model.Player;
import com.isfootball.model.PlayerCompetitionStatistics;
import com.isfootball.model.Team;
import com.isfootball.model.TeamCompetitionStatistics;
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
		String urlCompetitionBasicData="https://"+apiHost+"/leagues?id="+competitionId+"&season="+season;
		JsonNode responseData=doRequest(urlCompetitionBasicData);
		//El "type" ("League" o "Cup") solo está disponible en "/leagues", así que necesitamos
		//esta petición.

		try{
			if(responseData!=null){
				JsonNode competitionBasicData=responseData.get(0);
				
				JsonNode competitionBasicInfo=competitionBasicData.path("league");
				competition.setId(competitionBasicInfo.path("id").asInt());
				competition.setName(competitionBasicInfo.path("name").asText());
				competition.setLogo(competitionBasicInfo.path("logo").asText());
				competition.setType(competitionBasicInfo.path("type").asText());
				
				JsonNode competitionCountry=competitionBasicData.path("country");
				Country country=new Country();
				country.setName(competitionCountry.path("name").asText());
				country.setCode(competitionCountry.path("code").asText());
				country.setFlag(competitionCountry.path("flag").asText());
				competition.setCountry(country);
	
				JsonNode competitionSeason=competitionBasicData.path("seasons");
				competition.setSeason(competitionSeason.get(0).path("year").asInt());
	
				String url="https://"+apiHost+"/standings?league="+competitionId+"&season="+season;
				JsonNode competitionAllData=doRequest(url);
				if(competitionAllData!=null && !competitionAllData.isEmpty()){
					JsonNode competitionData=competitionAllData.get(0).path("league");
					
					List<TeamCompetitionStatistics> competitionTeamsStatistics=new ArrayList<>();
					
					for(JsonNode group: competitionData.path("standings")) {
						for(JsonNode competitionTeamStatisticsData: group){
							TeamCompetitionStatistics competitionTeamStatistics=new TeamCompetitionStatistics();
						
							competitionTeamStatistics.setRank(competitionTeamStatisticsData.path("rank").asInt());
							
							Team team=new Team();
							team.setId(competitionTeamStatisticsData.path("team").path("id").asInt());
							team.setName(competitionTeamStatisticsData.path("team").path("name").asText());
							team.setLogo(competitionTeamStatisticsData.path("team").path("logo").asText());
							competitionTeamStatistics.setTeam(team);
			
							competitionTeamStatistics.setPoints(competitionTeamStatisticsData.path("points").asInt());
							competitionTeamStatistics.setGoalsDiff(competitionTeamStatisticsData.path("goalsDiff").asInt());
							competitionTeamStatistics.setGroup(competitionTeamStatisticsData.path("group").asText());
							competitionTeamStatistics.setForm(competitionTeamStatisticsData.path("form").asText());
							
							JsonNode competitionTeamMatchesInfo=competitionTeamStatisticsData.path("all");
			
							MatchesTeamStatistics matchesPlayed=new MatchesTeamStatistics();
							matchesPlayed.setTotal(competitionTeamMatchesInfo.path("played").asInt());
							competitionTeamStatistics.setMatchesPlayed(matchesPlayed);
						
							MatchesTeamStatistics matchesWon=new MatchesTeamStatistics();
							matchesWon.setTotal(competitionTeamMatchesInfo.path("win").asInt());
							competitionTeamStatistics.setMatchesWon(matchesWon);

							MatchesTeamStatistics matchesDrawn=new MatchesTeamStatistics();
							matchesDrawn.setTotal(competitionTeamMatchesInfo.path("draw").asInt());
							competitionTeamStatistics.setMatchesDrawn(matchesDrawn);

							MatchesTeamStatistics matchesLost=new MatchesTeamStatistics();
							matchesLost.setTotal(competitionTeamMatchesInfo.path("lose").asInt());
							competitionTeamStatistics.setMatchesLost(matchesLost);
							
							Goal goalsFor=new Goal();
							goalsFor.setTotal(competitionTeamMatchesInfo.path("goals").path("for").asInt());
							competitionTeamStatistics.setGoalsFor(goalsFor);

							Goal goalsAgainst=new Goal();
							goalsAgainst.setTotal(competitionTeamMatchesInfo.path("goals").path("against").asInt());
							competitionTeamStatistics.setGoalsAgainst(goalsAgainst);

							competitionTeamsStatistics.add(competitionTeamStatistics);
							
						}
						competition.setTeamsCompetitionStatistics(competitionTeamsStatistics);
					}	
				}
			}
			return competition;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
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
			JsonNode competitionData=responseData.get(0);
			//Sacamos el primer resultado.

			Competition competition=new Competition();
			//Objeto "Competition" que vamos a devolver.

			JsonNode competitionInfo=competitionData.path("league");
			competition.setId(competitionInfo.get("id").asInt());
			competition.setName(competitionInfo.get("name").asText());
			competition.setType(competitionInfo.get("type").asText());
			competition.setLogo(competitionInfo.get("logo").asText());

			JsonNode competitionCountry=competitionData.path("country");
			Country country=new Country();
			country.setName(competitionCountry.path("name").asText());
			country.setCode(competitionCountry.path("code").asText());
			country.setFlag(competitionCountry.path("flag").asText());
			competition.setCountry(country);
			
			JsonNode competitionSeason=competitionData.path("seasons");
			competition.setSeason(competitionSeason.path("year").asInt());
			
	    	return competition;
	    }catch(Exception e) {
			e.printStackTrace();
	    	return null;
	    }
	}
	
	/**
	 * Función que devuelve una lista de competiciones que coincidan con un nombre espcificado.
	 * @param competitionName El nombre de la competición que se va a utilizar para realizar 
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
			try {
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
				}
			}catch(Exception e) {
				e.printStackTrace();
	    		return null;
	    	}
		}
		return competitions;
	}

	/**
	 * Devuelve los jugadores que sean los máximos goleadores de una competición
	 * con un ID especificado.
	 * @param competitionId Es el ID de la competición de la que vamos a sacar los datos.
	 * @return Una lista de jugadores con sus estadísticas en una competición especificada.
	 */
	@Cacheable("playerCompetitionTopScorers")
	public List<PlayerCompetitionStatistics> getCompetitionTopScorers(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+apiHost+"/players/topscorers?league="+competitionId+"&season="+season;
		JsonNode responseData=doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try {
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
		
					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=new Player();
					player.setId(playerBasicInfo.path("id").asInt());
					player.setName(playerBasicInfo.path("name").asText());
					player.setPhoto(playerBasicInfo.path("photo").asText());
					playerCompetitionStatistics.setPlayer(player);
					
					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=new Team();
					playerTeam.setId(playerTeamData.path("id").asInt());
					playerTeam.setName(playerTeamData.path("name").asText());
					playerTeam.setLogo(playerTeamData.path("logo").asText());;
					playerCompetitionStatistics.setTeam(playerTeam);
	
					playerCompetitionStatistics.setGamesAppearances(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setTotalGoals(playerAllStatistics.path("goals").path("total").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return competitionPlayersStatistics;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Devuelve los jugadores que sean los máximos asistentes de una competición
	 * con un ID especificado.
	 * @param competitionId Es el ID de la competición de la que vamos a sacar los datos.
	 * @return Una lista de jugadores con sus estadísticas en una competición especificada.
	 */
	@Cacheable("playerCompetitionTopAssistsProviders")
	public List<PlayerCompetitionStatistics> getCompetitionTopAssistsProviders(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+apiHost+"/players/topassists?league="+competitionId+"&season="+season;
		JsonNode responseData=doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try{
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
		
					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=new Player();
					player.setId(playerBasicInfo.path("id").asInt());
					player.setName(playerBasicInfo.path("name").asText());
					player.setPhoto(playerBasicInfo.path("photo").asText());
					playerCompetitionStatistics.setPlayer(player);
					
					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=new Team();
					playerTeam.setId(playerTeamData.path("id").asInt());
					playerTeam.setName(playerTeamData.path("name").asText());
					playerTeam.setLogo(playerTeamData.path("logo").asText());;
					playerCompetitionStatistics.setTeam(playerTeam);
	
					playerCompetitionStatistics.setGamesAppearances(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setAssists(playerAllStatistics.path("goals").path("assists").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return competitionPlayersStatistics;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Devuelve los jugadores que más tarjetas rojas hayan recibido en una competición
	 * con un ID especificado.
	 * @param competitionId Es el ID de la competición de la que vamos a sacar los datos.
	 * @return Una lista de jugadores con sus estadísticas en una competición especificada.
	 */
	@Cacheable("playerCompetitionTopYellowCards")
	public List<PlayerCompetitionStatistics> getCompetitionTopYellowCards(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+apiHost+"/players/topyellowcards?league="+competitionId+"&season="+season;
		JsonNode responseData=doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try{
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
		
					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=new Player();
					player.setId(playerBasicInfo.path("id").asInt());
					player.setName(playerBasicInfo.path("name").asText());
					player.setPhoto(playerBasicInfo.path("photo").asText());
					playerCompetitionStatistics.setPlayer(player);
					
					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=new Team();
					playerTeam.setId(playerTeamData.path("id").asInt());
					playerTeam.setName(playerTeamData.path("name").asText());
					playerTeam.setLogo(playerTeamData.path("logo").asText());;
					playerCompetitionStatistics.setTeam(playerTeam);
	
					playerCompetitionStatistics.setGamesAppearances(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setYellowCards(playerAllStatistics.path("cards").path("yellow").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return competitionPlayersStatistics;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Devuelve los jugadores que más tarjetas rojas hayan recibido en una competición
	 * con un ID especificado.
	 * @param competitionId Es el ID de la competición de la que vamos a sacar los datos.
	 * @return Una lista de jugadores con sus estadísticas en una competición especificada.
	 */
	@Cacheable("playerCompetitionTopRedCards")
	public List<PlayerCompetitionStatistics> getCompetitionTopRedCards(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+apiHost+"/players/topredcards?league="+competitionId+"&season="+season;
		JsonNode responseData=doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try{
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
		
					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=new Player();
					player.setId(playerBasicInfo.path("id").asInt());
					player.setName(playerBasicInfo.path("name").asText());
					player.setPhoto(playerBasicInfo.path("photo").asText());
					playerCompetitionStatistics.setPlayer(player);
					
					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=new Team();
					playerTeam.setId(playerTeamData.path("id").asInt());
					playerTeam.setName(playerTeamData.path("name").asText());
					playerTeam.setLogo(playerTeamData.path("logo").asText());;
					playerCompetitionStatistics.setTeam(playerTeam);
	
					playerCompetitionStatistics.setGamesAppearances(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setRedCards(playerAllStatistics.path("cards").path("red").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return competitionPlayersStatistics;
			
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Función que obtiene todas las rondas de una competición (tanto jornadas como 
	 * fases eliminatorias).
	 * @param competitionId EL ID de la competición de la que se quiere sacar la información.
	 * @return Una lista de todas las rondas/jornadas de una competición.
	 */
	@Cacheable("competitionAllRounds")
	public List<String>getCompetitionAllRounds(Integer competitionId){
		List<String>competitionRounds=new ArrayList<>();
		String url="https://"+apiHost+"/fixtures/rounds?league="+competitionId+"&season="+season;
		JsonNode responseData=doRequest(url);
		if(responseData!=null && responseData.isArray()){
			try{
				for(JsonNode competitionRound:responseData){
					competitionRounds.add(competitionRound.asText());
				}
				return competitionRounds;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Cacheable("competitionRoundMatchesSummary")
	public List<Match>getCompetitionRoundMatchesSummary(Integer competitionId, String round){
		List<Match> competitionRoundMatches=new ArrayList<>();
		TimeZone timeZone=TimeZone.getDefault(); 
		String timeZoneId=timeZone.getID();
		
		String url="https://"+apiHost+"/fixtures?league="+competitionId+"&season="+season+"&round="+round+"&timezone="+timeZoneId;
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
					//La fecha se transforma en un formato adaptado según el idioma del equipo.
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
	
					competitionRoundMatches.add(match);
				}
				return competitionRoundMatches;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
