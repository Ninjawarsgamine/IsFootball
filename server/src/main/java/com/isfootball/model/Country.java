package com.isfootball.model;

import java.io.Serializable;

/**
 * Esta clase contiene toda la información sobre un país.
 */
public class Country implements Serializable {

	private static final long serialVersionUID = -3961331635464484795L;
	
	private String name;
    private String code;
    private String flag;
    
    /**
     * Constructor para crear un país con sus parámetros correspondientes.
     * @param name El nombre del país en inglés.
     * @param code El código identificador del país.
     * @param flag La URL de la imagen de la bandera del país.
     */
    public Country(String name, String code, String flag) {
		super();
		this.name = name;
		this.code = code;
		this.flag = flag;
	}
    /**
     * Constructor para crear una instancia vacía del país.
     */
    public Country() {
    	
	}

	/**
     * Devuelve el nombre del país en inglés.
     * 
     * @return El nombre del país en inglés.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del país en inglés.
     * 
     * @param name El nombre del país en inglés.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el código que identifica al país.
     * 
     * @return El código que identifica al país.
     */
    public String getCode() {
        return code;
    }

    /**
     * Establece el código que identifica al país.
     * 
     * @param code El código que identifica al país.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtiene la bandera del país.
     * 
     * @return La URL de la bandera del país.
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Establece la URL de la bandera del país.
     * 
     * @param flag La URL de la bandera del país.
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
