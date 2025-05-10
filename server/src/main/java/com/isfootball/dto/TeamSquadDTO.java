package com.isfootball.dto;

import java.io.Serializable;
import java.util.List;

import com.isfootball.model.Coach;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamSquadDTO implements Serializable {
    
    private static final long serialVersionUID = -6267435500543936881L;
    
	private Coach coach;
    private List<PlayerBasicDTO> players;
    
}
