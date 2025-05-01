package com.isfootball.model;

import java.io.Serializable;

/**
 * Clase que representa la información de un entrenador de un equipo.
 */
public class Coach implements Serializable {

    private static final long serialVersionUID = -2826680478285504605L;
    
	private Integer id;
    private String name;
    private String photo;
    private Country country;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


}
