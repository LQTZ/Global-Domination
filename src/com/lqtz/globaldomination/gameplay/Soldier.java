/**
 * 
 */
package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;
import java.util.Random;

import com.lqtz.globaldomination.graphics.Tile;

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
	public Soldier(Nation nation, double healthPoints, int moveDistance,
			double attackPower, double defendPower, int xCoord, int yCoord)
	{
		super(nation, healthPoints, moveDistance, defendPower, xCoord, yCoord);
		// TODO Auto-generated constructor stub
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

}
