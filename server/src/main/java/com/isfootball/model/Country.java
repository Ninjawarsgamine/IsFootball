package com.isfootball.model;
/**
 * Esta clase contiene toda la información sobre un país.
 */
public class Country {
	private String name;
	private String code;
	private String flag;

	/**
	 * Devuelve el nombre del país en inglés.
	 * @return El nombre del país en inglés.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Obtiene el código que identifica al país.
	 * @return El código que identifica al país.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Obtiene la bandera del país.
	 * @return La URL de la bandera del país.
	 */
	public String getFlag() {
		return flag;
	}
}
