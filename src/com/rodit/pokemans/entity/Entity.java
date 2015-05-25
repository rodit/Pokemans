package com.rodit.pokemans.entity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.GameLog;
import com.rodit.pokemans.map.Map;
import com.rodit.pokemans.resource.Animation;
import com.rodit.pokemans.resource.Animations;
import com.rodit.pokemans.resource.IBoundable;
import com.rodit.pokemans.resource.IDrawable;
import com.rodit.pokemans.resource.ResourceCache;
import com.rodit.pokemans.script.ScriptParser;
import com.rodit.pokemans.script.ScriptRef;
import com.rodit.pokemans.script.VariableManager;

public class Entity implements IBoundable, IDrawable{

	public static final int SPEED_WALK = 1;
	public static final int SPEED_RUN = 2;
	public static final int SPEED_BIKE = 3;

	private float x = 0;
	private float y = 0;
	private float width = 64;
	private float height = 64;
	private String name;
	private HashMap<String, String> animations;
	private String resource;
	private Map parent;
	private boolean visible;
	private String spawnScript;
	private String collideScript;
	private float moveSpeed = 1;
	private boolean noclip = false;
	private MoveState state = MoveState.WALK;
	private Direction direction = Direction.DOWN;

	public Entity(){
		name = "newEnt";
		animations = new HashMap<String, String>();
		resource = "undefined";
		visible = true;
		spawnScript = "";
		collideScript = "";
	}

	public MoveState getMoveState(){
		return state;
	}

	public Direction getDirection(){
		return direction;
	}

	public void setDirection(Direction direction){
		this.direction = direction;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Animation getAnimation(){
		return Animations.get(animations.get(getState()), "entity_" + this.name);
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

	@SuppressLint("DefaultLocale")
	@ScriptRef
	public String getState(){
		if(inWall)state = MoveState.IDLE;
		return state.toString().toLowerCase() + "_" + direction.toString().toLowerCase();
	}

	@ScriptRef
	public void setState(MoveState state){
		Animation canim = getAnimation();
		if(canim != null)
			if(!canim.doesRetainFrame())canim.reset();
		this.state = state;
	}

	@ScriptRef
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
		return new RectF(x, y, x + width, y + height);
	}

	public RectF getCollisionBounds(){
		if(isTrainer() || isPlayer()){
			return new RectF(x - 1, y + (height / 2) - 1, x + width + 1, y + height + 1);
		}
		return new RectF(x - 1, y - 1, x + width + 1, y + height + 1);
	}

	public boolean collides(IBoundable b){
		return RectF.intersects(getCollisionBounds(), b.getCollisionBounds());
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
		Animation a = getAnimation();
		if(a != null)
			return getAnimation().getFrame();
		GameLog.write("Failed to get animation " + name + "_" + state + " for entity " + name + ".");
		return null;
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

	public boolean getNoClip(){
		return noclip;
	}

	public void setNoClip(boolean noclip){
		this.noclip = noclip;
	}

	@ScriptRef
	public float scr_getMoveSpeed(String arg){
		return getMoveSpeed();
	}

	public float getMoveSpeed(){
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed){
		this.moveSpeed = moveSpeed;
	}

	@ScriptRef
	public void moveUp(String arg){
		move(0, -moveSpeed);
		direction = Direction.UP;
	}

	@ScriptRef
	public void moveDown(String arg){
		move(0, moveSpeed);
		direction = Direction.DOWN;
	}

	@ScriptRef
	public void moveLeft(String arg){
		move(-moveSpeed, 0);
		direction = Direction.LEFT;
	}

	@ScriptRef
	public void moveRight(String arg){
		move(moveSpeed, 0);
		direction = Direction.RIGHT;
	}

	@ScriptRef
	public void teleport(float x, float y){
		if(isPlayer()){
			Game.camera.translate(this.x - x, y - this.y, 0);
		}
		this.x = x;
		this.y = y;
	}

	@ScriptRef
	public void move(float x, float y){
		float newX = this.x + x;
		float newY = this.y + y;
		if(parent == null || this.noclip)
			teleport(newX, newY);
		else{
			Object cob = parent.getCollisionObject(this);
			if(cob instanceof Rect || cob instanceof RectF){
				switch(direction){
				case UP:
					teleport(this.x, this.y + 1);
					break;
				case DOWN:
					teleport(this.x, this.y - 1);
					break;
				case LEFT:
					teleport(this.x + 1, this.y);
					break;
				case RIGHT:
					teleport(this.x - 1, this.y);
					break;
				}
				this.lastX = x;
				this.lastY = y;
				state = MoveState.IDLE;
				inWall = true;
				return;
			}else if(cob instanceof Entity){
				if(((Entity)cob).noclip)
					teleport(newX, newY);
				else
					((Entity)cob).onCollide(this);
			}else teleport(newX, newY);
		}
	}

	private float lastX = 0;
	private float lastY = 0;
	private boolean bike = false;
	private boolean running = false;
	private boolean inWall = false;

	public void update(){
		this.setWidth(getBitmap().getWidth());
		this.setHeight(getBitmap().getHeight());

		if(bike)moveSpeed = SPEED_BIKE;
		else if(running)moveSpeed = SPEED_RUN;
		else moveSpeed = SPEED_WALK;

		if(x != lastX || y != lastY){
			if(bike)state = MoveState.BIKE;
			else if(running)state = MoveState.RUN;
			else state = MoveState.WALK;
		}else state = MoveState.IDLE;

		if(inWall)state = MoveState.IDLE;

		lastX = this.x;
		lastY = this.y;
		getAnimation().update();
	}

	@ScriptRef
	public boolean isPlayer(){
		return this instanceof EntityPlayer;
	}

	@ScriptRef
	public boolean isTrainer(){
		return this instanceof EntityTrainer;
	}

	public HashMap<String, String> getAnimations() {
		return animations;
	}
}
