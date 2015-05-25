package com.rodit.pokemans;

import java.util.Timer;
import java.util.TimerTask;

public class MemRecord {

	public static long cUse = 0;
	public static long cFree = 0;

	private static Timer timer;

	public static void init(){
		timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				cFree = Runtime.getRuntime().freeMemory();
				cUse = Runtime.getRuntime().totalMemory() - cFree;
			}
		}, 0, 200);
	}
}
