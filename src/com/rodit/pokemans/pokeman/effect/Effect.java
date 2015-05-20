package com.rodit.pokemans.pokeman.effect;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.entity.EntityTrainer;
import com.rodit.pokemans.script.ScriptParser;
import com.rodit.pokemans.script.VariableManager;

public class Effect {

	public String name;
	public float delay;
	public String script;

	private long lastUpdate = System.currentTimeMillis();
	
	public Effect(){
		name = "Unknown effect";
		delay = 1.5F;
		script = "";
	}
	
	public void update(EntityTrainer trainerRef, int tIndex){
		if((float)Game.getTime() - (float)lastUpdate >= delay){
			if(script != null){
				if(script != "" && !script.isEmpty()){
					VariableManager args = new VariableManager();
					args.addVar("trainer", trainerRef);
					args.addVar("target", tIndex);
					ScriptParser.run(script, args);
				}
			}
		}
	}
}
