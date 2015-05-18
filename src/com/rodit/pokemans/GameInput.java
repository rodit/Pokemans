package com.rodit.pokemans;

import android.view.MotionEvent;

import com.rodit.pokemans.gui.Gui;

public class GameInput {

	public static void input(MotionEvent event){
		for(Gui g : Game.guis.values()){
			if(g.getActive())g.input(event);
		}
	}
}
