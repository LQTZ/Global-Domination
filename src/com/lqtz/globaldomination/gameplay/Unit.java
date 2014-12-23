package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;
import java.util.Random;

import com.lqtz.globaldomination.graphics.Tile;

/**
 * @author Gandalf
 * 
 */
public class Unit implements java.io.Serializable
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
	 * The power the unit uses when attacking (a variable in the attack odds
	 * formula)
	 */
	public double attackPower;

	/**
	 * The power the unit uses when defending (a variable in the defense odds
	 * formula)
	 */
	public double defendPower;

	/**
	 * Initialize the unit
	 * 
	 * @param nation
	 *            nation of the unit
	 * @param healthPoints
	 *            max (starting) health points
	 * @param moveDistance
	 *            number of tiles the unit can move per turn
	 * @param attackPower
	 *            attack power (variable in attack odds formula)
	 * @param defendPower
	 *            defense power (variable in the defense odds formula)
	 */
	public Unit(Nation nation, double healthPoints, int moveDistance,
			double attackPower, double defendPower)
	{
		// Initialize fields
		this.nation = nation;
		this.maxHealthPoints = healthPoints;
		this.currentHealthPoints = healthPoints;
		this.maxMoveDistance = moveDistance;
		this.attackPower = attackPower;
		this.defendPower = defendPower;
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
		for (Unit u : tile.unitsOnTile)
		{
			if (u.nation.nationality == nation.nationality)
			{
				unitsToAttack.add(u);
			}
		}

		// If there is an enemy to attack:
		if (unitsToAttack.size() > 0)
		{
			// Order list from smallest to greatest defensive power
			Unit u;
			for (int i = 0; i < unitsToAttack.size(); i++)
			{
				if (unitsToAttack.get(i).defendPower < unitsToAttack.get(0).defendPower)
				{
					u = unitsToAttack.get(i);
					unitsToAttack.remove(unitsToAttack.get(i));
					unitsToAttack.add(0, u);
				}
			}

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
		double attackHits = attackPower + (new Random()).nextGaussian()
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

	/**
	 * Generate defenseHits value
	 * 
	 * @param unit
	 *            unit defending from
	 * @return defenseHits to pass to attacker's attackUnit method
	 */
	public double defendDamage(Unit unit)
	{
		return defendPower + (new Random()).nextGaussian() * unit.attackPower
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
		if (this.movesLeft <= 0)
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
		this.nation.units.remove(this);
		this.tile.unitsOnTile.remove(this);
	}
}
