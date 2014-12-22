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
	public int moveDistance;

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
		this.moveDistance = moveDistance;
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
			// Order list from smallest defensive power to greatest defensive
			// power
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

	private void attackUnit(Unit unit)
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

	private double defendDamage(Unit unit)
	{
		return defendPower + (new Random()).nextGaussian() * unit.attackPower
				/ ((defendPower + currentHealthPoints) / 2);
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
