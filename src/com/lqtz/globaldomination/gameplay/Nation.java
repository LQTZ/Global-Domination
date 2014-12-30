package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;

/**
 * 
 * Object representing a nation (player) in the game
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
	 * Add a soldier to the Nation's units list
	 * 
	 * @param healthPoints
	 * @param moveDistance
	 * @param attackPower
	 * @param defendPower
	 * @param xCoord
	 * @param yCoord
	 */
	public void addSoldier(double healthPoints, int moveDistance,
			double attackPower, double defendPower, int xCoord, int yCoord)
	{
		Soldier s = new Soldier(this, healthPoints, moveDistance, attackPower,
				defendPower, xCoord, yCoord);
		units.add(s);
	}

	/**
	 * Add a settler to the Nation's units list
	 * 
	 * @param healthPoints
	 * @param moveDistance
	 * @param defendPower
	 * @param xCoord
	 * @param yCoord
	 */
	public void addSettler(double healthPoints, int moveDistance,
			double defendPower, int xCoord, int yCoord)
	{
		Settler s = new Settler(this, healthPoints, moveDistance, defendPower,
				xCoord, yCoord);
		units.add(s);
	}
}
