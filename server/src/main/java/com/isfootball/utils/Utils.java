package com.isfootball.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.isfootball.model.HomeAwayStats;
import com.isfootball.model.HomeAwayTotalStats;

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
}
