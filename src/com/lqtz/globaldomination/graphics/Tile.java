package com.lqtz.globaldomination.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.lqtz.globaldomination.Game;

// import com.lqtz.globaldomination.gameplay.*; // Uncomment when this exists

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
	 * The Hexagon to represent the tile on the screen
	 */
	public Hexagon hexagon;

	// public Unit[] unitsOnTile; // Uncomment when this exists
	// public unitsOnTile = new Unit[Tile.maxUnitCapacity]; // Uncomment when
	// this exists

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
	// public boolean hasCity = false;
	public boolean hasCity = true;

	private int centerX;
	private int centerY;
	private int tileSize;
	private Game game;

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
			int productivity, Game game)
	{
		this.hexagon = new Hexagon(centerX, centerY, tileSize);
		this.tileRevenue = revenue;
		this.tileProductivity = productivity;
		this.centerX = centerX;
		this.centerY = centerY;
		this.tileSize = tileSize;
		this.game = game;
	}

	protected void paint(Graphics g, Font font)
	{
		// Draw the hexagon
		g.setColor(new Color(127, 127, 127, 200));
		g.fillPolygon(hexagon);
		g.setColor(Color.BLACK);
		g.drawPolygon(hexagon);

		// Draw the city (if applicable)
		if (this.hasCity)
		{
			g.drawImage(game.images.city, (int) ((centerX - 7 * tileSize / 8)),
					(int) (centerY - tileSize / 2),
					(int) (this.tileSize * Math.sqrt(3)),
					2 * 7 / 8 * this.tileSize, null);
		}

		// Draw revenue icon
		g.drawImage(game.images.revenue, centerX - 7 * tileSize / 16, centerY
				- 3 * tileSize / 4, tileSize / 4, tileSize / 4, null);
		g.setFont(font);
		g.drawString(String.valueOf(tileRevenue), centerX - 7 * tileSize / 16,
				centerY - 2 * tileSize / 4);
	}
}
