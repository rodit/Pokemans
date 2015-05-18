package com.rodit.pokemans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.BitmapFactory;
import android.util.Base64;

public class Util {

	public static class Bitmap{
		
		public static android.graphics.Bitmap fromBase64(String b64){
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
	}
}
