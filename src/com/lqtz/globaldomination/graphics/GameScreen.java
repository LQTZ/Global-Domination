package com.lqtz.globaldomination.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.lqtz.globaldomination.gameplay.UnitType;
import com.lqtz.globaldomination.io.Utils;

/**
 * 
 * Game screen - where tiles are drawn
 * 
 * @author Gitdropcode
 * 
 */
public class GameScreen extends JPanel implements MouseInputListener
{
	private static final long serialVersionUID = 1L;
	private Utils utils;
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
	 * Map screen to draw tiles on
	 * 
	 * @param gw
	 *            GameWindow object for repainting on
	 * 
	 * @param utils
	 *            Game object for loading res
	 */
	public GameScreen(GameWindow gw, Utils utils)
	{
		super();
		this.gw = gw;
		this.utils = utils;

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
		final int DIM = utils.DIM;

		// Size needed to fit tiles horizontally / 8
		double sizeFitX = width / (3 * DIM - 1) / 7.0;
		// Size needed to fit tiles vertically / 8
		double sizeFitY = height / (1.5 * DIM + 0.5) / 8.0;
		// Best size for tiles
		int sizeFit = (int) (8 * (int) Math.min(sizeFitX, sizeFitY));

		// Create font size
		tileFont = utils.fonts.sourcesans.deriveFont(Font.PLAIN, sizeFit / 6);

		// Center tiles
		int xOffset = (int) ((width - sizeFit * (3 * DIM - 1) * 7 / 8) / 2);
		int yOffset = (int) ((height - sizeFit * (1.5 * DIM + 0.5)) / 2);
		tiles = new Tile[DIM][DIM];

		// Add tiles
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				tiles[i][j] = new Tile(
						i,
						j,
						sizeFit * (1 + 2 * i + j) * 7 / 8 + xOffset,
						height - (sizeFit * (3 * j + 2) / 2 + yOffset),
						sizeFit,
						(int) Math.abs(utils.random.nextGaussian() * 100 + 500),
						(int) Math.abs(utils.random.nextGaussian() * 100 + 500),
						utils);
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
	public void mousePressed(MouseEvent e)
	{
		mouseMoved(e);
		if (utils.game.selectedTile != null)
		{
			utils.game.selectedTile.isSelected = false;
		}
		utils.game.selectTile(highlightedTile);
		utils.game.updateWindow();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (highlightedTile != null)
		{
			if (highlightedTile.hexagon.contains(e.getPoint()))
			{
				return;
			}
			else
			{
				highlightedTile.isHighlighted = false;
				highlightedTile = null;
			}
		}
		for (Tile[] t0 : tiles)
		{
			for (Tile t1 : t0)
			{
				if (t1.hexagon.contains(e.getPoint()))
				{
					highlightedTile = t1;
					highlightedTile.isHighlighted = true;
				}
			}
		}
		utils.game.updateWindow();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() >= 2 && utils.game.selectedTile != null &&
				utils.game.selectedTile.city != null)
		{
			// Create array of possibilities
			String[] possibilities = new String[16];
			possibilities[0] = "--";
			for (int i = 1; i < 6; i++)
				possibilities[i] = "Settler Level " + String.valueOf(i);
			for (int i = 6; i < 16; i++)
				possibilities[i] = "Soldier Level " + String.valueOf(i - 5);

			// Display growUnit selection dialog
			String s = (String) JOptionPane.showInputDialog(gw,
					"Which unit would you like your city to work on "
							+ "right now?", "Grow Unit",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "--");

			// Check for null string
			if ((s == null) || (s == "--"))
				return;

			String utString = s.substring(0, 7);
			int ul = Integer.parseInt(s.substring("Settler Level ".length(),
					s.length()));

			if (!utils.game.selectedTile.city.isGrowing)
			{
				JOptionPane.showConfirmDialog(gw,
						"You are about to grow a unit. This cannot be"
								+ " cancelled.", "Grow Unit Confirmation",
						JOptionPane.OK_CANCEL_OPTION);
				utils.game.selectedTile.city.growUnit(
						UnitType.fromString(utString), ul);
			}
			else
			{
				JOptionPane.showMessageDialog(gw,
						"The city is already growing a unit.",
						"Cannot Grow Unit", JOptionPane.ERROR_MESSAGE);
			}
		}
		utils.game.updateWindow();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{}
}
