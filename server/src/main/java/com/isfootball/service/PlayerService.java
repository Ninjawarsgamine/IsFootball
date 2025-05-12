package com.isfootball.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.config.AppConfig;
import com.isfootball.dto.PlayerDTO;
import com.isfootball.dto.PlayerSimpleDTO;
import com.isfootball.dto.TeamPlayerCareerDTO;
import com.isfootball.mapper.PlayerBasicMapper;
import com.isfootball.mapper.PlayerMapper;
import com.isfootball.mapper.TeamPlayerCareerMapper;
import com.isfootball.model.Competition;
import com.isfootball.model.Country;
import com.isfootball.model.Player;
import com.isfootball.model.PlayerCompetitionStatistics;
import com.isfootball.model.Team;
import com.isfootball.model.TeamPlayerCareer;
import com.isfootball.utils.Utils;

@Service
public class PlayerService {
	
    private final AppConfig appConfig;
    private final PlayerMapper playerMapper;
    private final TeamPlayerCareerMapper teamPlayerCareerMapper;
    private final Utils utils;

	/**
	 * Es el constructor de "PlayerService".
	 */
    @Autowired
	public PlayerService(AppConfig appConfig,ObjectMapper objectMapper, 
    PlayerMapper playerMapper, PlayerBasicMapper playerBasicMapper, 
    TeamPlayerCareerMapper teamPlayerCareerMapper, Utils utils) {
		this.appConfig = appConfig;
        this.playerMapper = playerMapper;
        this.teamPlayerCareerMapper=teamPlayerCareerMapper;
        this.utils=utils;
	}

