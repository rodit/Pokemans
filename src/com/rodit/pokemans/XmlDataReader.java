package com.rodit.pokemans;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

import com.rodit.pokemans.entity.Entity;
import com.rodit.pokemans.entity.EntityTrainer;
import com.rodit.pokemans.gui.Gui;
import com.rodit.pokemans.gui.component.GuiComponent;
import com.rodit.pokemans.item.Inventory;
import com.rodit.pokemans.item.Item;
import com.rodit.pokemans.item.Items;
import com.rodit.pokemans.map.Map;
import com.rodit.pokemans.pokeman.BattleType;
import com.rodit.pokemans.pokeman.Pokeman;
import com.rodit.pokemans.pokeman.ability.Abilities;
import com.rodit.pokemans.pokeman.ability.Ability;
import com.rodit.pokemans.pokeman.effect.Effect;
import com.rodit.pokemans.pokeman.effect.Effects;

public class XmlDataReader {

	public static Entity readEntity(String xml){
		Entity e = new Entity();
		Document doc = createDocument(xml);
		Element entDat = (Element)doc.getElementsByTagName("entity").item(0);
		e.setName(entDat.getAttribute("name"));
		e.setWidth(Float.valueOf(entDat.getAttribute("width")));
		e.setHeight(Float.valueOf(entDat.getAttribute("height")));
		e.setResource(entDat.getAttribute("resource"));
		NodeList animNodes = doc.getElementsByTagName("animation");
		for(int i = 0; i < animNodes.getLength(); i++){
			Node n = animNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element anim = (Element)n;
			e.addAnimation(anim.getAttribute("state"), anim.getAttribute("anim"));
		}
		return e;
	}

	public static EntityTrainer readEntityTrainer(String xml){
		EntityTrainer e = (EntityTrainer)readEntity(xml);
		Document doc = createDocument(xml);
		e.money = Integer.valueOf(((Element)doc.getElementsByTagName("inventory").item(0)).getAttribute("money"));
		NodeList pNodes = doc.getElementsByTagName("pokeman");
		for(int i = 0; i < pNodes.getLength(); i++){
			Node n = pNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element pokeman = (Element)n;
			Pokeman p = Game.pokemans.get(Integer.valueOf(pokeman.getAttribute("id")));
			e.pokemans.add(p);
			e.pokemanController.setLevel(i, Integer.valueOf(pokeman.getAttribute("level")));
			String abilities = pokeman.getAttribute("abilities");
			for(String a : abilities.split(Pattern.quote("|"))){
				Ability ab = Abilities.get(a);
				e.pokemanController.addAbility(i, ab);
			}
		}
		NodeList iNodes = doc.getElementsByTagName("item");
		for(int i = 0; i < iNodes.getLength(); i++){
			Node n = iNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element item = (Element)n;
			e.inventory = new Inventory();
			e.inventory.add(Items.get(item.getAttribute("id")), Integer.valueOf(item.getAttribute("quantity")));
		}
		return e;
	}
	
	@SuppressLint("DefaultLocale")
	public static Pokeman readPokeman(String xml){
		Pokeman p = new Pokeman();
		Document doc = createDocument(xml);
		Element pEl = (Element)doc.getElementsByTagName("pokeman").item(0);
		p.id = Integer.valueOf(pEl.getAttribute("id"));
		p.name = pEl.getAttribute("name");
		p.type = BattleType.valueOf(pEl.getAttribute("type").toUpperCase());
		p.icon = pEl.getAttribute("icon");
		p.evolvesat = Integer.valueOf(pEl.getAttribute("evolvesat"));
		p.evolvesto = Integer.valueOf(pEl.getAttribute("evolvesto"));
		String abilities = pEl.getAttribute("abilities");
		for(String ability : abilities.split(Pattern.quote("|"))){
			if(ability.isEmpty())continue;
			p.defaultAbilities.add(Abilities.get(ability.replace("|", "")));
		}
		NodeList animNodes = doc.getElementsByTagName("animation");
		for(int i = 0; i < animNodes.getLength(); i++){
			Node n = animNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element animElement = (Element)n;
			p.animations.put(animElement.getAttribute("state"), animElement.getAttribute("anim"));
		}
		Element statsEl = (Element)doc.getElementsByTagName("stats").item(0);
		p.stats.attack = Integer.valueOf(statsEl.getAttribute("attack"));
		p.stats.specialAttack = Integer.valueOf(statsEl.getAttribute("specialAttack"));
		p.stats.defence = Integer.valueOf(statsEl.getAttribute("defence"));
		p.stats.specialDefence = Integer.valueOf(statsEl.getAttribute("specialDefence"));
		p.stats.maxHp = Integer.valueOf(statsEl.getAttribute("maxHp"));
		return p;
	}

	public static HashMap<String, Ability> readAbilities(String xml){
		HashMap<String, Ability> a = new HashMap<String, Ability>();
		Document doc = createDocument(xml);
		NodeList ablNodes = doc.getElementsByTagName("ability");
		for(int i = 0; i < ablNodes.getLength(); i++){
			Node n = ablNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element el = (Element)n;
			Ability abl = new Ability();
			String id = el.getAttribute("id");
			abl.name = el.getAttribute("name");
			abl.damage = Float.valueOf(el.getAttribute("damage"));
			for(String s : el.getAttribute("effectsSelf").split(Pattern.quote("|"))){
				if(s.isEmpty())continue;
				abl.effectsSelf.add(Effects.get(s.replace("|", "")));
			}
			for(String s : el.getAttribute("effectsTarget").split(Pattern.quote("|"))){
				if(s.isEmpty())continue;
				abl.effectsTarget.add(Effects.get(s.replace("|", "")));
			}
			abl.maxPP = Integer.valueOf(el.getAttribute("maxPP"));
			a.put(id, abl);
		}
		return a;
	}

