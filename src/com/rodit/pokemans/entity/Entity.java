package com.rodit.pokemans.entity;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.rodit.pokemans.map.Map;
import com.rodit.pokemans.resource.Animation;
import com.rodit.pokemans.resource.IBoundable;
import com.rodit.pokemans.resource.IDrawable;
import com.rodit.pokemans.resource.ResourceCache;
import com.rodit.pokemans.script.ScriptParser;
import com.rodit.pokemans.script.ScriptRef;
import com.rodit.pokemans.script.VariableManager;

public class Entity implements IBoundable, IDrawable{

	private float x = 0;
	private float y = 0;
	private float width = 64;
	private float height = 64;
	private String name;
	private HashMap<String, String> animations;
	private String resource;
	private String state;
	private Map parent;
	private boolean visible;
	private String spawnScript;
	private String collideScript;

	public Entity(){
		name = "newEnt";
		animations = new HashMap<String, String>();
		resource = "undefined";
		state = "idle_down";
		visible = true;
		spawnScript = "";
		collideScript = "";
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Animation getAnimation(){
		return ResourceCache.getAnim(animations.get(state));
	}

	public void addAnimation(String state, String anim){
		animations.put(state, anim);
	}

	public String getResource(){
		return resource;
	}

	public void setResource(String resource){
		this.resource = resource;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}

	public Map getParent(){
		return parent;
	}

	public void setParent(Map parent){
		this.parent = parent;
	}

	@ScriptRef
	public float getX(){
		return x;
	}

	@ScriptRef
	public void setX(float x){
		this.x = x;
	}

	@ScriptRef
	public float getY(){
		return y;
	}

	@ScriptRef
	public void setY(float y){
		this.y = y;
	}

	@ScriptRef
	public float getWidth(){
		return width;
	}

	@ScriptRef
	public void setWidth(float width){
		this.width = width;
	}

	@ScriptRef
	public float getHeight(){
		return height;
	}

	@ScriptRef
	public void setHeight(float height){
		this.height = height;
	}

	@ScriptRef
	public boolean isVisible(){
		return visible;
	}

	@ScriptRef
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public RectF getBounds(){
		return new RectF(x, y, width, height);
	}

	public RectF getCollisionBounds(){
		return new RectF(x - 1, y - y, width + 1, height + 1);
	}

	public boolean collides(IBoundable b){
		return getCollisionBounds().intersect(b.getCollisionBounds());
	}

	public void onCollide(IBoundable b){
		if(collideScript != null){
			if(collideScript != "" && !collideScript.isEmpty()){
				VariableManager args = new VariableManager();
				args.addVar("collide", b);
				ScriptParser.run(collideScript, args);
			}
		}
	}

	public Bitmap getBitmap() {
		if(animations.size() == 0)return ResourceCache.getImage(resource);
		return ResourceCache.getAnim(animations.get(state)).getFrame();
	}

	public String getScript(){
		return spawnScript;
	}

	public void setScript(String spawnScript) {
		this.spawnScript = spawnScript;
	}

	public String getCollideScript(){
		return collideScript;
	}

	public void setCollideScript(String collideScript) {
		this.collideScript = collideScript;
	}

	public void update(){
		ResourceCache.getAnim(animations.get(state)).update();
	}

	@ScriptRef
	public boolean isPlayer(){
		return this instanceof EntityPlayer;
	}

	@ScriptRef
	public boolean isTrainer(){
		return this instanceof EntityTrainer;
	}
}
