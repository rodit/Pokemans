package com.rodit.pokemans.pokeman;

import java.util.Random;

import com.rodit.pokemans.DataGrid;
import com.rodit.pokemans.Game;
import com.rodit.pokemans.pokeman.ability.Ability;

public class DamageCalc {

	private static Random random = new Random();

	public static DataGrid<BattleType, BattleType, Float> grid = new DataGrid<BattleType, BattleType, Float>();

	public static void init() {
		for (BattleType t : BattleType.values())
			grid.addColumn(t);
		String txt = new String(Game.readAsset("multies.txt"));
		for (String line : txt.split(";")) {
			String t = line.trim();
			if (t.equals("") || t.isEmpty()) continue;
			String[] parts = line.split(" ");
			for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
			multi(parts[0], parts[1], Float.valueOf(parts[2]));
		}
	}

	public static void multi(String type1, String type2, float multi) {
		BattleType t1 = BattleType.valueOf(type1.toUpperCase());
		BattleType t2 = BattleType.valueOf(type2.toUpperCase());
		grid.add(t1, t2, multi);
	}

	public static float calcDamage(Ability ability, Pokeman attacker, Pokeman defender) {
		float dmg = ability.damage;
		float f = random.nextFloat() + random.nextFloat();
		while (f < 0.85f)
			f += random.nextFloat();
		if (f > 1.25f) f = 1.25f;
		dmg *= grid.getValue(attacker.type, defender.type);
		return dmg * f;
	}
}
