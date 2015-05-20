package com.rodit.pokemans.pokeman;

import java.util.ArrayList;
import java.util.HashMap;

import com.rodit.pokemans.pokeman.ability.Ability;

public class Pokeman {

	public int id;
	public String name;
	public String icon;
	public HashMap<String, String> animations;
	public BattleType type;
	public int evolvesto;
	public int evolvesat;
	public ArrayList<Ability> defaultAbilities;
	
	public PokemanStats stats = new PokemanStats();
	
	public Pokeman(){
		id = 0;
		name = "Unknown Pokeman";
		icon = "undefined";
		animations = new HashMap<String, String>();
		type = BattleType.NORMAL;
		evolvesto = -1;
		evolvesat = -1;
		defaultAbilities = new ArrayList<Ability>();
	}
	
	public void update(){
		
	}
}
