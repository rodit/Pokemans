package com.rodit.pokemans;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_game);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			Game.setContext(this.getApplicationContext());
			Game.assets = getAssets();
			Game.init();
			setContentView(new GamePanel(this));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy(){

		super.onDestroy();
	}

	@Override
	protected void onStop(){

		super.onStop();
	}
}
