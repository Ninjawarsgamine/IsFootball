package com.isfootball.dto;

import java.io.Serializable;

import com.isfootball.model.Venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamDTO implements Serializable{

    private static final long serialVersionUID = 2799462121969883753L;
	
	private Integer id;
	private String name;
	private String logo;
	private String code;
	private CountryDTO country;
	private Integer founded;
	private Venue venue;

}
