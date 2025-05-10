package com.isfootball.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompetitionSimpleDTO implements Serializable {

    private static final long serialVersionUID = 7262152478326871344L;
	
	private Integer id;
	private String name;
	private String type;
	private String logo;
	private CountryDTO country;
}
