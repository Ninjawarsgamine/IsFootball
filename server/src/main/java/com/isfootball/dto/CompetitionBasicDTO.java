package com.isfootball.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la información básica de una competición.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompetitionBasicDTO implements Serializable{

    private static final long serialVersionUID = -2872835842653518063L;
	
	private Integer id;
	private String name;
	private String logo;
}
