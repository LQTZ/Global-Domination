package com.lqtz.globaldomination.graphics;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.lqtz.globaldomination.io.Game;

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
	private Game game;
	private Font tileFont;

	/**
	 * Map screen to draw tiles on
	 * 
	 * @param game
	 *            Game object for loading res
	 */
	public GameScreen(Game game)
	{
		super();
		this.game = game;
	}

	/**
	 * Adds all the Hexagons
	 * 
	 * @param width
	 *            Width of the panel
	 * @param height
	 *            Height of the panel
	 */
	public void addTiles(int width, int height)
	{
		// Size needed to fit tiles horizontally / 8
		double sizeFitX = width / (3 * DIM - 1) / 7.0;
		// Size needed to fit tiles vertically / 8
		double sizeFitY = height / (1.5 * DIM + 0.5) / 8.0;
		// Best size for tiles
		int sizeFit = (int) (8 * (int) Math.min(sizeFitX, sizeFitY));

		// Create font size
		tileFont = game.fonts.sourcesans.deriveFont(Font.PLAIN, sizeFit / 4);

		// Center tiles
		int xOffset = (int) ((width - sizeFit * (3 * DIM - 1) * 7 / 8) / 2);
		int yOffset = (int) ((height - sizeFit * (1.5 * DIM + 0.5)) / 2);
		tiles = new Tile[DIM][DIM];

		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				tiles[i][j] = new Tile(sizeFit * (1 + 2 * i + j) * 7 / 8
						+ xOffset, height
						- (sizeFit * (3 * j + 2) / 2 + yOffset), sizeFit, 0, 0,
						game);
			}
		}
	}

	@Override
	/**
	 * Paints all the Hexagons in the GameScreen
	 * 
	 * @param g
	 *            Graphics device for painting
	 */
	protected void paintComponent(Graphics g)
	{
		for (Tile[] tileList : tiles)
		{
			for (Tile tile : tileList)
			{
				tile.paint(g, tileFont);
			}
		}
	}
}
