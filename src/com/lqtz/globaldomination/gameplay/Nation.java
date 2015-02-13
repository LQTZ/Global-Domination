package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

/**
 * 
 * Object representing a nation (player) in the game
 * 
 * @author Gandalf
 * 
 */
public class Nation
{
	/**
	 * Nationality of the nation
	 */
	public Nationality nationality;

	/**
	 * Units that belong to the nation
	 */
	public ArrayList<Unit> units;

	/**
	 * Cities that belong to the nation
	 */
	public ArrayList<City> cities;

	/**
	 * Nations this nation is allied with
	 */
	public Nation[] allies;

	/**
	 * Nations this nation is neutral with
	 */
	public Nation[] neutral;

	/**
	 * Nations this nation is enemies with
	 */
	public Nation[] enemies;

	private Utils utils;

	/**
	 * Creates new nation
	 * 
	 * @param nationality
	 *            nationality of the nation
	 */
	public Nation(Nationality nationality, Utils utils)
	{
		this.nationality = nationality;
		this.utils = utils;

		this.units = new ArrayList<Unit>();
		this.cities = new ArrayList<City>();
	}

	/**
	 * Add a soldier to the Nation's units list
	 * 
	 * @param healthPoints
	 * @param moveDistance
	 * @param attackPower
	 * @param defendPower
	 * @param xCoord
	 * @param yCoord
	 */
	public void addSoldier(int level, int xCoord, int yCoord)
	{
		Soldier s = new Soldier(this, level, xCoord, yCoord, utils);
		units.add(s);
		utils.game.tiles[xCoord][yCoord].soldiers.add(s);
	}

	/**
	 * Add a settler to the Nation's units list
	 * 
	 * @param healthPoints
	 * @param moveDistance
	 * @param defendPower
	 * @param xCoord
	 * @param yCoord
	 */
	public void addSettler(int level, int xCoord, int yCoord)
	{
		Settler s = new Settler(this, level, xCoord, yCoord, utils);
		units.add(s);
		utils.game.tiles[xCoord][yCoord].settlers.add(s);
	}

	public void addCity(Tile t)
	{
		City c = new City(t, this, utils);
		cities.add(c);
		t.city = c;
		t.nat = nationality;
	}
}
