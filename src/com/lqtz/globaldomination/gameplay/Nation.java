package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;

/**
 * A nation.
 * 
 * @author Gandalf
 * 
 */
public class Nation
{
	/**
	 * Nationality of the nation
	 */
	public Nationality nationality;

	/**
	 * Units that belong to the nation
	 */
	public ArrayList<Unit> units;

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
	 * Creates new nation
	 * 
	 * @param nationality
	 *            nationality of the nation
	 */
	public Nation(Nationality nationality)
	{
		this.nationality = nationality;
	}

	/**
	 * Add a unit to the nation's forces
	 * 
	 * @param healthPoints
	 *            max (starting) health points
	 * @param moveDistance
	 *            number of tiles the unit can move per turn
	 * @param attackPower
	 *            attack power (variable in attack odds formula)
	 * @param defendPower
	 *            defense power (variable in the defense odds formula)
	 */
	public void addUnit(float healthPoints, int moveDistance,
			float attackPower, float defendPower)
	{
		// TODO make hp, moveDistance, attackPower, and defendPower based on
		// unit level
		units.add(new Unit(this, 10, 1, 1, 1));
	}

}
