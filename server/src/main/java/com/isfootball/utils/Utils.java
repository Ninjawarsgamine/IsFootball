package com.isfootball.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.isfootball.model.Competition;
import com.isfootball.model.HomeAwayStats;
import com.isfootball.model.HomeAwayTotalStats;
import com.isfootball.model.Match;
import com.isfootball.model.Team;

public class Utils {

	/**
	 * Función que adapta textos de una URL a un texto normal oconvirtiendo "%20" en espacios normales
	 *  y recortando los espacios laterales.
	 * 
	 * @param text El texto codificado desde una URL que vamos a adaptar
	 * @return "text" pero con los "%20" cambiados por " " y los espacios laterales recortados.
	 */
	public static String decodeSpaces(String text) {
		if(text==null) {
			return null;
		}
		return text.trim().replace("%20", " ");
	}

	 /**
     * Esta función convierte los datos de un JsonNode a un objeto "HomeAwayTotalStats".
     * 
     * @param matchesInfo Es la parte de un JSON que tiene datos de partidos que dentro tienen 
     * "home", "away" y "total".
     * @return Un objeto "HomeAwayTotalStats" con los datos de un JSON.
     */
    public static HomeAwayTotalStats parseHomeAwayTotalStats(JsonNode matchesInfo){
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
    public static HomeAwayStats parseHomeAwayStats(JsonNode matchesInfo){
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
    public static Match parseMatch(JsonNode matchData){
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
