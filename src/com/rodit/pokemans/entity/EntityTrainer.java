package com.rodit.pokemans.entity;

import java.util.ArrayList;

import com.rodit.pokemans.item.Inventory;
import com.rodit.pokemans.pokeman.Pokeman;
import com.rodit.pokemans.pokeman.effect.Effect;
import com.rodit.pokemans.script.ScriptRef;

public class EntityTrainer extends Entity {

	public int money = 0;
	public Inventory inventory;
	public ArrayList<Pokeman> pokemans;
	public PokemanController pokemanController;
	
	public EntityTrainer(){
		super();
		inventory = new Inventory();
		pokemans = new ArrayList<Pokeman>();
		pokemanController = new PokemanController();
	}
	
	@Override
	public void update(){
		super.update();
		for(int i = 0; i < pokemans.size(); i++){
			pokemans.get(i).update();
			for(Effect e : pokemanController.getEffects(i)){
				e.update(this, i);
			}
		}
	}
	
	@ScriptRef
	public void hurt(int pIndex, float amount){
		pokemanController.addHealth(pIndex, -amount);
	}
	
	@ScriptRef
	public void consume(int iIndex){
		inventory.consume(inventory.get(iIndex));
	}
}
