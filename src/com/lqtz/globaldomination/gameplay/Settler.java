/**
 * 
 */
package com.lqtz.globaldomination.gameplay;

/**
 * 
 * Settler type Unit
 * 
 * @author Gandalf
 * 
 */
public class Settler extends Unit
{
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize the Settler
	 * 
	 * @param nation
	 *            nation of the unit
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
	public Settler(Nation nation, double healthPoints, int moveDistance,
			double defendPower, int xCoord, int yCoord)
	{
		super(nation, healthPoints, moveDistance, defendPower, xCoord, yCoord);
	}

	/**
	 * Create a City object on the tile the Settler is on
	 * 
	 * @return number of turns it will take to build the city
	 */
	public int buildCity()
	{
		// TODO create method once City class exists
		return 0;
	}
}
