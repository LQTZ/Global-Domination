package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

/**
 * This file is part of Global Domination.
 *
 * Global Domination is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Global Domination is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Global Domination. If not, see <http://www.gnu.org/licenses/>.
 */
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
	public void addSoldier(int level, int xCoord, int yCoord)
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
	public void addSettler(int level, int xCoord, int yCoord)
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

	/**
	 * Reinstate {@code transient} fields
	 *
	 * @param utils
	 *            new {@code Utils}
	 */
	public void onDeserialization(Utils utils)
	{
		this.utils = utils;
	}
}
