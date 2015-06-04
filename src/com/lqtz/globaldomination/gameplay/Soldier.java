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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Soldier extends Unit
{
	private static final long serialVersionUID = 1L;

	/**
	 * The power the unit uses when attacking (a variable in the attack odds
	 * formula)
	 */
	public double attackPower;

	/**
	 * {@code Soldier} {@code Unit}
	 *
	 * @param nation
	 *            {@code Nation} the {@code Soldier} belongs to
	 * @param level
	 *            {@code level} of the {@code Soldier} to grow
	 * @param xCoord
	 *            initial x-coordinate to put the {@code Soldier} on
	 * @param yCoord
	 *            initial y-coordinate to put the {@code Soldier} on
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public Soldier(Nation nation, int level, int xCoord, int yCoord, Utils utils)
	{
		super(nation, level, xCoord, yCoord, utils);
		unitType = UnitType.SOLDIER;
		utils.game.tiles[xCoord][yCoord].addUnit(this);
	}

	@Override
	protected void assignByLevel()
	{
		// Note: switch is used instead of simply writing a function to generate
		// the values since it allows for greater flexibility (e.g.
		// maxMoveDistance can flat out at 3 and level 10 can be
		// disproportionately more OP than previous levels

		// TODO make the constants realistic (current values of maxHealthPoints,
		// attackPower, and defendPower were arbitrarily chosen)
		switch (level)
		{
			case 1:
			{
				maxHealthPoints = 4;
				defendPower = 3;
				attackPower = 3;
				maxMoveDistance = 1;
				break;
			}
			case 2:
			{
				maxHealthPoints = 6;
				defendPower = 5;
				attackPower = 6;
				maxMoveDistance = 1;
				break;
			}
			case 3:
			{
				maxHealthPoints = 8;
				defendPower = 7;
				attackPower = 8;
				maxMoveDistance = 2;
				break;
			}
			case 4:
			{
				maxHealthPoints = 10;
				defendPower = 9;
				attackPower = 11;
				maxMoveDistance = 2;
				break;
			}
			case 5:
			{
				maxHealthPoints = 12;
				defendPower = 11;
				attackPower = 13;
				maxMoveDistance = 3;
				break;
			}
			case 6:
			{
				maxHealthPoints = 14;
				defendPower = 13;
				attackPower = 15;
				maxMoveDistance = 3;
				break;
			}
			case 7:
			{
				maxHealthPoints = 16;
				defendPower = 15;
				attackPower = 17;
				maxMoveDistance = 3;
				break;
			}
			case 8:
			{
				maxHealthPoints = 18;
				defendPower = 17;
				attackPower = 19;
				maxMoveDistance = 3;
				break;
			}
			case 9:
			{
				maxHealthPoints = 20;
				defendPower = 19;
				attackPower = 21;
				maxMoveDistance = 3;
				break;
			}
			case 10:
			{
				maxHealthPoints = 22;
				defendPower = 21;
				attackPower = 23;
				maxMoveDistance = 5;
				break;
			}
		}
	}

	/**
	 * Move to a certain {@code Tile}
	 *
	 * @param toTile
	 *            {@code Tile} to move to
	 * @return Whether or not {@code move()} was legal (-2 if the
	 *         {@code Soldier} has maxed out moves for the turn, -1 if the
	 *         {@code Tile}s are not adjacent, and 0 if {@code move()}
	 *         successful)
	 */
	@Override
	public int move(Tile toTile)
	{
		super.move(toTile);

		checkCityFlip(toTile);
		tile.nat = nation.nationality;

		return 0;
	}

	@Override
	protected int getMoveError(Tile toTile)
	{
		// Check if tile is not adjacent
		if ((Math.abs(tile.xCoord - toTile.xCoord) > 1)
				|| (Math.abs(tile.yCoord - toTile.yCoord) > 1)
				|| (Math.abs(tile.xCoord - toTile.xCoord) == 1)
				&& (tile.yCoord - toTile.yCoord == tile.xCoord - toTile.xCoord))
		{
			return -1;
		}

		// Check if unit has maxed out moves for the turn
		if (movesLeft <= 0)
		{
			return -2;
		}

		return 0;
	}

	private void checkCityFlip(Tile toTile)
	{
		// Check for foreign city flip
		if (toTile.city != null)
		{
			if (toTile.city.nation.nationality != nation.nationality)
			{
				toTile.city.stopGrowing();
				toTile.city = null;
			}
		}

	}

	/**
	 * Attack a {@code Tile}
	 *
	 * @param toTile
	 *            {@code Tile} to attack
	 * @return attack status
	 */
	public int attackTile(Tile toTile)
	{
		// Check if tile is not adjacent
		if ((Math.abs(tile.xCoord - toTile.xCoord) > 1)
				|| (Math.abs(tile.yCoord - toTile.yCoord) > 1)
				|| (Math.abs(tile.xCoord - toTile.xCoord) == 1)
				&& (tile.yCoord - toTile.yCoord == tile.xCoord - toTile.xCoord))
		{
			return -1;
		}

		ArrayList<Unit> unitsToAttack = new ArrayList<Unit>();

		// Determine whether or not tile is hostile
		for (Unit u : toTile.soldiers)
		{
			if (u.nation.nationality != nation.nationality)
			{
				unitsToAttack.add(u);
			}
		}
		for (Unit u : toTile.settlers)
		{
			if (u.nation.nationality != nation.nationality)
			{
				unitsToAttack.add(u);
			}
		}

		// If there is an enemy to attack:
		if (unitsToAttack.size() > 0)
		{
			Collections.sort(unitsToAttack, new Comparator<Unit>()
					{
				@Override
				public int compare(Unit o1, Unit o2)
				{
					return Double.compare(o1.defendPower, o2.defendPower);
				}
					});

			// Attack the greatest defensive power
			attackUnit(unitsToAttack.get(unitsToAttack.size() - 1));
		}

		boolean survivors = false;

		// Determine whether or not tile is hostile
		for (Unit u : toTile.soldiers)
		{
			if (u.nation.nationality != nation.nationality)
			{
				survivors = true;
			}
		}
		for (Unit u : toTile.settlers)
		{
			if (u.nation.nationality != nation.nationality)
			{
				survivors = true;
			}
		}

		if (!survivors)
		{
			toTile.nat = Nationality.NEUTRAL;
			toTile.city = null;
		}

		return 0;
	}

	/**
	 * Attacks (hits) a specific enemy {@code Unit}, checks if enemy
	 * {@code Unit} is dead, if not gets enemy {@code Unit} to hit back, and
	 * checks if self is dead
	 *
	 * @param defender
	 *            {@code Unit} to attack
	 */
	public void attackUnit(Unit defender)
	{
		utils.gw.eventLog("A " + this + " attacked a " + defender + ".");

		// Attack hits
		double damage = generateHits(attackPower, defender);
		defender.currentHealthPoints -= damage;
		utils.gw.eventLog("The " + defender + " lost "
				+ Math.round(damage * 1000) / 1000.0 + " points.");

		// Check for kill
		if (defender.currentHealthPoints <= 0)
		{
			defender.delete();
			return;
		}

		// Recieved hits
		double defendDamage = defender.generateHits(defender.defendPower, this);
		currentHealthPoints -= defendDamage;
		utils.gw.eventLog("The " + this + " lost "
				+ Math.round(defendDamage * 1000) / 1000.0 + " points.");

		// Check for kill
		if (currentHealthPoints <= 0)
			delete();
	}
}
