package com.rodit.pokemans.gui;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.GameLog;
import com.rodit.pokemans.gui.component.GuiActionListener;
import com.rodit.pokemans.gui.component.GuiComponent;
import com.rodit.pokemans.resource.ResourceCache;

public class Gui {

	public String regid = "";

	private ArrayList<GuiComponent> components;
	private boolean active = false;
	private String background = "";

	public Gui(){
		components = new ArrayList<GuiComponent>();
	}

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
		final String id = comp.getID();
		if(Game.DEBUG){
			comp.addListener(new GuiActionListener(){
				@Override
				public void onTouch(int eventId){
					GameLog.write("Component " + id + " touched eventId=" + eventId + ".");
				}
				@Override
				public void onTouchDown(){
					GameLog.write("Component " + id + " touch down.");
				}
				@Override
				public void onTouchUp(){
					GameLog.write("Component " + id + " touch up.");
				}
			});
		}
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
		if(background != null){
			if(background != "" && !background.isEmpty()){
				Bitmap bgbit = getBGBitmap();
				if(bgbit != null)
					canvas.drawBitmap(bgbit, new Rect((int)Game.camera.getLocationX(), (int)Game.camera.getLocationY(), (int)Game.resizeX(bgbit.getWidth()), (int)Game.resizeY(bgbit.getHeight())), Game.getScreenRect(), null);
			}
		}
		for(GuiComponent comp : components){
			if(comp.getVisible())comp.paint(canvas);
		}
	}

	public void input(MotionEvent event) {
		for(GuiComponent g : components){
			if(event.getAction() == MotionEvent.ACTION_UP){
				g.input(event.getAction());
				continue;
			}
			if(!g.getVisible())continue;
			RectF r = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
			RectF r2 = new RectF(g.getX(), g.getY(), g.getX() + g.getWidth(), g.getY() + g.getHeight());
			if(RectF.intersects(r, r2))g.input(event.getAction());
		}
		//TODO Add screen size ratio change for input
	}
	
	public void update(){
		for(GuiComponent comp : components){
			comp.update();
		}
	}
}
