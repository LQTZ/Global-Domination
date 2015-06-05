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

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public abstract class Unit implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected transient Utils utils;

	/**
	 * {@code Nation} of the {@code Unit}
	 */
	public Nation nation;

	/**
	 * {@code Tile} the {@code Unit} is currently on
	 */
	public Tile tile;

	/**
	 * {@code UnitType} of the {@code Unit}
	 */
	public UnitType unitType;

	/**
	 * Maximum number of health points the {@code Unit} can have (also the
	 * starting hp {@code level})
	 */
	public double maxHealthPoints;

	/**
	 * Number of health points the {@code Unit} currently has
	 */
	public double currentHealthPoints;

	/**
	 * Number of {@code Tile}s the {@code Unit} can move per turn
	 */
	public int maxMoveDistance;

	/**
	 * Number of {@code Tile}s the {@code Unit} has left to move on the current
	 * turn
	 */
	public int movesLeft;

	/**
	 * Power the {@code Unit} uses when defending (a variable in the defense
	 * odds formula)
	 */
	public double defendPower;

	/**
	 * {@code Unit} {@code level} (hp and power depend on this)
	 */
	public int level;

	/**
	 * Initialize the unit
	 * 
	 * @param nation
	 *            nation of the Unit
	 * @param level
	 *            {@code Unit} {@code level} to base other fields on
	 * @param xCoord
	 *            initial x-coordinate
	 * @param yCoord
	 *            initial y-coordinate
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public Unit(Nation nation, int level, int xCoord, int yCoord, Utils utils)
	{
		// Initialize passed fields
		this.nation = nation;
		this.level = level;
		this.utils = utils;

		// Assign level based fields
		assignByLevel();

		// Initialize "current" fields
		tile = utils.game.tiles[xCoord][yCoord];
		currentHealthPoints = maxHealthPoints;
		movesLeft = maxMoveDistance;
	}

	protected abstract void assignByLevel();

	protected abstract int getMoveError(Tile toTile);

	/**
	 * Move the {@code Unit} to a specific {@code Tile} if legal
	 * 
	 * @param toTile
	 *            {@code Tile} the {@code Unit} is moving to
	 * 
	 * @return exit status (see implementations)
	 */
	public int move(Tile toTile)
	{
		int moveError = getMoveError(toTile);

		if (moveError != 0)
		{
			return moveError;
		}

		// Delete the old one
		tile.removeUnit(this);

		// Check if own Nation has abandoned Tile
		if (tile.soldiers.size() + tile.settlers.size() == 0
				&& tile.city == null)
		{
			tile.nat = Nationality.NEUTRAL;
		}

		utils.gw.eventLog("A " + this + " was moved from " + tile + " to "
				+ toTile + ".");

		// Add to new one
		tile = toTile;
		tile.addUnit(this);

		// Decrement movesLeft
		movesLeft--;

		return moveError;
	}

	/**
	 * Randomly generate amount of attack with in a fight. Considers health,
	 * power, and defense capability.
	 * 
	 * @param power
	 *            power {@code level} being used against enemy {@code Unit}
	 * @param againstUnit
	 *            enemy {@code Unit}
	 * @return hits to hit enemy {@code Unit} with
	 */
	public double generateHits(double power, Unit againstUnit)
	{
		double thisEffectivePower = power * currentHealthPoints
				/ maxHealthPoints;
		double attackerEffectivePower = againstUnit.defendPower
				* againstUnit.currentHealthPoints / againstUnit.maxHealthPoints;

		double hits = thisEffectivePower * thisEffectivePower
				/ attackerEffectivePower * (utils.random.nextDouble() + 1) / 2;

		if (hits < 0)
			return 0;
		else
			return hits;
	}

	/**
	 * Remove all references to the {@code Unit} (kill it) (To be
	 * {@code Override}d to add {@code UnitType} specific code)
	 */
	public void delete()
	{
		// Log unit death
		utils.game.gw.eventLog("A " + this + " died on " + tile + ".");

		// Remove references to the object
		nation.units.remove(this);
		if (tile.soldiers.size() + tile.settlers.size() == 0
				&& tile.city == null)
		{
			tile.nat = Nationality.NEUTRAL;
		}

		tile.removeUnit(this);
	}

	@Override
	public String toString()
	{
		return this.nation.nationality + " Level " + level + " " + unitType
				+ " Unit";
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
