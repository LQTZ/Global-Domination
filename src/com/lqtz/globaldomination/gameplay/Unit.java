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

	/**
	 * Move the {@code Unit} to a specific {@code Tile} if legal
	 * 
	 * @param tile
	 *            {@code Tile} to move to
	 * @return exit status (see implementations)
	 */
	public abstract int move(Tile tile);

	/**
	 * Randomly generate hits to hit an enemy {@code Unit} with in a fight
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
				/ attackerEffectivePower * utils.random.nextDouble();

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
		utils.game.gw.eventLog("A " + this.nation.nationality.toString() + " "
				+ unitType.toString() + " unit died on Tile "
				+ (tile.xCoord + 1) + ", " + (tile.yCoord + 1));

		// Remove references to the object
		nation.units.remove(this);
		if (tile.soldiers.size() + tile.settlers.size() == 0
				&& tile.city == null)
		{
			tile.nat = Nationality.NEUTRAL;
		}
	}
	
	public void onDeserialization(Utils utils)
	{
		this.utils = utils;
	}
}
