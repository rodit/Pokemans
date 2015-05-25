package com.rodit.pokemans.resource;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;

import com.rodit.pokemans.Game;

public class Animation {

	private float delay = 1;
	private ArrayList<Bitmap> frames;
	private int cFrame = 0;
	private boolean retainFrame = false;
	
	private long lastTime = 0;
	
	public Animation(){
		frames = new ArrayList<Bitmap>();
	}
	
	public void update(){
		long diff = Game.getTime() - lastTime;
		if(diff >= delay){
			cFrame++;
			if(cFrame >= frames.size())cFrame = 0;
			lastTime = Game.getTime();
		}
	}
	
	public Bitmap getFrame() {
		return frames.get(cFrame);
	}
	
	public void setBitmaps(ArrayList<Bitmap> bmps){
		this.frames = bmps;
	}
	
	public boolean doesRetainFrame(){
		return retainFrame;
	}
	
	public void setRetainFrame(boolean retainFrame){
		this.retainFrame = retainFrame;
	}
	
	public void reset(){
		cFrame = 0;
		lastTime = 0;
	}
	
	public Animation clone(){
		Animation a = new Animation();
		a.delay = delay;
		a.frames = new ArrayList<Bitmap>();
		a.frames.addAll(frames);
		a.cFrame = cFrame;
		a.retainFrame = retainFrame;
		return a;
	}
	
	private HashMap<String, Animation> animStates = new HashMap<String, Animation>();
	
	public Animation get(String reqID){
		if(animStates.get(reqID) == null){
			animStates.put(reqID, clone());
		}
		return animStates.get(reqID);
	}

	public void setDelay(float delay) {
		this.delay = delay;
	}
}
