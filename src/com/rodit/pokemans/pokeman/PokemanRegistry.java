package com.rodit.pokemans.pokeman;

import java.util.ArrayList;

import com.rodit.pokemans.Game;

public class PokemanRegistry {

	public static void init(PokemanRegistry pokemans) {
		String pokxml = new String(Game.readAsset("pokeman/pokemans.xml"));
	}

	private ArrayList<Pokeman> pokemans;
	
	public PokemanRegistry(){
		pokemans = new ArrayList<Pokeman>();
	}
	
	public void reg(Pokeman p){
		pokemans.add(p);
	}
	
	public void unreg(Pokeman p){
		pokemans.remove(p);
	}
	
	public Pokeman get(int id){
		for(Pokeman p : pokemans){
			if(p.id == id)return p;
		}
		return null;
	}
	
	public Pokeman get(String name){
		for(Pokeman p : pokemans){
			if(p.name.equals(name))return p;
		}
		return null;
	}
}
