package com.isfootball.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.config.AppConfig;
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

	private AppConfig appConfig;
	private final ObjectMapper objectMapper;
	private final TeamMapper teamMapper;
	private final TeamCompetitionStatisticsMapper teamCompetitionStatisticsMapper;
	private final MatchMapper matchMapper;
	private final Utils utils;

	@Autowired
	public TeamService(AppConfig appConfig, ObjectMapper objectMapper, TeamMapper teamMapper,
	TeamCompetitionStatisticsMapper teamCompetitionStatisticsMapper, MatchMapper matchMapper,
	Utils utils) {
		this.appConfig=appConfig;
		this.objectMapper = objectMapper;
		this.teamMapper = teamMapper;
		this.teamCompetitionStatisticsMapper = teamCompetitionStatisticsMapper;
		this.matchMapper = matchMapper;
		this.utils=utils;
	}

	/**
	 * Función que obtiene los datos básicos de un equipo según un ID especificado.
	 * 
	 * @param teamId El ID del equipo que se va a buscar.
	 * @return Un objeto Team con la información básica de un equipo que coincida
	 *         con
	 *         el ID especificado.
	 */
	@Cacheable("teamByNameAndById")
	public TeamDTO getTeamByNameAndId(String teamName, Integer teamId) {
		teamName = utils.decodeSpaces(teamName);
		// Quitamos la codificación del nombre para que sea entendible por la API.

		List<TeamDTO> teamsByName = getTeamsByName(teamName);
		TeamDTO team = new TeamDTO();

		try {
			for (TeamDTO teamInfo : teamsByName) {
				if (teamInfo.getId().equals(teamId)) {
					team.setId(teamInfo.getId());
					team.setName(teamInfo.getName());
					team.setLogo(teamInfo.getLogo());
					team.setFounded(teamInfo.getFounded());

					String urlCountry ="https://"+appConfig.getApiHost()+"/countries?name="+teamInfo.getCountry().getName();
					JsonNode countryData = utils.doRequest(urlCountry).get(0);
					CountryDTO country = new CountryDTO();
					country.setFlag(countryData.path("flag").asText());
					team.setCountry(country);

					Venue venueInfo = teamInfo.getVenue();
					Venue venue = new Venue();
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Función que devuelve una lista de equipos que coincidan con un nombre
	 * espcificado.
	 * 
	 * @param teamName El nombre del que se va a utilizar para realizar
	 *                 la búsqueda.
	 * @return Lista de equipos coincidentes con el nombre especificado.
	 */
	@Cacheable("teamsByName")
	public List<TeamDTO> getTeamsByName(String teamName) {
		List<Team> teams = new ArrayList<>();
		if (teamName.length() < 3) {
			return null;
		}
		String url ="https://"+appConfig.getApiHost()+"/teams?search="+teamName;
		JsonNode responseData = utils.doRequest(url);
		try {
			for (JsonNode teamData : responseData) {
				JsonNode teamInfo = teamData.path("team");
				Team team = utils.parseTeamSimple(teamInfo);
				team.setFounded(teamInfo.path("founded").asInt());

				Country country = new Country();
				country.setName(teamInfo.path("country").asText());
				team.setCountry(country);

				JsonNode venueData = teamData.path("venue");
				Venue venue = new Venue();
				venue.setId(venueData.path("id").asInt());
				venue.setName(venueData.path("name").asText());
				venue.setAddress(venueData.path("address").asText());
				venue.setImage(venueData.path("image").asText());
				venue.setCity(venueData.path("city").asText());
				venue.setCapacity(venueData.path("capacity").asInt());
				team.setVenue(venue);

				teams.add(team);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return teamMapper.toTeamDTOList(teams);
	}

	@Cacheable("teamMatches")
	public List<MatchDTO> getTeamMatches(Integer teamId) {
		List<Match> teamMatches = new ArrayList<>();

		TimeZone timeZone = TimeZone.getDefault();
		String timeZoneId = timeZone.getID();
		String url ="https://"+appConfig.getApiHost() +"/fixtures?season=" + appConfig.getSeason() + 
		"&team="+teamId+"&timezone=" + timeZoneId;

		JsonNode responseData = utils.doRequest(url);

		if (responseData != null && responseData.isArray()) {
			try {
				for (JsonNode matchData : responseData) {
					Match match = utils.parseMatch(matchData);
					Boolean sameDate=false;
					for(Match teamMatch: teamMatches){
						if(teamMatch.getDate().equals(match.getDate())){
							sameDate=true;
						}
					}
					if(!sameDate){
						teamMatches.add(match);
					}
				}
				teamMatches.sort(Comparator.comparing(Match::getZonedDateTime));
				return matchMapper.toMatchDTOList(teamMatches);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	/**
	 * Función que devuelve una lista con todas las competiciones que juega un
	 * equipo.
	 * 
	 * @param ID Es el ID del equipo.
	 * @return Una lista con todas las competiciones que juega un equipo.
	 */
	@Cacheable("teamsCompetitions")
	public List<CompetitionBasicDTO> getTeamCompetitions(Integer teamId) {
		List<CompetitionBasicDTO> teamCompetitions = new ArrayList<>();

		List<MatchDTO> teamMatches = getTeamMatches(teamId);

		Map<Integer, CompetitionBasicDTO> teamCompetitionsMap = new HashMap<>();
		try {
			for (MatchDTO match : teamMatches) {
				CompetitionBasicDTO competition = match.getCompetition();
				Integer competitionId = competition.getId();

				if (!teamCompetitionsMap.containsKey(competitionId)) {
					teamCompetitionsMap.put(competitionId, competition);
				}
				// Si el ID de la competición no está en el "teamCompetitionsMap", entonces
				// añade
				// la competición.
			}
			teamCompetitions.addAll(teamCompetitionsMap.values());

			return teamCompetitions;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Función que saca todas las estadísticas de un equipo en una competición con
	 * un ID especificado.
	 * 
	 * @param teamId        Es el ID del equipo.
	 * @param competitionId Es el ID de la competición.
	 * @return Las estadísticas de un equipo en una competición con un ID
	 *         especificado.
	 */
	@Cacheable("teamCompetitionStatistics")
	public TeamCompetitionStatisticsDTO getTeamCompetitionStatistics(Integer teamId, Integer competitionId) {
		TeamCompetitionStatistics teamCompetitionStatistics = new TeamCompetitionStatistics();
		String url ="https://"+appConfig.getApiHost()+"/teams/statistics?league="+competitionId+"&season="
		+ appConfig.getSeason()+ "&team="+teamId;

		JsonNode responseData = utils.doRequest(url);
		try {
			JsonNode matchesInfo = responseData.path("fixtures");
			// Información sobre los partidos (ganados, empatados y perdidos).
			JsonNode matchesPlayedInfo = matchesInfo.path("played");
			HomeAwayTotalStats matchesPlayed = utils.parseHomeAwayTotalStats(matchesPlayedInfo);
			teamCompetitionStatistics.setMatchesPlayed(matchesPlayed);

			JsonNode matchesWonInfo = matchesInfo.path("wins");
			HomeAwayTotalStats matchesWon = utils.parseHomeAwayTotalStats(matchesWonInfo);
			teamCompetitionStatistics.setMatchesWon(matchesWon);

			JsonNode matchesDrawnInfo = matchesInfo.path("draws");
			HomeAwayTotalStats matchesDrawn = utils.parseHomeAwayTotalStats(matchesDrawnInfo);
			teamCompetitionStatistics.setMatchesDrawn(matchesDrawn);

			JsonNode matchesLostInfo = matchesInfo.path("loses");
			HomeAwayTotalStats matchesLost = utils.parseHomeAwayTotalStats(matchesLostInfo);
			teamCompetitionStatistics.setMatchesLost(matchesLost);

			JsonNode goalsInfo = responseData.path("goals");
			// Información sobre los goles.

			JsonNode goalsForInfo = goalsInfo.path("for");
			Goal goalsFor = new Goal();

			HomeAwayTotalStats goalsForStats = utils.parseHomeAwayTotalStats(goalsForInfo.path("total"));
			goalsFor.setDistribution(goalsForStats);

			HomeAwayTotalStats averageForStats = utils.parseHomeAwayTotalStats(goalsForInfo.path("average"));
			goalsFor.setAverage(averageForStats);

			JsonNode minutesForInfo = goalsForInfo.path("minute");
			Map<String, Stat> minutesFor = objectMapper.convertValue(minutesForInfo,
					new TypeReference<Map<String, Stat>>() {
					});
			// Al tener la misma estructura que el JSON, en este caso podemos importar el
			// valor
			// de manera automática con "Jackson".

			goalsFor.setMinutes(minutesFor);

			JsonNode underOverForInfo = goalsForInfo.path("under_over");
			Map<String, UnderOver> underOverFor = objectMapper.convertValue(underOverForInfo,
					new TypeReference<Map<String, UnderOver>>() {
					});
			goalsFor.setUnderOver(underOverFor);

			teamCompetitionStatistics.setGoalsFor(goalsFor);

			JsonNode goalsAgainstInfo = goalsInfo.path("against");
			Goal goalsAgainst = new Goal();

			HomeAwayTotalStats goalsAgainstStats = utils.parseHomeAwayTotalStats(goalsAgainstInfo.path("total"));
			goalsAgainst.setDistribution(goalsAgainstStats);

			HomeAwayTotalStats averageAgainstStats = utils.parseHomeAwayTotalStats(goalsAgainstInfo.path("average"));
			goalsAgainst.setAverage(averageAgainstStats);

			JsonNode minutesAgainstInfo = goalsAgainstInfo.path("minute");
			Map<String, Stat> minutesAgainst = objectMapper.convertValue(minutesAgainstInfo,
					new TypeReference<Map<String, Stat>>() {
					});

			goalsAgainst.setMinutes(minutesAgainst);

			JsonNode underOverAgainstInfo = goalsAgainstInfo.path("under_over");
			Map<String, UnderOver> underOverAgainst = objectMapper.convertValue(underOverAgainstInfo,
					new TypeReference<Map<String, UnderOver>>() {
					});
			goalsAgainst.setUnderOver(underOverAgainst);

			teamCompetitionStatistics.setGoalsAgainst(goalsAgainst);

			JsonNode biggestInfo = responseData.path("biggest");

			JsonNode biggestStreakInfo = biggestInfo.path("streak");
			// Información del biggestStreak.

			teamCompetitionStatistics.setBiggestStreakWins(biggestStreakInfo.path("wins").asInt());
			teamCompetitionStatistics.setBiggestStreakDraws(biggestStreakInfo.path("draws").asInt());
			teamCompetitionStatistics.setBiggestStreakLoses(biggestStreakInfo.path("loses").asInt());

			JsonNode biggestWinsInfo = biggestInfo.path("wins");
			// Información del biggestWin.

			HomeAwayStats biggestWins = utils.parseHomeAwayStats(biggestWinsInfo);
			teamCompetitionStatistics.setBiggestWins(biggestWins);

			JsonNode biggestLosesInfo = biggestInfo.path("loses");
			HomeAwayStats biggestLoses = utils.parseHomeAwayStats(biggestLosesInfo);
			teamCompetitionStatistics.setBiggestLoses(biggestLoses);

			JsonNode biggestGoalsInfo = biggestInfo.path("goals");

			HomeAwayStats biggestGoalsFor = utils.parseHomeAwayStats(biggestGoalsInfo.path("for"));
			teamCompetitionStatistics.setBiggestGoalsFor(biggestGoalsFor);

			HomeAwayStats biggestGoalsAgainst = utils.parseHomeAwayStats(biggestGoalsInfo.path("against"));
			teamCompetitionStatistics.setBiggestGoalsAgainst(biggestGoalsAgainst);

			JsonNode cleanSheetInfo = responseData.path("clean_sheet");
			HomeAwayTotalStats cleanSheet = utils.parseHomeAwayTotalStats(cleanSheetInfo);
			teamCompetitionStatistics.setCleanSheet(cleanSheet);
			// Información del "clean_sheet".

			JsonNode failedToScoreInfo = responseData.path("failed_to_score");
			HomeAwayTotalStats failedToScore = utils.parseHomeAwayTotalStats(failedToScoreInfo);
			teamCompetitionStatistics.setFailedToScore(failedToScore);
			// Información de "failed_to_score".

			JsonNode penaltiesInfo = responseData.path("penalty");
			// Información de "penalty".

			JsonNode penaltiesScoredInfo = penaltiesInfo.path("scored");
			Stat penaltiesScored = new Stat();
			penaltiesScored.setTotal(penaltiesScoredInfo.path("total").asInt());
			penaltiesScored.setPercentage(penaltiesScoredInfo.path("percentage").asText());
			teamCompetitionStatistics.setPenaltiesScored(penaltiesScored);

			JsonNode penaltiesMissedInfo = penaltiesInfo.path("missed");
			Stat penaltiesMissed = new Stat();
			penaltiesMissed.setTotal(penaltiesMissedInfo.path("total").asInt());
			penaltiesMissed.setPercentage(penaltiesMissedInfo.path("percentage").asText());
			teamCompetitionStatistics.setPenaltiesMissed(penaltiesMissed);

			teamCompetitionStatistics.setTotalPenalties(penaltiesInfo.path("total").asInt());

			JsonNode linupsInfo = responseData.path("lineups");
			// Información de las "lineups".

			List<Lineup> lineups = new ArrayList<>();

			for (JsonNode lineupInfo : linupsInfo) {
				Lineup lineup = new Lineup();
				lineup.setFormation(lineupInfo.path("formation").asText());
				lineup.setMatchesPlayed(lineupInfo.path("played").asInt());
				lineups.add(lineup);
			}
			teamCompetitionStatistics.setLineups(lineups);

			JsonNode cardsInfo = responseData.path("cards");
			// Información de "cards".

			JsonNode cardsYellowInfo = cardsInfo.path("yellow");
			Map<String, Stat> cardsYellow = objectMapper.convertValue(cardsYellowInfo,
					new TypeReference<Map<String, Stat>>() {
					});

			teamCompetitionStatistics.setCardsYellow(cardsYellow);

			JsonNode cardsRedsInfo = cardsInfo.path("yellow");
			Map<String, Stat> cardsRed = objectMapper.convertValue(cardsRedsInfo,
					new TypeReference<Map<String, Stat>>() {
					});

			teamCompetitionStatistics.setCardsRed(cardsRed);

			return teamCompetitionStatisticsMapper.toTeamCompetitionStatisticsDTO(teamCompetitionStatistics);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Función que devuelve una lista de todos los equipos de una competición con un
	 * ID especificado.
	 * 
	 * @param competitionId EL ID de la competición.
	 * @return Una lista con la información básica de todos los equipos de una
	 *         competición.
	 */
	@Cacheable("competitionTeams")
	public List<TeamBasicDTO> getCompetitionTeams(Integer competitionId) {
		List<Team> competitionTeams = new ArrayList<>();
		if (competitionId == null) {
			throw new IllegalArgumentException("The competition id is null.");
		}
		if (!(competitionId instanceof Integer)) {
			throw new IllegalArgumentException("The competition id is not a Integer.");
		}
		String url = "https://" + appConfig.getApiHost() + "/teams?league=" + competitionId + "&season=" + appConfig.getSeason();
		JsonNode responseData = utils.doRequest(url);
		for (JsonNode teamInfo : responseData) {
			Team team = utils.parseTeamSimple(teamInfo.path("team"));
			competitionTeams.add(team);
		}
		return teamMapper.toTeamBasicDTOList(competitionTeams);
	}

	/**
	 * Función que obtiene la información del entrenador y una lista con la
	 * información de todos los jugadores de un equipo
	 * con un ID especificado
	 * 
	 * @param teamId ID del equipo.
	 * @return Un objeto "TeamSquadDTO" con la información de la plantilla de un
	 * equipo con un ID especificado.
	 */
	@Cacheable("teamSquad")
	public TeamSquadDTO getTeamSquad(Integer teamId) {
		Team teamSquad = new Team();
		Coach coach = new Coach();
		String url = "https://" + appConfig.getApiHost() + "/coachs?team=" + teamId;
		JsonNode responseData = utils.doRequest(url);

		try {
			if (responseData != null && responseData.isArray()) {
				JsonNode coachsData = responseData;
				for(JsonNode coachInfo: coachsData){
					JsonNode coachCareerInfo=coachInfo.path("career");
					for(JsonNode coachCareerTeamInfo: coachCareerInfo){
						if(!coachCareerTeamInfo.path("end").isNull() && LocalDate.
						parse(coachCareerTeamInfo.path("end").asText()).isBefore(LocalDate.
						of(Integer.valueOf(appConfig.getSeason()), 7, 1))){
							continue;
						}
						//Si el "end" no es nulo pero la fecha no es mayor en la que entrenador se fue es
						//de antes del 01/07/2023, entonces ese entrenador no se toene en cuenta.
						//entonces saltamos al siguiente entrenador. Esto lo hacemos para asegurarnos
						
						if(coachCareerTeamInfo.path("team").path("id").asInt()==teamId){
							if(LocalDate.parse(coachCareerTeamInfo.path("start").asText())
							.isBefore(LocalDate.parse(Integer.valueOf(appConfig.getSeason())+1+"-06-01")) && coach.getName()==null){
								//Si la fecha de inicio es de antes del 01/06/2024, entonces incluimos al entrenador.
								coach.setName(coachInfo.path("name").asText());
								coach.setPhoto(coachInfo.path("photo").asText());
								
								teamSquad.setCoach(coach);
								break;
							}
						}
					}
				}
				
				List<Player> teamPlayers = new ArrayList<>();
				String urlPlayers ="https://"+appConfig.getApiHost()+"/players?team="+teamId+ "&season="+appConfig.getSeason();
				JsonNode responseAllData = utils.getAllRequestResponse(urlPlayers);

				List<String>playersPositions=Arrays.asList("Goalkeeper", "Defender", "Midfielder", "Attacker");

				Integer pagesNumber=responseAllData.path("paging").path("total").asInt();
				for (int i=1;i<=pagesNumber;i++) {
					JsonNode responseDataPlayers=utils.doRequest(urlPlayers+"&page="+i);
					if (responseDataPlayers != null && responseDataPlayers.isArray()) {
						for (JsonNode playerData : responseDataPlayers) {
							JsonNode playerStatisticsInfo=playerData.path("statistics");
							Boolean hasAppearences=false;
							Integer totalAppeareneces=0;

							for(JsonNode statistics: playerStatisticsInfo){
								Integer appearences=0;
								if(statistics.path("games").path("appearences").asInt()>0 ){
									appearences=statistics.path("games").path("appearences").asInt();
									totalAppeareneces=totalAppeareneces+appearences;
									hasAppearences=true;
									if(statistics.path("league").path("id").asInt()==667){
										if(statistics.path("games").path("appearences").asInt()==totalAppeareneces){
											hasAppearences=false;
										}
										//Hacemos que si el número de apariciones del jugador en los
										//amistosos de clubes es igual a sus apariciones totales, 
										//entonces no cuenta, pues solo ha jugado amistosos de 
										//clubes (partidos no oficiales).
									}
									break;
								}
							}
							//Comprobamos que el jugador haya disputado al menos un partido de cualquier
							//competición (sin contar los amistosos de clubes) para que se 
							//pueda considerar parte de la plantilla del equipo (siempre y cuando 
							//no esté lesionado.).

							if(hasAppearences==true){
								JsonNode playerInfo = playerData.path("player");
	
								Player player = new Player();
								player.setId(playerInfo.path("id").asInt());
								player.setName(playerInfo.path("name").asText());
								player.setPhoto(playerInfo.path("photo").asText());
								player.setPosition(playerData.path("statistics").get(0)
								.path("games").path("position").asText());
		
								Country country = new Country();
								country.setName(playerInfo.path("nationality").asText());
								player.setNationality(country);
		
								teamPlayers.add(player);
							}
						}
					}
				}
				
				teamPlayers.sort(Comparator.comparingInt(player->{
					Integer index=playersPositions.indexOf(player.getPosition());
					return index==-1?Integer.MAX_VALUE:index;
				}));
				//Ordenamos los jugadores según la posición. 
				teamSquad.setPlayers(teamPlayers);
			}
			return teamMapper.toTeamSquadDTO(teamSquad);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}