package com.rodit.pokemans.entity;

import java.util.ArrayList;

import com.rodit.pokemans.item.Inventory;
import com.rodit.pokemans.pokeman.Pokeman;


public class EntityPlayer extends EntityTrainer{
	
	public EntityPlayer(){
		this.setName("player");
		this.setHeight(84);
		this.setWidth(84);
		this.setResource("player");
		this.money = 1000;
		this.setVisible(true);
		this.setState(MoveState.WALK);
		inventory = new Inventory();
		pokemans = new ArrayList<Pokeman>();
		pokemanController = new PokemanController();
		this.setMoveSpeed(2.5F);
	}

	public EntityPlayer(Entity e){
		this();
		this.setX(e.getX());
		this.setY(e.getY());
		this.setWidth(e.getWidth());
		this.setHeight(e.getHeight());
		this.setCollideScript(e.getCollideScript());
		this.setScript(e.getScript());
		for(String s : e.getAnimations().keySet()){
			addAnimation(s, e.getAnimations().get(s));
		}
		this.setDirection(Direction.DOWN);
		this.setState(MoveState.WALK);//this.setState(e.getState());
		this.setResource(e.getResource());
		this.setVisible(e.isVisible());
	}

	/*
	@ScriptRef
	public void moveUp(String arg){
		this.setMotionY(this.getMotionY() - this.getMoveSpeed());
	}

	@ScriptRef
	public void moveDown(String arg){
		this.setMotionY(this.getMotionY() + this.getMoveSpeed());
	}

	@ScriptRef
	public void moveLeft(String arg){
		this.setMotionX(this.getMotionX() - this.getMoveSpeed());
	}

	@ScriptRef
	public void moveRight(String arg){
		this.setMotionX(this.getMotionX() + this.getMoveSpeed());
	}
	*/
}
