package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

/**
 * 
 * Soldier type Unit
 * 
 * @author Gandalf
 * 
 */
public class Soldier extends Unit
{
	private static final long serialVersionUID = 1L;

	/**
	 * The power the unit uses when attacking (a variable in the attack odds
	 * formula)
	 */
	public double attackPower;

	/**
	 * Initialize the Soldier
	 * 
	 * @param nation
	 *            nation of the unit
	 * @param healthPoints
	 *            max (starting) health points
	 * @param moveDistance
	 *            number of tiles the unit can move per turn
	 * @param attackPower
	 *            attack power (variable in the attack odds formula
	 * @param defendPower
	 *            defense power (variable in the defense odds formula)
	 * @param xCoord
	 *            initial x-coordinate
	 * @param yCoord
	 *            initial y-coordinate
	 */
	public Soldier(Nation nation, int level, int xCoord, int yCoord, Utils utils)
	{
		super(nation, level, xCoord, yCoord, utils);
		utils.game.tiles[xCoord][yCoord].soldiers.add(this);
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
				attackPower = 5;
				maxMoveDistance = 1;
			}
			case 2:
			{
				maxHealthPoints = 6;
				defendPower = 5;
				attackPower = 7;
				maxMoveDistance = 1;
			}
			case 3:
			{
				maxHealthPoints = 8;
				defendPower = 7;
				attackPower = 9;
				maxMoveDistance = 2;
			}
			case 4:
			{
				maxHealthPoints = 10;
				defendPower = 9;
				attackPower = 11;
				maxMoveDistance = 2;
			}
			case 5:
			{
				maxHealthPoints = 12;
				defendPower = 11;
				attackPower = 13;
				maxMoveDistance = 3;
			}
			case 6:
			{
				maxHealthPoints = 14;
				defendPower = 13;
				attackPower = 15;
				maxMoveDistance = 3;
			}
			case 7:
			{
				maxHealthPoints = 16;
				defendPower = 15;
				attackPower = 17;
				maxMoveDistance = 3;
			}
			case 8:
			{
				maxHealthPoints = 18;
				defendPower = 17;
				attackPower = 19;
				maxMoveDistance = 3;
			}
			case 9:
			{
				maxHealthPoints = 20;
				defendPower = 19;
				attackPower = 21;
				maxMoveDistance = 3;
			}
			case 10:
			{
				maxHealthPoints = 22;
				defendPower = 21;
				attackPower = 23;
				maxMoveDistance = 5;
			}
		}
	}

	/**
	 * Move to a certain tile
	 * 
	 * @param tile
	 *            tile to move to
	 * @return Whether or not move was legal (-2 if the unit has maxed out moves
	 *         for the turn, -1 if the tiles are not adjacent, and 0 if move
	 *         successful)
	 */
	public int move(Tile toTile)
	{
		// Check if unit has maxed out moves for the turn
		if (movesLeft <= 0)
			return -2;

		// Make sure tile is not adjacent
		else if ((Math.abs(tile.xCoord - toTile.xCoord) > 1)
				|| (Math.abs(tile.xCoord - toTile.xCoord) > 1))
			return -1;

		// Delete the old one
		tile.soldiers.remove(this);
		nation.units.remove(this);

		// Add new one
		tile = toTile;
		tile.soldiers.add(this);

		// Update window
		utils.game.updateWindow();

		return 0;
	}

	/**
	 * Attack a tile
	 * 
	 * @param tile
	 *            tile to attack
	 */
	public void attackTile(Tile tile)
	{
		ArrayList<Unit> unitsToAttack = new ArrayList<Unit>();

		// Determine whether or not tile is hostile
		for (Unit u : tile.soldiers)
		{
			if (u.nation.nationality == nation.nationality)
			{
				unitsToAttack.add(u);
			}
		}
		for (Unit u : tile.settlers)
		{
			if (u.nation.nationality == nation.nationality)
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
			attackUnit(unitsToAttack.get(unitsToAttack.size()));
		}
	}

	/**
	 * Attacks a specific unit (hits enemy unit based on attackHits, checks if
	 * enemy unit is dead, if not tells enemy unit to hit self based on enemy
	 * unit defenseHits, checks if self is dead
	 * 
	 * @param unit
	 *            unit to attack
	 */
	public void attackUnit(Unit unit)
	{
		double attackHits = attackPower + utils.random.nextGaussian()
				* unit.defendPower / ((attackPower + currentHealthPoints) / 2);
		unit.currentHealthPoints -= attackHits;

		if (unit.currentHealthPoints <= 0)
			unit.delete();
		else
		{
			double recievedHits = unit.defendDamage(this);
			currentHealthPoints -= recievedHits;

			if (currentHealthPoints <= 0)
				delete();
		}
	}
}