    /**
     * Función que obtiene la información de un jugador por su ID.
     * @param playerId Es el ID del jugador
     * @return Un objeto "Player" con toda la información de un jugador con un ID especificado.
     */
    @Cacheable("playerById")
    public PlayerDTO getPlayerById(Integer playerId){
        Player player=new Player();
        String url="https://"+appConfig.getApiHost()+"/players?id="+playerId+"&season="+appConfig.getSeason();
        JsonNode responseData=utils.doRequest(url);
        //PlayerDTO
        if(responseData!=null && responseData.isArray()){
            try {
                JsonNode playerAllInfo=responseData.get(0);

                JsonNode playerInfo=responseData.get(0).path("player");
                player.setId(playerInfo.get("id").asInt());
                player.setName(playerInfo.get("name").asText());

                player.setFirstname(playerInfo.get("firstname").asText());
                player.setLastname(playerInfo.get("lastname").asText());
                
                player.setHeight(playerInfo.path("height").asText());
                player.setWeight(playerInfo.path("weight").asText());
                player.setIsInjured(playerInfo.path("injured").asBoolean());
                player.setAge(playerInfo.path("age").asInt());

                String birthday = playerInfo.path("birth").path("date").asText();
                LocalDate birthdayDate = LocalDate.parse(birthday);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                player.setBirthday(birthdayDate.format(formatter));
                
                Country nationality=new Country();
                String urlCountry="https://"+appConfig.getApiHost()+"/countries?name="+playerInfo.get("nationality").asText();
                JsonNode countryInfo=utils.doRequest(urlCountry);
                nationality.setName(countryInfo.get(0).path("name").asText());
                nationality.setFlag(countryInfo.get(0).path("flag").asText());
                player.setNationality(nationality);
                
                player.setPosition(playerInfo.path("position").asText());
                player.setPhoto(playerInfo.path("photo").asText());
                player.setPosition(playerAllInfo.path("statistics").get(0).
                path("games").path("position").asText());

                JsonNode playerTeamInfo=playerAllInfo.path("statistics").get(0).
                path("team");

                Team playerTeam=utils.parseTeamBasic(playerTeamInfo);
                player.setPlayerTeam(playerTeam);

                JsonNode playerCompetitionsStatisticsInfo=playerAllInfo.path("statistics");
                List<PlayerCompetitionStatistics>playerCompetitionsStatistics=new ArrayList<>();

                for(JsonNode playerCompetitionStatisticsInfo: playerCompetitionsStatisticsInfo ){

                    PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();
                    
                    JsonNode teamInfo=playerCompetitionStatisticsInfo.path("team");
                    Team team=utils.parseTeamBasic(teamInfo);
                    playerCompetitionStatistics.setTeam(team);
            
                    JsonNode competitionInfo=playerCompetitionStatisticsInfo.path("league");
                    Competition competition=utils.parseCompetitionBasic(competitionInfo);
                    playerCompetitionStatistics.setCompetition(competition);
             

                    JsonNode gamesInfo=playerCompetitionStatisticsInfo.path("games");
                    playerCompetitionStatistics.setGamesAppearences(gamesInfo.path("appearences").asInt());
                    playerCompetitionStatistics.setGamesLineups(gamesInfo.path("lineups").asInt());
                    playerCompetitionStatistics.setGamesMinutes(gamesInfo.path("minutes").asInt());
                    playerCompetitionStatistics.setGamesRating(gamesInfo.path("rating").asDouble());
                    
                    JsonNode substitutesInfo=playerCompetitionStatisticsInfo.path("substitutes");
                    playerCompetitionStatistics.setSubstitutesIn(substitutesInfo.path("in").asInt());
                    playerCompetitionStatistics.setSubstitutesOut(substitutesInfo.path("out").asInt());
                    playerCompetitionStatistics.setSubstitutesBench(substitutesInfo.path("bench").asInt());

                    JsonNode shotsInfo=playerCompetitionStatisticsInfo.path("shots");
                    playerCompetitionStatistics.setTotalShots(shotsInfo.path("total").asInt());
                    playerCompetitionStatistics.setShotsOn(shotsInfo.path("on").asInt());
                    
                    JsonNode goalsInfo=playerCompetitionStatisticsInfo.path("goals");
                    playerCompetitionStatistics.setTotalGoals(goalsInfo.path("total").asInt());
                    playerCompetitionStatistics.setConcededGoals(goalsInfo.path("conceded").asInt());
                    playerCompetitionStatistics.setAssists(goalsInfo.path("assists").asInt());
                    playerCompetitionStatistics.setSaves(goalsInfo.path("saves").asInt());

                    JsonNode passesInfo=playerCompetitionStatisticsInfo.path("passes");
                    playerCompetitionStatistics.setTotalPasses(passesInfo.path("total").asInt());
                    playerCompetitionStatistics.setKeyPasses(passesInfo.path("key").asInt());
                    playerCompetitionStatistics.setAccuracyPasses(passesInfo.path("accuracy").asInt());
                    
                    JsonNode tacklesInfo=playerCompetitionStatisticsInfo.path("tackles");
                    playerCompetitionStatistics.setTotalTackles(tacklesInfo.path("total").asInt());
                    playerCompetitionStatistics.setBlocksTackles(tacklesInfo.path("blocks").asInt());
                    playerCompetitionStatistics.setInterceptionsTackles(tacklesInfo.path("interceptions").asInt());

                    JsonNode duelsInfo=playerCompetitionStatisticsInfo.path("duels");
                    playerCompetitionStatistics.setTotalDuels(duelsInfo.path("total").asInt());
                    playerCompetitionStatistics.setDuelsWon(duelsInfo.path("won").asInt());

                    JsonNode dribblesInfo=playerCompetitionStatisticsInfo.path("dribbles");
                    playerCompetitionStatistics.setDribblesAttempts(dribblesInfo.path("attempts").asInt());
                    playerCompetitionStatistics.setDribblesSuccess(dribblesInfo.path("success").asInt());

                    JsonNode foulsInfo=playerCompetitionStatisticsInfo.path("fouls");
                    playerCompetitionStatistics.setFoulsCommitted(foulsInfo.path("committed").asInt());
                    playerCompetitionStatistics.setFoulsDrawn(foulsInfo.path("drawn").asInt());

                    JsonNode cardsInfo=playerCompetitionStatisticsInfo.path("cards");
                    playerCompetitionStatistics.setYellowCards(cardsInfo.path("yellow").asInt());
                    playerCompetitionStatistics.setYellowRedCards(cardsInfo.path("yellowred").asInt());
                    playerCompetitionStatistics.setRedCards(cardsInfo.path("red").asInt());

                    JsonNode penaltiesInfo=playerCompetitionStatisticsInfo.path("penalty");
                    playerCompetitionStatistics.setPenaltiesWon(penaltiesInfo.path("won").asInt());
                    playerCompetitionStatistics.setPenaltiesCommited(penaltiesInfo.path("committed").asInt());
                    playerCompetitionStatistics.setPenaltiesScored(penaltiesInfo.path("scored").asInt());
                    playerCompetitionStatistics.setPenaltiesMissed(penaltiesInfo.path("missed").asInt());
                    playerCompetitionStatistics.setPenaltiesSaved(penaltiesInfo.path("saved").asInt());

                    playerCompetitionsStatistics.add(playerCompetitionStatistics);
                }
                player.setPlayerCompetitionStatistics(playerCompetitionsStatistics);

                return playerMapper.toPlayerDTO(player);
            }catch(Exception e) {
                e.printStackTrace();
            }
    
        }
        return null;
    }


