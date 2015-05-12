package com.rodit.pokemans.resource;

import java.util.ArrayList;

import com.rodit.pokemans.Game;

import android.graphics.Bitmap;

public class Animation {

	private float delay;
	private ArrayList<Bitmap> frames;
	private int cFrame;
	
	public Animation(){}
	
	public void update(){
		
	}
	
	public Bitmap getFrame(long time) {
		return frames.get(cFrame);
	}
}
