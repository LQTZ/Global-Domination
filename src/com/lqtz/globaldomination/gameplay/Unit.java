package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

/**
 * 
 * Unit in the game
 * 
 * @author Gandalf
 * 
 */
public abstract class Unit implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Nation of the unit
	 */
	public Nation nation;

	/**
	 * The tile the unit is currently on
	 */
	public Tile tile;

	/**
	 * Maximum number of health points the unit can have (also the starting hp
	 * level)
	 */
	public double maxHealthPoints;

	/**
	 * The number of health points the unit currently has
	 */
	public double currentHealthPoints;

	/**
	 * The number of tiles the unit can move per turn
	 */
	public int maxMoveDistance;

	/**
	 * The number of tiles the unit has left to move on the current turn
	 */
	public int movesLeft;

	/**
	 * The power the unit uses when defending (a variable in the defense odds
	 * formula)
	 */
	public double defendPower;

	/**
	 * Unit level (hp and power depend on this)
	 */
	public int level;

	protected Utils utils;

	/**
	 * Initialize the unit
	 * 
	 * @param nation
	 *            nation of the Unit
	 * @param healthPoints
	 *            max (starting) health points
	 * @param moveDistance
	 *            number of tiles the unit can move per turn
	 * @param defendPower
	 *            defense power (variable in the defense odds formula)
	 * @param xCoord
	 *            initial x-coordinate
	 * @param yCoord
	 *            initial y-coordinate
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

	public abstract int move(Tile tile);

	/**
	 * Randomly generate hits to hit an enemy Unit with in a fight
	 * 
	 * @param power
	 *            power level being used against enemy Unit
	 * @param againstUnit
	 *            enemy unit
	 * @return hits to hit enemy Unit with
	 */
	public double generateHits(double power, Unit againstUnit)
	{
		double thisEffectivePower = power * currentHealthPoints
				/ maxHealthPoints;
		double attackerEffectivePower = againstUnit.defendPower
				* againstUnit.currentHealthPoints / againstUnit.maxHealthPoints;

		double hits = thisEffectivePower * thisEffectivePower
				/ attackerEffectivePower * utils.random.nextDouble();

		if (hits < 0)
			return 0;
		else
			return hits;
	}

	/**
	 * Removes all references to the unit
	 */
	public void delete()
	{
		nation.units.remove(this);
		if (this instanceof Settler)
		{
			tile.settlers.remove(this);
		}
		else
		{
			tile.soldiers.remove(this);
		}
	}
}
