/*******************************************************************************
 * Global Domination is a strategy game.
 * Copyright (C) 2014, 2015  LQTZ Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
	 * Add a {@code Unit} to the {@code Nation}
	 *
	 * @param ut
	 *            {@code UnitType} of the {@code Unit} to add
	 * @param level
	 *            {@code level} of the {@code Unit}
	 * @param xCoord
	 *            x-coordinate of the {@code Unit}'s placement in the map
	 * @param yCoord
	 *            y-coordinate of the {@code Unit}'s placement in the map
	 */
	public void addUnit(UnitType ut, int level, int xCoord, int yCoord)
	{
		if (ut == UnitType.SETTLER)
		{
			Settler s = new Settler(this, level, xCoord, yCoord, utils);
			units.add(s);
		}
		else
		{
			Soldier s = new Soldier(this, level, xCoord, yCoord, utils);
			units.add(s);
		}
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