    /**
     * Función que devuelve devuelve una lista de jugadores cuyo nombre/apellido coincida con
     * el especificado.
     * @param playerName Es el nombre/apellido que se va a utilizar para la búsqueda.
     * @return Una lista de jugadores cuyo nombre/apellido coincida con el especificado.
     */
    @Cacheable("playersByName")
    public List<PlayerSimpleDTO> getPlayersByName(String playerName){
        List<Player>players=new ArrayList<>();

        playerName=utils.decodeSpaces(playerName);
		if(playerName.length()<3) {
			return null;
		}

        String url="https://"+appConfig.getApiHost()+"/players/profiles?search="+playerName;
        JsonNode responseData=utils.doRequest(url);
        if(responseData!=null && responseData.isArray()){
            try {
                for(JsonNode playerData: responseData) {
                    
                    JsonNode playerInfo=playerData.path("player");
                    Player player=utils.parsePlayerBasic(playerInfo);
                    Country nationality=new Country();
                    nationality.setName(playerInfo.get("nationality").asText());
                    player.setNationality(nationality);
                    players.add(player);
                }
                return playerMapper.toPlayerSimpleDTOList(players);
            }catch(Exception e) {
                e.printStackTrace();
            }
    
        }
        return null;
    }

    /**
     * Función que devuelve una lista con todos los equipos en los que ha estado un jugador con 
     * un ID especificado, teniendo cada uno una lista con las temporadas que el jugador ha estado
     * en cada equipo.
     * @param playerId Es el ID del jugdor.
     * @return Una lista con todos los equipos en los que ha estado un jugador con 
     * un ID especificado, teniendo cada uno una lista con las temporadas que el jugador ha estado
     * en cada equipo.
     */
    @Cacheable("playerCareer")
    public List<TeamPlayerCareerDTO> getPlayerCareer(Integer playerId){
        List<TeamPlayerCareer>teamsPlayerCareer=new ArrayList<>();
        String url="https://"+appConfig.getApiHost()+"/players/teams?player="+playerId;
        JsonNode responseData=utils.doRequest(url);
        try{
            if(responseData!=null && responseData.isArray()){
                for(JsonNode teamPlayerCareerInfo: responseData){
                    TeamPlayerCareer teamPlayerCareer=new TeamPlayerCareer();

                    JsonNode teamInfo=teamPlayerCareerInfo.path("team");
                    Team team=utils.parseTeamBasic(teamInfo);

                    teamPlayerCareer.setTeam(team);

                    List<Integer>seasons=new ArrayList<>();

                    for(JsonNode seasonInfo: teamPlayerCareerInfo.path("seasons")){
                        if(seasonInfo.asInt()<=Integer.valueOf(appConfig.getSeason())+1){
                            seasons.add(seasonInfo.asInt());
                        }
                        //Solo añadimos las temporadas que estén por debajo de la variable
                        //de entorno "season".
                    }   
                    teamPlayerCareer.setSeasons(seasons);
                    teamsPlayerCareer.add(teamPlayerCareer);
                }

                return teamPlayerCareerMapper.toTeamPlayerCareerDTOList(teamsPlayerCareer);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
