package com.rodit.pokemans;

import com.example.pokemans.R;

import android.app.Activity;
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