	public static HashMap<String, Effect> readEffects(String xml){
		HashMap<String, Effect> e = new HashMap<String, Effect>();
		Document doc = createDocument(xml);
		NodeList effNodes = doc.getElementsByTagName("effect");
		for(int i = 0; i < effNodes.getLength(); i++){
			Node n = effNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element el = (Element)n;
			Effect eff = new Effect();
			String id = el.getAttribute("id");
			eff.name = el.getAttribute("name");
			eff.delay = Float.valueOf(el.getAttribute("delay"));
			eff.script = el.getAttribute("script");
			e.put(id, eff);
		}
		return e;
	}
	
	public static HashMap<String, Item> readItems(String xml){
		HashMap<String, Item> items = new HashMap<String, Item>();
		Document doc = createDocument(xml);
		NodeList itNodes = doc.getElementsByTagName("item");
		for(int i = 0; i < itNodes.getLength(); i++){
			Node n = itNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element el = (Element)n;
			Item it = new Item();
			String regName = el.getAttribute("regName");
			it.name = el.getAttribute("name");
			it.resource = el.getAttribute("resource");
			it.description = el.getAttribute("description");
			it.canUse = Boolean.valueOf(el.getAttribute("canUse"));
			it.useScript = el.getAttribute("script");
			items.put(regName, it);
		}
		return items;
	}

	public static Map readMap(String file){
		String xml = new String(Game.readAsset(file));
		Map m = new Map();
		Document doc = createDocument(xml);
		Element mel = (Element)doc.getElementsByTagName("map").item(0);
		m.setShowName(mel.getAttribute("name"));
		m.setScript(mel.getAttribute("script"));
		m.setSpawnX(Float.valueOf(mel.getAttribute("spawnX")));
		m.setSpawnY(Float.valueOf(mel.getAttribute("spawnY")));
		m.setBase64Img(((Element)doc.getElementsByTagName("bg").item(0)).getAttribute("base64"));
		NodeList cNodes = doc.getElementsByTagName("collision");
		for(int i = 0; i < cNodes.getLength(); i++){
			Node n = cNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element e = (Element)n;
			float x = Float.valueOf(e.getAttribute("x")), y = Float.valueOf(e.getAttribute("y"));
			m.addCollision(new RectF(x, y, x +  Float.valueOf(e.getAttribute("width")), y + Float.valueOf(e.getAttribute("height"))));
		}
		NodeList eNodes = doc.getElementsByTagName("entity");
		for(int i = 0; i < eNodes.getLength(); i++){
			Node n = eNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element e = (Element)n;
			m.spawn(e.getAttribute("name"), e.getAttribute("type"), Float.valueOf(e.getAttribute("x")), Float.valueOf(e.getAttribute("y")), e.getAttribute("script"), e.getAttribute("collide"));
		}
		return m;
	}
	
	@SuppressLint("DefaultLocale")
	public static Gui readGui(String xml){
		Gui g = new Gui();
		Document doc = createDocument(xml);
		Element el = (Element)doc.getElementsByTagName("gui").item(0);
		g.regid = el.getAttribute("name");
		g.setBackground(el.getAttribute("background"));
		NodeList cNodes = el.getElementsByTagName("component");
		for(int i = 0; i < cNodes.getLength(); i++){
			Node n = cNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element e = (Element)n;
			GuiComponent comp = new GuiComponent();
			comp.setID(e.getAttribute("id"));
			comp.setText(e.getAttribute("text"));
			comp.setFont(e.getAttribute("font"));
			comp.setAlign(Align.valueOf(e.getAttribute("align").toUpperCase()));
			comp.setFontSize(Float.valueOf(e.getAttribute("fontSize")));
			comp.setX(Float.valueOf(e.getAttribute("x").replace("%centerX%", "" + Game.getScreenRectF().centerX())));
			comp.setY(Float.valueOf(e.getAttribute("y").replace("%centerY%", "" + Game.getScreenRectF().centerY())));
			comp.setWidth(Float.valueOf(e.getAttribute("width")));
			comp.setHeight(Float.valueOf(e.getAttribute("height")));
			comp.setColor(Integer.valueOf(e.getAttribute("color")));
			comp.setBackground(e.getAttribute("background"));
			comp.setScript(e.getAttribute("onClick"));
			g.addComponent(comp);
		}
		return g;
	}

	private static DocumentBuilderFactory factory;
	private static DocumentBuilder builder;

	public static Document createDocument(String xml){
		try{
			if(factory == null){
				factory = DocumentBuilderFactory.newInstance();
			}
			builder = factory.newDocumentBuilder();
			return builder.parse(new ByteArrayInputStream(xml.getBytes()));
		}catch(Exception e){
			GameLog.write("Error creatig document for xml. " + e.getMessage());
		}
		return null;
	}
}
