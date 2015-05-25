package com.rodit.pokemans;

import android.view.MotionEvent;

public class GameInput {

	public static void input(MotionEvent event){
		if(Game.gui.getActive())Game.gui.input(event);
		//GameLog.write("Handled game input id=" + event.getAction() + " pos=" + event.getX() + ":" + event.getY());
	}
}
