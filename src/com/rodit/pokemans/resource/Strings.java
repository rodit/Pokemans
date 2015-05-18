package com.rodit.pokemans.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.rodit.pokemans.Game;

public class Strings {

	private static HashMap<String, HashMap<String, String>> strings = new HashMap<String, HashMap<String, String>>();
	private static String locale = "eng";

	public static void setLocale(String l){
		locale = l;
	}

	public static String get(String name){
		return strings.get(locale).get(name);
	}
	
	public static void put(String name, String value){
		strings.get(locale).put(name, value);
	}
	
	public static void init(){
		String s = new String(Game.readAsset("strings.xml"));
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		try {
			XmlPullParser xml = XmlPullParserFactory.newInstance().newPullParser();
			xml.setInput(stream, null);
			int event = xml.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) 
			{
				String name = xml.getName();
				switch (event){
				case XmlPullParser.START_TAG:
					break;
				case XmlPullParser.END_TAG:
					if(name.equals("locale")){
						locale = xml.getAttributeValue(null, "name");
					}else if(name.equals("string")){
						put(xml.getAttributeValue(null, "name"), xml.getAttributeValue(null, "value"));
					}
					break;
				}
				event = xml.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
