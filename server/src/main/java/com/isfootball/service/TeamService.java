package com.isfootball.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.isfootball.dto.CompetitionBasicDTO;
import com.isfootball.dto.CountryDTO;
import com.isfootball.dto.MatchDTO;
import com.isfootball.dto.TeamBasicDTO;
import com.isfootball.dto.TeamCompetitionStatisticsDTO;
import com.isfootball.dto.TeamDTO;
import com.isfootball.dto.TeamSquadDTO;

import com.isfootball.mapper.MatchMapper;
import com.isfootball.mapper.TeamCompetitionStatisticsMapper;
import com.isfootball.mapper.TeamMapper;

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
	private final TeamMapper teamMapper;
	private final TeamCompetitionStatisticsMapper teamCompetitionStatisticsMapper;
	private final MatchMapper matchMapper;

	@Autowired
    public TeamService(RestTemplate restTemplate, ObjectMapper objectMapper, TeamMapper teamMapper, 
    TeamCompetitionStatisticsMapper teamCompetitionStatisticsMapper, MatchMapper matchMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.teamMapper = teamMapper;
        this.teamCompetitionStatisticsMapper = teamCompetitionStatisticsMapper;
		this.matchMapper = matchMapper;
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
	public TeamDTO getTeamByNameAndId(String teamName, Integer teamId){
		teamName=Utils.decodeSpaces(teamName);
		//Quitamos la codificación del nombre para que sea entendible por la API.
		
		List<TeamDTO>teamsByName=getTeamsByName(teamName);
		TeamDTO team=new TeamDTO();
		//TeamDTO

		try{
			for(TeamDTO teamInfo: teamsByName){	
				if(teamInfo.getId().equals(teamId)){
					team.setId(teamInfo.getId());
					team.setName(teamInfo.getName());
					team.setLogo(teamInfo.getLogo());
					team.setFounded(teamInfo.getFounded());
					String urlCountry="https://"+apiHost+"/countries?name="+teamInfo.getCountry().getName();
					JsonNode countryData=doRequest(urlCountry).get(0);
					CountryDTO country=new CountryDTO();
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
	public List<TeamDTO>getTeamsByName(String teamName){
		//TeamDTO
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
		return teamMapper.toTeamDTOList(teams);
	}

	@Cacheable("teamMatches")
	public List<MatchDTO>getTeamMatches(Integer teamId){
		List<Match> teamMatches=new ArrayList<>();
		//Match no va a tener DTO
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
				return matchMapper.toMatchDTOList(teamMatches);
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
	public List<CompetitionBasicDTO> getTeamCompetitions(Integer teamId){
		List<CompetitionBasicDTO>teamCompetitions=new ArrayList<>();

		List<MatchDTO>teamMatches=getTeamMatches(teamId);

		Map<Integer,CompetitionBasicDTO>teamCompetitionsMap=new HashMap<>();
		try{
			for(MatchDTO match:teamMatches){
				CompetitionBasicDTO competition=match.getCompetition();
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
	//TeamCompetitionStatisticsDTO
	@Cacheable("teamCompetitionStatistics")
	public TeamCompetitionStatisticsDTO getTeamCompetitionStatistics(Integer teamId, Integer competitionId) {
		//TeamCompetitionStatisticsDTO
		TeamCompetitionStatistics teamCompetitionStatistics=new TeamCompetitionStatistics();
    	String url="https://"+apiHost+"/teams/statistics?league="+competitionId+"&season="+season+"&team="+
		teamId;

    	JsonNode responseData = doRequest(url);
    	try{
			JsonNode matchesInfo=responseData.path("fixtures");
			//Información sobre los partidos (ganados, empatados y perdidos).
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


			return teamCompetitionStatisticsMapper.toTeamCompetitionStatisticsDTO(teamCompetitionStatistics);
		
    	} catch (Exception e) {
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
    public List<TeamBasicDTO> getCompetitionTeams(Integer competitionId){
		//TeamBasicDTO
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
            competitionTeams.add(team);
        }
        return teamMapper.toTeamBasicDTOList(competitionTeams);
	}

	/**
	 * Función que obtiene la información del entrenador y una lista con la información de todos los jugadores de un equipo
	 * con un ID especificado
	 * @param teamId ID del equipo.
	 * @return Un objeto "TeamSquadDTO" con la información de la plantilla de un equipo con un ID especificado.
	 */
	@Cacheable("teamSquad")
	public TeamSquadDTO getTeamSquad(Integer teamId){
		Team teamSquad=new Team();
		Coach coach=new Coach();

		String url="https://"+apiHost+"/coachs?team="+teamId;
		JsonNode responseData=doRequest(url);
		try {
			if(responseData!=null && responseData.isArray()){
				JsonNode coachData=responseData.get(0);
				coach.setName(coachData.path("name").asText());
				coach.setPhoto(coachData.path("photo").asText());
				teamSquad.setCoach(coach);
				

				List<Player>teamPlayers=new ArrayList<>();

				String urlPlayers="https://"+apiHost+"/players?team="+teamId+"&season="+season;
				System.out.println(urlPlayers);
				JsonNode responseDataPlayers=doRequest(urlPlayers);
				if(responseDataPlayers!=null && responseDataPlayers.isArray()){
					for(JsonNode playerData: responseDataPlayers){
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
					};
					teamSquad.setPlayers(teamPlayers);
				}
			}
			return teamMapper.toTeamSquadDTO(teamSquad);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
/*
 * 
 * : Completed initialization in 2 ms
java.lang.NullPointerException: Cannot invoke "com.fasterxml.jackson.databind.JsonNode.path(String)" because the return value of "com.fasterxml.jackson.databind.JsonNode.get(int)" is null
	at com.isfootball.service.PlayerService.getPlayerById(PlayerService.java:120)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.cache.interceptor.CacheInterceptor.lambda$invoke$0(CacheInterceptor.java:55)
	at org.springframework.cache.interceptor.CacheAspectSupport.invokeOperation(CacheAspectSupport.java:431)
	at org.springframework.cache.interceptor.CacheAspectSupport.evaluate(CacheAspectSupport.java:601)
	at org.springframework.cache.interceptor.CacheAspectSupport.execute(CacheAspectSupport.java:448)
	at org.springframework.cache.interceptor.CacheAspectSupport.execute(CacheAspectSupport.java:410)
	at org.springframework.cache.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:65)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:728)
	at com.isfootball.service.PlayerService$$SpringCGLIB$$0.getPlayerById(<generated>)
	at com.isfootball.controller.PlayerController.getPlayerById(PlayerController.java:39)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:258)
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:191)
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:986)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:891)
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089)
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979)
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903)
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:116)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344)
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:398)
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:903)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1740)
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1189)
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:658)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)
	at java.base/java.lang.Thread.run(Thread.java:1583)
java.lang.NullPointerException: Cannot invoke "com.fasterxml.jackson.databind.JsonNode.path(String)" because the return value of "com.fasterxml.jackson.databind.JsonNode.get(int)" is null
	at com.isfootball.service.PlayerService.getPlayerById(PlayerService.java:99)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.cache.interceptor.CacheInterceptor.lambda$invoke$0(CacheInterceptor.java:55)
	at org.springframework.cache.interceptor.CacheAspectSupport.invokeOperation(CacheAspectSupport.java:431)
	at org.springframework.cache.interceptor.CacheAspectSupport.evaluate(CacheAspectSupport.java:601)
	at org.springframework.cache.interceptor.CacheAspectSupport.execute(CacheAspectSupport.java:448)
	at org.springframework.cache.interceptor.CacheAspectSupport.execute(CacheAspectSupport.java:410)

 */