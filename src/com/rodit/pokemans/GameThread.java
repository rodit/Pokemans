package com.rodit.pokemans;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{

	private boolean running = false;
	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;

	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	public boolean getRunning(){
		return running;
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	@SuppressLint("WrongCall")
	@Override
	public void run(){
		Canvas canvas;
		while (running) {
			Game.update();
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gamePanel.onDraw(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
