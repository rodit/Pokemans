package com.rodit.pokemans.resource;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.rodit.pokemans.Game;

public class Animation {

	private float delay = 1;
	private ArrayList<Bitmap> frames;
	private int cFrame = 0;
	
	private int lastTime = 0;
	
	public Animation(){
		frames = new ArrayList<Bitmap>();
	}
	
	public void update(){
		if(Game.getTime() - lastTime <= delay){
			cFrame++;
		}
	}
	
	public Bitmap getFrame() {
		return frames.get(cFrame);
	}
}
