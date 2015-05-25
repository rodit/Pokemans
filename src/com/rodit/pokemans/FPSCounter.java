package com.rodit.pokemans;

import java.util.Timer;
import java.util.TimerTask;


public class FPSCounter{

	class CounterTask extends TimerTask{
		@Override
		public void run(){
			fps = frames;
			frames = 0;
		}
	}

	private int fps = 0;
	private int frames = 0;
	private Timer timer = null;

	public void start(){
		fps = 0;
		if(timer != null)stop();
		timer = new Timer();
		timer.schedule(new CounterTask(), 0, 1000);
		GameLog.write("Started fps counter.");
	}

	public void update(){
		frames++;
	}

	public void stop(){
		if(timer != null){
			timer.cancel();
			timer.purge();
		}
		timer = null;
		fps = 0;
	}

	public void reset(){
		fps = 0;
		frames = 0;
	}

	public int fps(){
		return fps;
	} 
}