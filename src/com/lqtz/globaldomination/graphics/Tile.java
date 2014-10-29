package com.lqtz.globaldomination.graphics;

//import com.lqtz.globaldomination.gameplay.*;	// Uncomment when this exists

/**
 * 
 * @author Gandalf A hexagonal tile in the GD map
 * 
 */
public class Tile extends Object
{

	private static final int tileSize = 8; // Change this to the real value in
											// future
	private static final int maxUnitCapacity = 50; // Max number of units that
													// can fit on a tile

	public Hexagon hexagon;
	// public Unit[] unitsOnTile; // Uncomment when this exists
	// this.unitsOnTile = new Unit[Tile.maxUnitCapacity]; // Uncomment when this
	// exists
	public int tileRevenue;
	public int tileProductivity;
	public boolean hasCity = false;

	public Tile(int centerX, int centerY, int size, int revenue,
			int productivity)
	{
		// TODO Auto-generated constructor stub
		this.hexagon = new Hexagon(centerX, centerY, Tile.tileSize);
		this.tileRevenue = revenue;
		this.tileProductivity = productivity;
	}

	// public void addCity(City city){ // Uncomment when this exists
	// Foo
	// }

	// public void addUnit(Unit unit){ // Uncomment when this exists
	// Foo
	// }

}
