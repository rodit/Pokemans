package com.rodit.pokemans.pokeman;

import java.util.HashMap;

public class Pokeman {

	public String id;
	public String name;
	public int level;
	public String icon;
	public HashMap<String, String> animations;
	public BattleType type;
	
	public PokemanStats stats;
	
	public Pokeman(){
		name = "Unknown Pokeman";
	}
}
