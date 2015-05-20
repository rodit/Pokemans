package com.rodit.pokemans.map;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.Util;
import com.rodit.pokemans.entity.Entity;
import com.rodit.pokemans.entity.EntityTrainer;
import com.rodit.pokemans.script.ScriptParser;
import com.rodit.pokemans.script.ScriptRef;
import com.rodit.pokemans.script.VariableManager;

public class Map {

	private String showName;
	private ArrayList<Entity> entities;
	private float spawnX = 0;
	private float spawnY = 0;
	private String base64Img = "";
	private ArrayList<RectF> collisions;
	private String script = "";

	public Map(){
		entities = new ArrayList<Entity>();
		collisions = new ArrayList<RectF>();
	}

	public String getShowName(){
		return showName;
	}

	public void setShowName(String showName){
		this.showName = showName;
	}

	public float getSpawnX(){
		return spawnX;
	}

	public void setSpawnX(float spawnX){
		this.spawnX = spawnX;
	}

	public float getSpawnY(){
		return spawnY;
	}

	public void setSpawnY(float spawnY){
		this.spawnY = spawnY;
	}

	public String getBase64Img(){
		return base64Img;
	}

	public void setBase64Img(String img){
		this.base64Img = img;
	}

	private Bitmap bgCache = null;	
	public Bitmap getBGImage(){
		if(bgCache == null){
			bgCache = Util.Bitmap.fromBase64(base64Img);
		}
		return bgCache;
	}

	public ArrayList<RectF> getCollisions(){
		return collisions;
	}

	public void addCollision(RectF collision){
		collisions.add(collision);
	}

	public String getScript(){
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@ScriptRef
	public Entity spawn(String name, String type){
		return spawn(name, type, 0, 0);
	}

	@ScriptRef
	public Entity spawn(String name, String type, float x, float y){
		return spawn(name, type, x, y, "");
	}
	
	@ScriptRef
	public Entity spawn(String name, String type, float x, float y, String spawnScript){
		return spawn(name, type, x, y, spawnScript, spawnScript);
	}

	@ScriptRef
	public Entity spawn(String name, String type, float x, float y, String spawnScript, String collideScript){
		switch(type){
		case "entity":
			Entity e = new Entity();
			e.setName(name);
			e.setX(x);
			e.setY(y);
			e.setScript(spawnScript);
			entities.add(e);
			e.setParent(this);
			if(spawnScript != null){
				if(spawnScript != ""){
					VariableManager entVars = new VariableManager();
					entVars.addVar("self", e);
					ScriptParser.run(spawnScript, entVars);
				}
			}
			return e;
		case "trainer":
			EntityTrainer trainer = new EntityTrainer();
			trainer.setName(name);
			trainer.setX(x);
			trainer.setY(y);
			entities.add(trainer);
			trainer.setParent(this);
			if(spawnScript != null){
				if(spawnScript != ""){
					VariableManager tVars = new VariableManager();
					tVars.addVar("self", trainer);
					ScriptParser.run(spawnScript, tVars);
				}
			}
			return trainer;
		}
		return null;
	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(getBGImage(), Game.scrollX, Game.scrollY, null);
		for(Entity e : entities){
			Bitmap b = e.getBitmap();
			canvas.drawBitmap(b, new Rect(0, 0, b.getWidth(), b.getHeight()), Game.resizeRect(new RectF(e.getX() + Game.scrollX, e.getY() + 
					Game.scrollY, e.getWidth(), e.getHeight())), null);
		}
	}
}
