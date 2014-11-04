package com.lqtz.globaldomination.graphics;

//import com.lqtz.globaldomination.gameplay.*;	// Uncomment when this exists

/**
 * 
 * A hexagonal tile in the GD map
 * 
 * @author Gandalf
 * 
 */
public class Tile extends Object
{
	/**
	 * Max number of units that can fit on a file
	 */
	@SuppressWarnings("unused")
	private static final int maxUnitCapacity = 50;
	
	/**
	 * Number of units the tile contains
	 */
	private int currentUnitCapacity = 0;
	
	/**
	 * The Hexagon to represent the tile on the screen
	 */
	public Hexagon hexagon;

	// public Unit[] unitsOnTile; // Uncomment when this exists
	// this.unitsOnTile = new Unit[Tile.maxUnitCapacity]; // Uncomment when this
	// exists

	/**
	 * Revenue Cities on the Tile would collect
	 */
	public int tileRevenue;

	/**
	 * Productivity Cities on the Tile would collect
	 */
	public int tileProductivity;

	/**
	 * Whether there is a City on the Tile
	 */
	public boolean hasCity = false;

	/**
	 * A Tile in the Map
	 * 
	 * @param centerX
	 *            x-coordinate of center of the hexagon
	 * @param centerY
	 *            y-coordinate of center of the hexagon
	 * @param tileSize
	 *            radius of circumscribed circle (multiple of 8) of the hexagon
	 * @param revenue
	 *            revenue Cities on the Tile would collect
	 * @param productivity
	 *            productivity Cities on the Tile would collect
	 */
	public Tile(int centerX, int centerY, int tileSize, int revenue,
			int productivity)
	{
		// TODO Auto-generated constructor stub
		this.hexagon = new Hexagon(centerX, centerY, tileSize);
		this.tileRevenue = revenue;
		this.tileProductivity = productivity;
	}
	
	// public void addCity (City city){ // Uncomment when this exists
	// Foo
	// }

	public void addUnit(Unit unit)
	{
		if (currentUnitCapacity < 50)
		{
			currentUnitCapcity++; 
		}
		// else{
		// foo
		// }
	}
	// public void addUnit(Unit unit){ // Uncomment when this exists
	// Foo
	// }

}
