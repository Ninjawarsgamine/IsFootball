package com.isfootball.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.model.Coach;
import com.isfootball.model.Competition;
import com.isfootball.model.Country;
import com.isfootball.model.Goal;
import com.isfootball.model.HomeAwayStats;
import com.isfootball.model.HomeAwayTotalStats;
import com.isfootball.model.Lineup;
import com.isfootball.model.Match;
import com.isfootball.model.Player;
import com.isfootball.model.Stat;
import com.isfootball.model.Team;
import com.isfootball.model.TeamCompetitionStatistics;
import com.isfootball.model.UnderOver;
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
	 * Función que devuelve una lista con todas las competiciones que juega un equipo.
	 * @param ID Es el ID del equipo.
	 * @return Una lista con todas las competiciones que juega un equipo.
	 */
	@Cacheable("teamsCompetitions")
	public List<Competition> getTeamCompetitions(Integer teamId){
		List<Competition>teamCompetitions=new ArrayList<>();

		List<Match>teamMatches=getTeamMatches(teamId);

		Map<Integer,Competition>teamCompetitionsMap=new HashMap<>();
		try{
			for(Match match:teamMatches){
				Competition competition=match.getCompetition();
				Integer competitionId=competition.getId();

				if(!teamCompetitionsMap.containsKey(competitionId)){
					teamCompetitionsMap.put(competitionId, competition);
				}
				//Si el ID de la competición no está en el "teamCompetitionsMap", entonces añade
				//la competición.
			}
			teamCompetitions.addAll(teamCompetitionsMap.values());
			
			return teamCompetitions;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Función que saca todas las estadísticas de un equipo en una competición con 
	 * un ID especificado.
	 * @param teamId Es el ID del equipo.
	 * @param competitionId Es el ID de la competición.
	 * @return Las estadísticas de un equipo en una competición con un ID especificado.
	 */
	@Cacheable("teamCompetitionStatistics")
	public TeamCompetitionStatistics getTeamCompetitionStatistics(Integer teamId, Integer competitionId) {
	
		TeamCompetitionStatistics teamCompetitionStatistics=new TeamCompetitionStatistics();
    	String url="https://"+apiHost+"/teams/statistics?league="+competitionId+"&season="+season+"&team="+
		teamId;

    	JsonNode responseData = doRequest(url);
    	try{
			JsonNode matchesInfo=responseData.path("fixtures");
			//Información sobre los partidos (ganados, empatados y perdidos.)
			JsonNode matchesPlayedInfo=matchesInfo.path("played");
			HomeAwayTotalStats matchesPlayed=Utils.parseHomeAwayTotalStats(matchesPlayedInfo);
			teamCompetitionStatistics.setMatchesPlayed(matchesPlayed);

			JsonNode matchesWonInfo=matchesInfo.path("wins");
			HomeAwayTotalStats matchesWon=Utils.parseHomeAwayTotalStats(matchesWonInfo);
			teamCompetitionStatistics.setMatchesWon(matchesWon);

			JsonNode matchesDrawnInfo=matchesInfo.path("draws");
			HomeAwayTotalStats matchesDrawn=Utils.parseHomeAwayTotalStats(matchesDrawnInfo);
			teamCompetitionStatistics.setMatchesDrawn(matchesDrawn);

			JsonNode matchesLostInfo=matchesInfo.path("loses");
			HomeAwayTotalStats matchesLost=Utils.parseHomeAwayTotalStats(matchesLostInfo);
			teamCompetitionStatistics.setMatchesLost(matchesLost);


			JsonNode goalsInfo=responseData.path("goals");
			//Información sobre los goles.

			JsonNode goalsForInfo=goalsInfo.path("for");
			Goal goalsFor=new Goal();
			
			HomeAwayTotalStats goalsForStats=Utils.parseHomeAwayTotalStats(goalsForInfo.path("total"));
			goalsFor.setDistribution(goalsForStats);

			HomeAwayTotalStats averageForStats=Utils.parseHomeAwayTotalStats(goalsForInfo.path("average"));
			goalsFor.setAverage(averageForStats);

			JsonNode minutesForInfo=goalsForInfo.path("minute");
			Map<String,Stat>minutesFor=objectMapper.convertValue(minutesForInfo, 
			new TypeReference<Map<String,Stat>>(){});
			//Al tener la misma estructura que el JSON, en este caso podemos importar el valor 
			//de manera automática con "Jackson".

			goalsFor.setMinutes(minutesFor);

			JsonNode underOverForInfo=goalsForInfo.path("under_over");
			Map<String,UnderOver>underOverFor=objectMapper.convertValue(underOverForInfo, 
			new TypeReference<Map<String,UnderOver>>(){});	
			goalsFor.setUnderOver(underOverFor);

			teamCompetitionStatistics.setGoalsFor(goalsFor);


			JsonNode goalsAgainstInfo=goalsInfo.path("against");
			Goal goalsAgainst=new Goal();
			

			HomeAwayTotalStats goalsAgainstStats=Utils.parseHomeAwayTotalStats(goalsAgainstInfo.
			path("total"));
			goalsAgainst.setDistribution(goalsAgainstStats);

			HomeAwayTotalStats averageAgainstStats=Utils.parseHomeAwayTotalStats(goalsAgainstInfo.
			path("average"));
			goalsAgainst.setAverage(averageAgainstStats);

			JsonNode minutesAgainstInfo=goalsAgainstInfo.path("minute");
			Map<String,Stat>minutesAgainst=objectMapper.convertValue(minutesAgainstInfo, 
			new TypeReference<Map<String,Stat>>(){});
		
			goalsAgainst.setMinutes(minutesAgainst);

			JsonNode underOverAgainstInfo=goalsAgainstInfo.path("under_over");
			Map<String,UnderOver>underOverAgainst=objectMapper.convertValue(underOverAgainstInfo, 
			new TypeReference<Map<String,UnderOver>>(){});	
			goalsAgainst.setUnderOver(underOverAgainst);

			teamCompetitionStatistics.setGoalsAgainst(goalsAgainst);

			JsonNode biggestInfo=responseData.path("biggest");
			
			JsonNode biggestStreakInfo=biggestInfo.path("streak");
			//Información del biggestStreak.

			teamCompetitionStatistics.setBiggestStreakWins(biggestStreakInfo.path("wins").asInt());
			teamCompetitionStatistics.setBiggestStreakDraws(biggestStreakInfo.path("draws").asInt());
			teamCompetitionStatistics.setBiggestStreakLoses(biggestStreakInfo.path("loses").asInt());

			JsonNode biggestWinsInfo=biggestInfo.path("wins");
			//Información del biggestWin.

			HomeAwayStats biggestWins=Utils.parseHomeAwayStats(biggestWinsInfo);
			teamCompetitionStatistics.setBiggestWins(biggestWins);

			JsonNode biggestLosesInfo=biggestInfo.path("loses");
			HomeAwayStats biggestLoses=Utils.parseHomeAwayStats(biggestLosesInfo);
			teamCompetitionStatistics.setBiggestLoses(biggestLoses);

			JsonNode biggestGoalsInfo=biggestInfo.path("goals");
			
			HomeAwayStats biggestGoalsFor=Utils.parseHomeAwayStats(biggestGoalsInfo.path("for"));
			teamCompetitionStatistics.setBiggestGoalsFor(biggestGoalsFor);

			HomeAwayStats biggestGoalsAgainst=Utils.parseHomeAwayStats(biggestGoalsInfo.path("against"));
			teamCompetitionStatistics.setBiggestGoalsAgainst(biggestGoalsAgainst);

			JsonNode cleanSheetInfo=responseData.path("clean_sheet");
			HomeAwayTotalStats cleanSheet=Utils.parseHomeAwayTotalStats(cleanSheetInfo);
			teamCompetitionStatistics.setCleanSheet(cleanSheet);
			//Información del "clean_sheet".

			JsonNode failedToScoreInfo=responseData.path("failed_to_score");
			HomeAwayTotalStats failedToScore=Utils.parseHomeAwayTotalStats(failedToScoreInfo);
			teamCompetitionStatistics.setFailedToScore(failedToScore);
			//Información de "failed_to_score".

			JsonNode penaltiesInfo=responseData.path("penalty");
			//Información de "penalty".

			JsonNode penaltiesScoredInfo=penaltiesInfo.path("scored");
			Stat penaltiesScored=new Stat();
			penaltiesScored.setTotal(penaltiesScoredInfo.path("total").asInt());
			penaltiesScored.setPercentage(penaltiesScoredInfo.path("percentage").asText());
			teamCompetitionStatistics.setPenaltiesScored(penaltiesScored);

			JsonNode penaltiesMissedInfo=penaltiesInfo.path("missed");
			Stat penaltiesMissed=new Stat();
			penaltiesMissed.setTotal(penaltiesMissedInfo.path("total").asInt());
			penaltiesMissed.setPercentage(penaltiesMissedInfo.path("percentage").asText());
			teamCompetitionStatistics.setPenaltiesMissed(penaltiesMissed);

			teamCompetitionStatistics.setTotalPenalties(penaltiesInfo.path("total").asInt());

			JsonNode linupsInfo=responseData.path("lineups");
			//Información de las "lineups".

			List<Lineup>lineups=new ArrayList<>();

			for(JsonNode lineupInfo:linupsInfo){
				Lineup lineup=new Lineup();
				lineup.setFormation(lineupInfo.path("formation").asText());
				lineup.setMatchesPlayed(lineupInfo.path("played").asInt());
				lineups.add(lineup);
			}
			teamCompetitionStatistics.setLineups(lineups);

			JsonNode cardsInfo=responseData.path("cards");
			//Información de "cards".

			JsonNode cardsYellowInfo=cardsInfo.path("yellow");
			Map<String,Stat>cardsYellow=objectMapper.convertValue(cardsYellowInfo, 
			new TypeReference<Map<String,Stat>>(){});

			teamCompetitionStatistics.setCardsYellow(cardsYellow);

			JsonNode cardsRedsInfo=cardsInfo.path("yellow");
			Map<String,Stat>cardsRed=objectMapper.convertValue(cardsRedsInfo, 
			new TypeReference<Map<String,Stat>>(){});

			teamCompetitionStatistics.setCardsRed(cardsRed);


			return teamCompetitionStatistics;
		
    	} catch (Exception e) {
        	e.printStackTrace();
    	}
    	return null;
	}

	/**
	 * Función que obtiene la información de un entrenador de un equipo determinado.
	 * 
	 * @param teamId Es el ID del equipo del que se va a sacar el entrenador.
	 * @return El entrenador de un equipo con un ID especificado.
	 */
	@Cacheable("teamCoach")
	public Coach getTeamCoach(Integer teamId){
		Coach coach=new Coach();

		String url="https://"+apiHost+"/coachs?team="+teamId;
		JsonNode responseData=doRequest(url);
		try {
			if(responseData!=null && responseData.isArray()){
				JsonNode coachData=responseData.get(0);
				coach.setName(coachData.path("name").asText());
				coach.setPhoto(coachData.path("photo").asText());

				return coach;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Función que obtiene una lista de jugadores de un equipo determinado.
	 * 
	 * @param teamId Es el ID del equipo del que se van a sacar los jugadores
	 * @return Una lista de jugadores de un equipo determinado.
	 */
	@Cacheable("teamPlayers")
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
					player.setPosition(playerData.path("statistics").get(0)
					.path("games").path("position").asText());

					Country country=new Country();
					country.setName(playerInfo.path("nationality").asText());
					player.setNationality(country);

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
