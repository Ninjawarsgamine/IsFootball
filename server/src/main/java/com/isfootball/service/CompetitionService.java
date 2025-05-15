package com.isfootball.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.isfootball.config.AppConfig;
import com.isfootball.dto.CompetitionDTO;
import com.isfootball.dto.CompetitionSimpleDTO;
import com.isfootball.dto.MatchDTO;
import com.isfootball.dto.PlayerCompetitionStatisticsBasicDTO;

import com.isfootball.mapper.CompetitionMapper;
import com.isfootball.mapper.MatchMapper;
import com.isfootball.mapper.PlayerCompetitionStatisticsMapper;

import com.isfootball.model.Competition;
import com.isfootball.model.Country;
import com.isfootball.model.Goal;
import com.isfootball.model.HomeAwayTotalStats;
import com.isfootball.model.Match;
import com.isfootball.model.Player;
import com.isfootball.model.PlayerCompetitionStatistics;
import com.isfootball.model.Team;
import com.isfootball.model.TeamCompetitionStatistics;
import com.isfootball.utils.Utils;

@Service
public class CompetitionService {

	private AppConfig appConfig;
	private final CompetitionMapper competitionMapper;  
	private final PlayerCompetitionStatisticsMapper playerCompetitionStatisticsMapper;
	private final MatchMapper matchMapper;
	private final Utils utils;
	
	/**
	 * Es el constructor de "CompetitionService".
	 * @param competitionMapper 
	 */
	@Autowired
	public CompetitionService(AppConfig appConfig, CompetitionMapper competitionMapper,
	PlayerCompetitionStatisticsMapper playerCompetitionStatisticsMapper, 
	MatchMapper matchMapper, Utils utils) {
		this.appConfig=appConfig;
		this.competitionMapper=competitionMapper;
		this.playerCompetitionStatisticsMapper=playerCompetitionStatisticsMapper;
		this.matchMapper=matchMapper;
		this.utils=utils;
	}

