package com.isfootball.utils;

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
}
