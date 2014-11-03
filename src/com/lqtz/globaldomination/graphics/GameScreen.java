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

	public void addTiles()
	{
		tiles = new Tile[5][5];

		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				tiles[i][j] = new Tile(100, 100, 0, 0);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawPolygon(tiles[0][0].hexagon);
	}
}
