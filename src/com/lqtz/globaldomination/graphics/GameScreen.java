package com.lqtz.globaldomination.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.lqtz.globaldomination.gameplay.Game;

/**
 * 
 * Game screen - where tiles are drawn
 * 
 * @author Gitdropcode
 * 
 */
public class GameScreen extends JPanel implements MouseListener,
		MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	private final int DIM = 5;
	private Game game;
	private Font tileFont;
	private GameWindow gw;

	/**
	 * All the tiles on the map
	 */
	public Tile[][] tiles;

	/**
	 * Tile currently being moused over
	 */
	public Tile highlightedTile;

	/**
	 * Tile currently selected (clicked)
	 */
	public Tile selectedTile;

	/**
	 * Map screen to draw tiles on
	 * 
	 * @param gw
	 *            GameWindow object for repainting on
	 * 
	 * @param game
	 *            Game object for loading res
	 */
	public GameScreen(GameWindow gw, Game game)
	{
		super();
		this.gw = gw;
		this.game = game;

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Adds all the Hexagons
	 * 
	 * @param width
	 *            width of the panel
	 * @param height
	 *            height of the panel
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

		// Add tiles
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				tiles[i][j] = new Tile(i, j, sizeFit * (1 + 2 * i + j) * 7 / 8
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
	 *            graphics device for painting
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (Tile[] tileList : tiles)
		{
			for (Tile tile : tileList)
			{
				tile.paint(g, tileFont);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (selectedTile != null)
			selectedTile.isSelected = false;
		if (highlightedTile != null)
		{
			selectedTile = highlightedTile;
			selectedTile.isSelected = true;
		}
		gw.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (highlightedTile != null)
		{
			highlightedTile.isHighlighted = false;
			highlightedTile = null;
		}

		for (Tile[] t0 : tiles)
			for (Tile t1 : t0)
			{
				if (t1.hexagon.contains(e.getPoint()))
				{
					highlightedTile = t1;
					highlightedTile.isHighlighted = true;
				}
			}
		gw.repaint();
	}
}
