package com.lqtz.globaldomination.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * Game screen - where tiles are drawn
 * 
 * @author Gitdropcode
 * 
 */
public class GameScreen extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Tile[][] tiles;
	private final int DIM = 5;

	public void addTiles(int width, int height)
	{
		// Size needed to fit tiles horizontally / 8
		double sizeFitX = width / (3 * DIM - 1) / 7.0;
		// Size needed to fit tiles vertically / 8
		double sizeFitY = height / (1.5 * DIM + 0.5) / 8.0;
		// Best size for tiles
		int sizeFit = (int) (8 * (int) Math.min(sizeFitX, sizeFitY));

		// Center tiles
		int xOffset = (int) ((width - sizeFit * (3 * DIM - 1) * 7 / 8) / 2);
		int yOffset = (int) ((height - sizeFit * (1.5 * DIM + 0.5)) / 2);
		tiles = new Tile[5][5];

		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				tiles[i][j] = new Tile(sizeFit * (1 + 2 * i + j) * 7 / 8
						+ xOffset, height - (sizeFit * (3 * j + 2) / 2 + yOffset),
						sizeFit, 0, 0);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		for (Tile[] tileList : tiles)
		{
			for (Tile tile : tileList)
			{
				g.drawPolygon(tile.hexagon);
			}
		}
	}
}