	/**
	 * Función que obtiene todos los datos de una competición.
	 * @param competitionId Id de la competición.
	 * @return Los datos de la competición con el ID especificado.
	 */
	@Cacheable("competitionAllDataById")
	public CompetitionDTO getCompetitionAllDataById(Integer competitionId){
		Competition competition=new Competition();
		String urlCompetitionBasicData="https://"+appConfig.getApiHost()+"/leagues?id="+competitionId+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(urlCompetitionBasicData);
		//El "type" ("League" o "Cup") solo está disponible en "/leagues", así que necesitamos
		//esta petición.

		try{
			if(responseData!=null){
				JsonNode competitionBasicData=responseData.get(0);
				
				JsonNode competitionBasicInfo=competitionBasicData.path("league");
				competition=utils.parseCompetitionSimple(competitionBasicInfo);

				JsonNode competitionCountry=competitionBasicData.path("country");
				Country country=utils.parseCountry(competitionCountry);
				competition.setCountry(country);
				
				String url="https://"+appConfig.getApiHost()+"/standings?league="+competitionId+"&season="+appConfig.getSeason();
				JsonNode competitionAllData=utils.doRequest(url);
				if(competitionAllData!=null && !competitionAllData.isEmpty()){
					JsonNode competitionData=competitionAllData.get(0).path("league");
					
					List<TeamCompetitionStatistics> competitionTeamsStatistics=new ArrayList<>();
					for(JsonNode group: competitionData.path("standings")) {
						for(JsonNode competitionTeamStatisticsData: group){
							TeamCompetitionStatistics competitionTeamStatistics=new TeamCompetitionStatistics();
						
							competitionTeamStatistics.setRank(competitionTeamStatisticsData.path("rank").asInt());
							
							Team team=utils.parseTeamBasic(competitionTeamStatisticsData.path("team"));
							competitionTeamStatistics.setTeam(team);
			
							competitionTeamStatistics.setPoints(competitionTeamStatisticsData.path("points").asInt());
							competitionTeamStatistics.setGoalsDiff(competitionTeamStatisticsData.path("goalsDiff").asInt());
							competitionTeamStatistics.setGroup(competitionTeamStatisticsData.path("group").asText());
							competitionTeamStatistics.setForm(competitionTeamStatisticsData.path("form").asText());
							
							JsonNode competitionTeamMatchesInfo=competitionTeamStatisticsData.path("all");
							
							HomeAwayTotalStats matchesPlayed=new HomeAwayTotalStats();
							matchesPlayed.setTotal(competitionTeamMatchesInfo.path("played").asText());
							competitionTeamStatistics.setMatchesPlayed(matchesPlayed);
						
							HomeAwayTotalStats matchesWon=new HomeAwayTotalStats();
							matchesWon.setTotal(competitionTeamMatchesInfo.path("win").asText());
							competitionTeamStatistics.setMatchesWon(matchesWon);

							HomeAwayTotalStats matchesDrawn=new HomeAwayTotalStats();
							matchesDrawn.setTotal(competitionTeamMatchesInfo.path("draw").asText());
							competitionTeamStatistics.setMatchesDrawn(matchesDrawn);

							HomeAwayTotalStats matchesLost=new HomeAwayTotalStats();
							matchesLost.setTotal(competitionTeamMatchesInfo.path("lose").asText());
							competitionTeamStatistics.setMatchesLost(matchesLost);
							
							Goal goalsFor=new Goal();

							HomeAwayTotalStats goalsForDistribution=new HomeAwayTotalStats();

							goalsForDistribution.setTotal(competitionTeamMatchesInfo.
							path("goals").path("for").asText());
							goalsFor.setDistribution(goalsForDistribution);

							competitionTeamStatistics.setGoalsFor(goalsFor);
						
							Goal goalsAgainst=new Goal();

							HomeAwayTotalStats goalsAgainstDistribution=new HomeAwayTotalStats();
							goalsAgainstDistribution.setTotal(competitionTeamMatchesInfo.
							path("goals").path("against").asText());

							goalsAgainst.setDistribution(goalsAgainstDistribution);
							competitionTeamStatistics.setGoalsAgainst(goalsAgainst);
						
							competitionTeamsStatistics.add(competitionTeamStatistics);
							
						}
						competition.setTeamsCompetitionStatistics(competitionTeamsStatistics);
					}	
				}
			}
			return competitionMapper.toCompetitionDTO(competition);
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
	public CompetitionSimpleDTO getCompetitionByName(String competitionName) {
		competitionName=utils.decodeSpaces(competitionName);
		//CompetitionSimpleDTO
		String url="https://"+appConfig.getApiHost()+"/leagues?name="+competitionName+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(url);
	    
	    try {
			JsonNode competitionData=responseData.get(0);

			JsonNode competitionInfo=competitionData.path("league");
			Competition competition=utils.parseCompetitionSimple(competitionInfo);

			JsonNode competitionCountry=competitionData.path("country");
			Country country=utils.parseCountry(competitionCountry);
			competition.setCountry(country);
			
	    	return competitionMapper.toCompetitionSimpleDTO(competition);
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
	//CompetitionSimpleDTO
	@Cacheable ("competitionsByName")
	public List<CompetitionSimpleDTO> getCompetitionsByName(String competitionName){
		List<Competition> competitions=new ArrayList<>();
		competitionName=utils.decodeSpaces(competitionName);
		if(competitionName.length()<3) {
			return null;
		}
		String url="https://"+appConfig.getApiHost()+"/leagues?search="+competitionName;
		JsonNode responseData=utils.doRequest(url);
		try {
			for(JsonNode competitionAllData: responseData) {
				
				JsonNode competitionInfo=competitionAllData.path("league");
				
				Competition competition=utils.parseCompetitionBasic(competitionInfo);
				competition.setType(competitionInfo.get("type").asText());

				JsonNode competitionCountry=competitionAllData.path("country");
				Country country=utils.parseCountry(competitionCountry);
				competition.setCountry(country);
				
				JsonNode competitionSeason=competitionAllData.path("seasons");
				
				for(JsonNode s: competitionSeason) {
					if(s.path("year").asText().equals(appConfig.getSeason())) {
						competitions.add(competition);
						break;
					}
					//Solo añadimos la competición a la lista si es de la temporada 2023-2024.
				}
			}
			
			return competitionMapper.toCompetitionSimpleDTOList(competitions);
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
	public List<CompetitionSimpleDTO> getListCompetitionsByIds(Integer[]ids){
		List<Competition> competitions=new ArrayList<>();
		String url="https://"+appConfig.getApiHost()+"/leagues?season="+appConfig.getSeason();
		JsonNode allCompetitions= utils.doRequest(url);
		
		if(allCompetitions!=null && allCompetitions.isArray()) {
			List <Integer>idsList=Arrays.asList(ids);
			try {
				for(JsonNode c: allCompetitions) {
					if(idsList.contains(c.path("league").get("id").asInt())) {
	
						JsonNode competitionInfo=c.path("league");
						Competition competition=utils.parseCompetitionSimple(competitionInfo);
	
						JsonNode competitionCountry=c.path("country");
						Country country=utils.parseCountry(competitionCountry);
						competition.setCountry(country);
						
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
		return competitionMapper.toCompetitionSimpleDTOList(competitions);
	}

	/**
	 * Devuelve los jugadores que sean los máximos goleadores de una competición
	 * con un ID especificado.
	 * @param competitionId Es el ID de la competición de la que vamos a sacar los datos.
	 * @return Una lista de jugadores con sus estadísticas en una competición especificada.
	 */
	@Cacheable("playerCompetitionTopScorers")
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopScorers(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+appConfig.getApiHost()+"/players/topscorers?league="+competitionId+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(url);

		if(responseData!=null && responseData.isArray()) {
			try {
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
		
					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=utils.parsePlayerBasic(playerBasicInfo);
					playerCompetitionStatistics.setPlayer(player);

					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=utils.parseTeamBasic(playerTeamData);
					playerCompetitionStatistics.setTeam(playerTeam);
		
					playerCompetitionStatistics.setGamesAppearences(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setTotalGoals(playerAllStatistics.path("goals").path("total").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return playerCompetitionStatisticsMapper.toPlayerCompetitionStatisticsBasicDTOList(competitionPlayersStatistics);
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
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopAssistsProviders(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+appConfig.getApiHost()+"/players/topassists?league="+competitionId+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try{
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();

					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=utils.parsePlayerBasic(playerBasicInfo);
					playerCompetitionStatistics.setPlayer(player);
					
					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=utils.parseTeamBasic(playerTeamData);
					playerCompetitionStatistics.setTeam(playerTeam);
	
					playerCompetitionStatistics.setGamesAppearences(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setAssists(playerAllStatistics.path("goals").path("assists").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return playerCompetitionStatisticsMapper.toPlayerCompetitionStatisticsBasicDTOList(competitionPlayersStatistics);
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
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopYellowCards(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+appConfig.getApiHost()+"/players/topyellowcards?league="+competitionId+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try{
				for(JsonNode playersTotalInfo: responseData){
					PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
		
					JsonNode playerBasicInfo=playersTotalInfo.path("player");
					Player player=utils.parsePlayerBasic(playerBasicInfo);
					playerCompetitionStatistics.setPlayer(player);
					
					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);
					
					JsonNode playerTeamData=playerAllStatistics.path("team");
					Team playerTeam=utils.parseTeamBasic(playerTeamData);
					playerCompetitionStatistics.setTeam(playerTeam);
					playerCompetitionStatistics.setGamesAppearences(playerAllStatistics.path("games").path("appearences").asInt());
					playerCompetitionStatistics.setYellowCards(playerAllStatistics.path("cards").path("yellow").asInt());
					competitionPlayersStatistics.add(playerCompetitionStatistics);
				}
				return playerCompetitionStatisticsMapper.toPlayerCompetitionStatisticsBasicDTOList(competitionPlayersStatistics);
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
	public List<PlayerCompetitionStatisticsBasicDTO> getCompetitionTopRedCards(Integer competitionId){
		List<PlayerCompetitionStatistics>competitionPlayersStatistics=new ArrayList<>();
		String url="https://"+appConfig.getApiHost()+"/players/topredcards?league="+competitionId+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(url);
		if(responseData!=null && responseData.isArray()) {
			try{
				for(JsonNode playersTotalInfo: responseData){

					JsonNode playerAllStatistics=playersTotalInfo.path("statistics").get(0);

					if(playerAllStatistics.path("cards").path("red").asInt()>0){
						PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
						JsonNode playerBasicInfo=playersTotalInfo.path("player");
						Player player=utils.parsePlayerBasic(playerBasicInfo);
						playerCompetitionStatistics.setPlayer(player);
						
						
						
						JsonNode playerTeamData=playerAllStatistics.path("team");
						Team playerTeam=utils.parseTeamBasic(playerTeamData);
						playerCompetitionStatistics.setTeam(playerTeam);
		
						playerCompetitionStatistics.setGamesAppearences(playerAllStatistics.path("games").path("appearences").asInt());
						playerCompetitionStatistics.setRedCards(playerAllStatistics.path("cards").path("red").asInt());
						competitionPlayersStatistics.add(playerCompetitionStatistics);
					}
				}
				return playerCompetitionStatisticsMapper.toPlayerCompetitionStatisticsBasicDTOList(competitionPlayersStatistics);
			
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
		String url="https://"+appConfig.getApiHost()+"/fixtures/rounds?league="+competitionId+"&season="+appConfig.getSeason();
		JsonNode responseData=utils.doRequest(url);
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
	public List<MatchDTO>getCompetitionRoundMatchesSummary(Integer competitionId, String round){
		List<Match> competitionRoundMatches=new ArrayList<>();
		TimeZone timeZone=TimeZone.getDefault(); 
		String timeZoneId=timeZone.getID();
		
		String url="https://"+appConfig.getApiHost()+"/fixtures?league="+competitionId+"&season="+appConfig.getSeason()+"&round="+round+"&timezone="+timeZoneId;
		JsonNode responseData=utils.doRequest(url);
		if(responseData!=null && responseData.isArray()){
			try{
				for(JsonNode matchData: responseData){
					Match match=utils.parseMatch(matchData);
					competitionRoundMatches.add(match);
				}
				return matchMapper.toMatchDTOList(competitionRoundMatches);
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
	