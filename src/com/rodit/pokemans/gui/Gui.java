package com.rodit.pokemans.gui;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.gui.component.GuiComponent;
import com.rodit.pokemans.resource.ResourceCache;

public abstract class Gui {

	private ArrayList<GuiComponent> components;
	private boolean active = false;
	private String background = "";
	
	public Gui(){
		components = new ArrayList<GuiComponent>();
		init();
	}
	
	public abstract void init();
	
	public ArrayList<GuiComponent> getComponents(){
		return components;
	}
	
	public GuiComponent getComponent(String id){
		for(GuiComponent comp : components){
			if(comp.getID().equals(id))return comp;
		}
		return null;
	}
	
	public void addComponent(GuiComponent comp){
		components.add(comp);
		comp.setParent(this);
	}
	
	public void removeComponent(String id){
		removeComponent(getComponent(id));
	}
	
	public void removeComponent(GuiComponent comp){
		components.remove(comp);
	}
	
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public String getBackground(){
		return background;
	}
	
	public void setBackground(String background){
		this.background = background;
	}
	
	public Bitmap getBGBitmap(){
		return ResourceCache.getImage(background);
	}
	
	public void draw(Canvas canvas){
		if(background != ""){
			Bitmap bgbit = getBGBitmap();
			canvas.drawBitmap(bgbit, new Rect(0, 0, bgbit.getWidth(), bgbit.getHeight()), Game.getScreenRect(), null);
		}
		for(GuiComponent comp : components){
			if(comp.getVisible())comp.paint(canvas);
		}
	}

	public void input(MotionEvent event) {
		for(GuiComponent g : components){
			if(!g.getVisible())continue;
			RectF r = new RectF(event.getX(), event.getY(), 1, 1);
			RectF r2 = new RectF(g.getX(), g.getY(), g.getWidth(), g.getHeight());
			if(r.intersect(r2))g.input(event.getAction());
		}
	}
}
