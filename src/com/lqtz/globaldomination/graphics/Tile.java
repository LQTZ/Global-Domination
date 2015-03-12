package com.lqtz.globaldomination.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lqtz.globaldomination.gameplay.City;
import com.lqtz.globaldomination.gameplay.Nationality;
import com.lqtz.globaldomination.gameplay.Settler;
import com.lqtz.globaldomination.gameplay.Soldier;
import com.lqtz.globaldomination.io.Utils;

public class Tile
{
	private Utils utils;
	private int centerX;
	private int centerY;
	private int tileSize;

	/**
	 * {@code Hexagon} to represent the {@code Tile} on the screen
	 */
	public Hexagon hexagon;

	/**
	 * {@code Nationality} of the {@code Tile}
	 */
	public Nationality nat;

	/**
	 * Revenue {@code City}s on the {@code Tile} would collect
	 */
	public int tileRevenue;

	/**
	 * Productivity {@code City}s on the {@code Tile} would collect
	 */
	public int tileProductivity;

	/**
	 * {@code City} on the {@code Tile} (null if no city)
	 */
	public City city;

	/**
	 * {@code Tile}'s x-coordinate in the map
	 */
	public int xCoord;

	/**
	 * {@code Tile}'s y-coordinate in the map
	 */
	public int yCoord;

	/**
	 * Whether or not the {@code Tile} is currently being moused over
	 */
	public boolean isHighlighted;

	/**
	 * Whether or not the {@code Tile} currently selected (clicked)
	 */
	public boolean isSelected;

	/**
	 * All the {@code Settler}s on the {@code Tile}
	 */
	public ArrayList<Settler> settlers;

	/**
	 * All the {@code Soldier}s on the {@code Tile}
	 */
	public ArrayList<Soldier> soldiers;

	/**
	 * {@code HashMap} that contains all possible colors. Each array of colors
	 * has the following format:
	 * 
	 * <p>
	 * <code>{normalColor, highlightedColor, selectedColor}</code>
	 */
	private Map<Nationality, Color[]> colors;

	/**
	 * A {@code Tile} in the Map
	 * 
	 * @param xCoord
	 *            x-coordinate of the {@code Tile} on the map
	 * @param yCoord
	 *            y-coordinate of the {@code Tile} on the map
	 * @param centerX
	 *            x-coordinate of center of the {@code Hexagon}
	 * @param centerY
	 *            y-coordinate of center of the {@code Hexagon}
	 * @param tileSize
	 *            radius of circumscribed circle (multiple of 8) of the
	 *            {@code Hexagon}
	 * @param revenue
	 *            revenue Cities on the {@code Tile} would collect
	 * @param productivity
	 *            productivity Cities on the {@code Tile} would collect
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public Tile(int xCoord, int yCoord, int centerX, int centerY, int tileSize,
			int revenue, int productivity, Utils utils)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		hexagon = new Hexagon(centerX, centerY, tileSize);
		this.tileRevenue = revenue;
		this.tileProductivity = productivity;
		this.centerX = centerX;
		this.centerY = centerY;
		this.tileSize = tileSize;
		this.utils = utils;

		this.isHighlighted = false;
		this.city = null;
		nat = Nationality.NEUTRAL;

		soldiers = new ArrayList<Soldier>();
		settlers = new ArrayList<Settler>();

		colors = new HashMap<Nationality, Color[]>();
		colors.put(Nationality.RED, new Color[] {new Color(255, 0, 0, 150),
				new Color(255, 127, 127, 150), new Color(127, 0, 0)});
		colors.put(Nationality.GREEN, new Color[] {new Color(0, 255, 0, 150),
				new Color(127, 255, 127, 150), new Color(0, 127, 0)});
		colors.put(Nationality.YELLOW, new Color[] {
				new Color(191, 191, 0, 150), new Color(255, 255, 191, 150),
				new Color(63, 63, 0)});
		colors.put(Nationality.BLUE, new Color[] {new Color(0, 0, 255, 150),
				new Color(127, 127, 255, 150), new Color(0, 0, 200)});
		colors.put(Nationality.NEUTRAL, new Color[] {
				new Color(127, 127, 127, 150), new Color(191, 191, 191, 150),
				new Color(63, 63, 63)});
	}

	protected void paint(Graphics g, Font font)
	{
		g.setFont(font); // Init font

		// Fill hexagon based on its selected/highlighted status
		Color fillColor;
		int colorState = 0;
		if (isSelected)
		{
			colorState = 2;
		}
		else if (isHighlighted)
		{
			colorState = 1;
		}
		fillColor = colors.get(nat)[colorState];

		g.setColor(fillColor);
		g.fillPolygon(hexagon);

		// Draw hexagon border
		g.setColor(Color.BLACK);
		g.drawPolygon(hexagon);

		int size = tileSize / 4;

		// Calculate coordinates of each corner
		int xOffset = tileSize * 7 / 16;
		int yOffset = tileSize * 3 / 4;
		int xMin = centerX - xOffset;
		int xMax = centerX + xOffset - size;
		int yMin = centerY - yOffset;
		int yMax = centerY + yOffset - size;

		// Draw city-related icons
		if (city != null)
		{
			g.drawImage(utils.images.city, centerX - 2 * xOffset, centerY
					- tileSize / 2, tileSize * 7 / 4, tileSize, null);
		}

		// Draw the settler units icon
		g.drawImage(utils.images.settler, xMin, yMax, size, size, null);

		drawCenterText(g, String.valueOf(settlers.size()), xMin, yMax);

		// Draw the soldier units icon
		g.drawImage(utils.images.soldier, xMax, yMax, size, size, null);

		drawCenterText(g, String.valueOf(soldiers.size()), xMax, yMax);

		// Draw revenue icon
		g.drawImage(utils.images.revenue, xMin, yMin, size, size, null);

		drawCenterText(g, String.valueOf(tileRevenue), xMin, yMin);

		// Draw productivity icon
		g.drawImage(utils.images.productivity, xMax, yMin, size, size, null);

		drawCenterText(g, String.valueOf(tileProductivity), xMax, yMin);
	}

	private void drawCenterText(Graphics g, String str, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(str);
		int yOffset = (fm.getAscent() - fm.getDescent()) / 2;

		// width and yOffset center it to the point (x, y), but we need to
		// center the text to the center of the image --> tileSize / 8
		g.drawString(str, x - width / 2 + tileSize / 8, y + yOffset + tileSize
				/ 8);

	}
}
