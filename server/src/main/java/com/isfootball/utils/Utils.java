package com.isfootball.utils;

public class Utils {

	/**
	 * Función que adapta un texto para que sea apto en una URL.
	 * 
	 * @param text El texto que vamos a adaptar
	 * @return "text" pero con los los espacios laterales eliminados y los 
	 * espacios entre palabras cambiados por "%20".
	 */
	public static String encodeSpaces(String text) {
		if(text==null) {
			return null;
		}
		return text.trim().replace(" ", "%20");
	}
}
