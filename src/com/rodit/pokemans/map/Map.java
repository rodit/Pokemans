package com.rodit.pokemans.map;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.Util;
import com.rodit.pokemans.entity.Entity;

public class Map {

	private String showName;
	private ArrayList<Entity> entities;
	private float spawnX = 0;
	private float spawnY = 0;
	private String base64Img = "";
	
	public Map(){
		
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
	
	public void render(Canvas canvas) {
		canvas.drawBitmap(getBGImage(), Game.scrollX, Game.scrollY, null);
		for(Entity e : entities){
			Bitmap b = e.getBitmap();
			canvas.drawBitmap(b, new Rect(0, 0, b.getWidth(), b.getHeight()), Game.resizeRect(new RectF(e.getX() + Game.scrollX, e.getY() + 
					Game.scrollY, e.getWidth(), e.getHeight())), null);
		}
	}
}
