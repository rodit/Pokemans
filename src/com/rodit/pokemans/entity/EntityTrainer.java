package com.rodit.pokemans.entity;

import java.util.ArrayList;

import com.rodit.pokemans.item.Inventory;
import com.rodit.pokemans.pokeman.Pokeman;

public class EntityTrainer extends Entity {

	public int money = 0;
	public Inventory inventory;
	public ArrayList<Pokeman> pokemans;
	
	public EntityTrainer(){
		super();
		inventory = new Inventory();
		pokemans = new ArrayList<Pokeman>();
	}
}
