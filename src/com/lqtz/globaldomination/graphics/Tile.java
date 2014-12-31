package com.lqtz.globaldomination.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.lqtz.globaldomination.gameplay.Game;
import com.lqtz.globaldomination.gameplay.Settler;
import com.lqtz.globaldomination.gameplay.Unit;

/**
 * 
 * A hexagonal tile in the GD map
 * 
 * @author Gandalf
 * 
 */
public class Tile
{
	// /**
	// * Max number of units that can fit on a file
	// */
	// private static final int maxUnitCapacity = 50;

	/**
	 * The Hexagon to represent the tile on the screen
	 */
	public Hexagon hexagon;

	/**
	 * The units currently on the tile
	 */
	public ArrayList<Unit> unitsOnTile;

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
	public boolean hasCity = true;

	/**
	 * Tile's x-coordinate in the map
	 */
	public int xCoord;

	/**
	 * Tile's y-coordinate in the map
	 */
	public int yCoord;

	/**
	 * Whether or not the Tile is currently being moused over
	 */
	public boolean isHighlighted;

	/**
	 * Whether or not the Tile currently selected (clicked)
	 */
	public boolean isSelected;

	private int centerX;
	private int centerY;
	private int tileSize;
	private Game game;

	/**
	 * A Tile in the Map
	 * 
	 * @param xCoord
	 * @param yCoord
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
	 * @param game
	 *            Game object for loading res
	 */
	public Tile(int xCoord, int yCoord, int centerX, int centerY, int tileSize,
			int revenue, int productivity, Game game)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.hexagon = new Hexagon(centerX, centerY, tileSize);
		this.unitsOnTile = new ArrayList<Unit>();
		this.tileRevenue = revenue;
		this.tileProductivity = productivity;
		this.centerX = centerX;
		this.centerY = centerY;
		this.tileSize = tileSize;
		this.game = game;
		this.isHighlighted = false;
	}

	protected void paint(Graphics g, Font font)
	{
		g.setFont(font); // Init font

		// Fill hexagon based on its selected/highlighted status
		Color fillColor;
		if (isSelected)
			fillColor = new Color(200, 235, 50, 200);
		else if (isHighlighted)
			fillColor = new Color(130, 140, 130);
		else
			fillColor = new Color(127, 127, 127, 200);
		g.setColor(fillColor);
		g.fillPolygon(hexagon);

		// Draw hexagon border
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

		// Find the size of string in the font
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(String.valueOf(tileRevenue), g);

		int textHeight = (int) (rect.getHeight());
		int textWidth = (int) (rect.getWidth());

		// Find the center of the icon
		int cornerX = (int) (((centerX - 7 * tileSize / 16) + tileSize / 8) - 
				(textWidth / 2));
		int cornerY = (int) (((centerY - 3 * tileSize / 4 - (textHeight / 2)) +
				tileSize / 8) + fm.getAscent());

		g.drawString(String.valueOf(tileRevenue), cornerX, cornerY);

		// Draw productivity icon
		g.drawImage(game.images.productivity, centerX + 7 * tileSize / 16
				- tileSize / 4, centerY - 3 * tileSize / 4, tileSize / 4,
				tileSize / 4, null);

		// Find the center of the icon based on the calculated difference in x
		// between the 2 icons
		cornerX += 5 * tileSize / 8;

		g.drawString(String.valueOf(tileProductivity), cornerX, cornerY);

		// Count the number of settlers and the number of soldiers on the tile
		int settlerCount = 0;
		int soldierCount = 0;
		for (Unit u : unitsOnTile)
		{
			if (u instanceof Settler)
				settlerCount++;
			else
				soldierCount++;
		}

		// Draw the settler units icon
		g.drawImage(game.images.settler, centerX + 7 * tileSize / 16 - tileSize
				/ 4, centerY + 3 * tileSize / 4 - tileSize / 4, tileSize / 4,
				tileSize / 4, null);

		// Find the center of the icon based on the calculated difference in y
		// between the 2 icons
		cornerY += 5 * tileSize / 4;

		g.drawString(String.valueOf(settlerCount), cornerX, cornerY);

		// Draw the soldier units icon
		g.drawImage(game.images.soldier, centerX - 7 * tileSize / 16, centerY
				+ 3 * tileSize / 4 - tileSize / 4, tileSize / 4, tileSize / 4,
				null);

		// Find the center of the icon based on the calculated difference in x
		// between the 2 icons
		cornerX -= 5 * tileSize / 8;

		g.drawString(String.valueOf(soldierCount), cornerX, cornerY);
	}
}
