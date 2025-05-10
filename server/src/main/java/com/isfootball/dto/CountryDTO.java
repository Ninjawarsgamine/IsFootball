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
public class CountryDTO implements Serializable{
    
    private static final long serialVersionUID = 7035633339151516099L;

	private String name;
    private String code;
    private String flag;
}