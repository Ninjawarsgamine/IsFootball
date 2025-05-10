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
public class TeamBasicDTO implements Serializable {
    
    private static final long serialVersionUID = -3128428684260404898L;
    
	private Integer id;
    private String name;
    private String logo;
}