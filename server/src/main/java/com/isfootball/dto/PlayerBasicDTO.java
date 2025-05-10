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
public class PlayerBasicDTO implements Serializable{
	
    private static final long serialVersionUID = -3698580840553841251L;
	
	private Integer id;
	private String name;
	private String position;
	private String photo;
}
