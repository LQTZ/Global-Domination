package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Nation implements Serializable
{
	private static final long serialVersionUID = 1L;
	private transient Utils utils;

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

	/**
	 * Object representing a {@code Nation} (player) in the game
	 * 
	 * @param nationality
	 *            nationality of the nation
	 * @param utils
	 *            GD {@code Utils} utility
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
	 * @param level
	 *            {@code level} of the {@code Soldier}
	 * @param xCoord
	 *            x-coordinate of the {@code Soldier}'s placement in the map
	 * @param yCoord
	 *            y-coordinate of the {@code Soldier}'s placement in the map
	 */
	public synchronized void addSoldier(int level, int xCoord, int yCoord)
	{
		Soldier s = new Soldier(this, level, xCoord, yCoord, utils);
		units.add(s);
	}

	/**
	 * Add a settler to the Nation's units list
	 * 
	 * @param level
	 *            {@code level} of the {@code Settler}
	 * @param xCoord
	 *            x-coordinate of the {@code Settler}'s placement in the map
	 * @param yCoord
	 *            y-coordinate of the {@code Settler}'s placement in the map
	 */
	public synchronized void addSettler(int level, int xCoord, int yCoord)
	{
		Settler s = new Settler(this, level, xCoord, yCoord, utils);
		units.add(s);
	}

	/**
	 * Add a {@code City}
	 * 
	 * @param t
	 *            {@code Tile} to put the {@code City} on
	 */
	public void addCity(Tile t)
	{
		City c = new City(t, this, utils);
		cities.add(c);
		t.city = c;
		t.nat = nationality;
	}

	public void onDeserialization(Utils utils)
	{
		this.utils = utils;
	}
}
