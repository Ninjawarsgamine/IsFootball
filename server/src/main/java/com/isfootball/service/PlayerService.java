package com.isfootball.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.isfootball.model.Player;
import com.isfootball.model.PlayerCompetitionStatistics;
import com.isfootball.model.Team;
import com.isfootball.utils.Utils;

@Service
public class PlayerService {
     @Value("${api.key}")
	private String apiKey;
	
	@Value("${api.host}")
	private String apiHost;
	
	@Value("${season}")
	private String season;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	/**
	 * Es el constructor de "PlayerService".
	 */
	public PlayerService() {
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
     * Función que obtiene la información de un jugador por su ID.
     * @param playerId Es el ID del jugador
     * @return Un objeto "Player" con toda la información de un jugador con un ID especificado.
     */
    @Cacheable("playerById")
    public Player getPlayerById(Integer playerId){
        Player player=new Player();
        String url="https://"+apiHost+"/players?id="+playerId+"&season="+season;
        JsonNode responseData=doRequest(url);
        
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
                String urlCountry="https://"+apiHost+"/countries?name="+playerInfo.get("nationality").asText();
                JsonNode countryInfo=doRequest(urlCountry);
                nationality.setName(countryInfo.get(0).path("name").asText());
                nationality.setFlag(countryInfo.get(0).path("flag").asText());
                player.setNationality(nationality);
                
                player.setPosition(playerInfo.path("position").asText());
                player.setPhoto(playerInfo.path("photo").asText());
                player.setPosition(playerAllInfo.path("statistics").get(0).
                path("games").path("position").asText());

                JsonNode playerTeamInfo=playerAllInfo.path("statistics").get(0).
                path("team");

                Team playerTeam=new Team();
                playerTeam.setId(playerTeamInfo.path("id").asInt());
                playerTeam.setName(playerTeamInfo.path("name").asText());
                playerTeam.setLogo(playerTeamInfo.path("logo").asText());
                player.setPlayerTeam(playerTeam);
                
                JsonNode playerCompetitionsStatisticsInfo=playerAllInfo.path("statistics");
                List<PlayerCompetitionStatistics>playerCompetitionsStatistics=new ArrayList<>();
                
                for(JsonNode playerCompetitionStatisticsInfo: playerCompetitionsStatisticsInfo ){

                    PlayerCompetitionStatistics playerCompetitionStatistics=new PlayerCompetitionStatistics();

                    Team team=new Team();
                    
                    JsonNode teamInfo=playerCompetitionStatisticsInfo.path("team");
                    team.setId(teamInfo.path("id").asInt());
                    team.setName(teamInfo.path("name").asText());
                    team.setLogo(teamInfo.path("logo").asText());
                    playerCompetitionStatistics.setTeam(team);
                    
                    Competition competition=new Competition();
                    JsonNode competitionInfo=playerCompetitionStatisticsInfo.path("league");
                    competition.setId(competitionInfo.path("id").asInt());
                    competition.setName(competitionInfo.path("name").asText());
                    competition.setLogo(competitionInfo.path("logo").asText());
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

                return player;
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
    public List<Player>getPlayersByName(String playerName){
        List<Player>players=new ArrayList<>();

        playerName=Utils.decodeSpaces(playerName);
		if(playerName.length()<3) {
			return null;
		}

        String url="https://"+apiHost+"/players/profiles?search="+playerName;
        JsonNode responseData=doRequest(url);;
        if(responseData!=null && responseData.isArray()){
            try {
                for(JsonNode playerData: responseData) {
                   Player player=new Player();
                    
                    JsonNode playerInfo=playerData.path("player");
                    player.setId(playerInfo.get("id").asInt());
                    player.setName(playerInfo.get("name").asText());
                    
                    Country nationality=new Country();
                    nationality.setName(playerInfo.get("nationality").asText());
                    player.setNationality(nationality);
                    
                    player.setPosition(playerInfo.path("position").asText());
                    player.setPhoto(playerInfo.path("photo").asText());

                    players.add(player);
                }
                return players;
            }catch(Exception e) {
                e.printStackTrace();
            }
    
        }
        return null;
    }
}
