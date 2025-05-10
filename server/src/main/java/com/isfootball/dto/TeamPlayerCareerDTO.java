package com.isfootball.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la limitar los campos de "TeamPlayerCareer".
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamPlayerCareerDTO implements Serializable {

    private static final long serialVersionUID = 6256727175665268654L;

	private TeamBasicDTO team;
    private List<Integer> seasons;
}
