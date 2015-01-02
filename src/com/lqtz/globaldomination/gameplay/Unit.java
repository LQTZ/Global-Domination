package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

import com.lqtz.globaldomination.graphics.Tile;

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
	 * Position on map (x and y coordinates)
	 */
	public int[] position;

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
	 * Current x-coordinate on the map
	 */
	public int xCoord;

	/**
	 * Current y-coordinate on the map
	 */
	public int yCoord;
	
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
	public Unit(Nation nation, double healthPoints, int moveDistance,
			double defendPower, int xCoord, int yCoord, Utils utils)
	{
		// Initialize fields
		this.nation = nation;
		this.maxHealthPoints = healthPoints;
		this.currentHealthPoints = healthPoints;
		this.maxMoveDistance = moveDistance;
		this.defendPower = defendPower;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.utils = utils;

		// TODO make unit level
	}

	/**
	 * Generate defenseHits value
	 * 
	 * @param soldier
	 *            soldier defending from
	 * @return defenseHits to pass to attacker's attackUnit method
	 */
	public double defendDamage(Soldier soldier)
	{
		return defendPower + utils.random.nextGaussian()
				* soldier.attackPower
				/ ((defendPower + currentHealthPoints) / 2);
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
	public int move(Tile tile)
	{
		// Check if unit has maxed out moves for the turn
		if (movesLeft <= 0)
			return -2;

		// Check if tile is not adjacent
		else if (!((Math.abs((this.tile.xCoord - tile.xCoord)) <= 1) && (Math
				.abs((this.tile.xCoord - tile.xCoord)) <= 1)))
			return -1;

		// If move is legal, switch the unit's tile to the new tile
		else
		{
			this.tile = tile;
			return 0;
		}
	}

	/**
	 * Removes all references to the unit
	 */
	public void delete()
	{
		nation.units.remove(this);
		tile.city.units.remove(this);
	}
}
