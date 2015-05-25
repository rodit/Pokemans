package com.rodit.pokemans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Base64;

public class Util {
	
	public static class Bitmap{
		
		public static android.graphics.Bitmap fromBase64(String b64){
			GameLog.write("Converting base64 to bitmap...");
			return fromBytes(Base64.decode(b64.getBytes(), Base64.DEFAULT));
		}
		
		public static String toBase64(android.graphics.Bitmap b){
			return Base64.encodeToString(toBytes(b), Base64.DEFAULT);
		}
		
		public static android.graphics.Bitmap fromBytes(byte[] data){
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		}
		
		public static byte[] toBytes(android.graphics.Bitmap b){
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			b.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
			byte[] data = stream.toByteArray();
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return data;
		}
		
		public static android.graphics.Bitmap crop(android.graphics.Bitmap src, Rect srcRect){
			int w = srcRect.width(), h = srcRect.height();
			android.graphics.Bitmap cropped = android.graphics.Bitmap.createBitmap(w, h, Config.ARGB_8888);
			Paint p = new Paint();
		    p.setXfermode(new PorterDuffXfermode(Mode.CLEAR));      
		    Canvas c = new Canvas(cropped); 
		    c.drawBitmap(src, srcRect, new Rect(0, 0, srcRect.width(), srcRect.height()), null);
		    return cropped;
		}
	}
	
	public static class Maths{
		
		public static float pos(float f){
			if(f < 0)return f * -1;
			return f;
		}
	}
}
