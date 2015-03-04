package com.lqtz.globaldomination.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.lqtz.globaldomination.gameplay.Nationality;
import com.lqtz.globaldomination.gameplay.Soldier;
import com.lqtz.globaldomination.gameplay.UnitType;
import com.lqtz.globaldomination.io.Utils;

public class GameScreen extends JPanel implements MouseInputListener
{
	private static final long serialVersionUID = 1L;
	private Utils utils;
	private GameWindow gw;
	private Font tileFont;

	/**
	 * All the {@code Tile}s on the map
	 */
	public Tile[][] tiles;

	/**
	 * {@code Tile} currently being moused over
	 */
	public Tile highlightedTile;

	/**
	 * Map {@code JPanel} to draw {@code Tile}s on
	 * 
	 * @param gw
	 *            {@code GameWindow} for painting on
	 * 
	 * @param utils
	 *            GD {@code Utils} utility
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
	 * Add all the {@code Hexagon}s
	 * 
	 * @param width
	 *            width of the {@code GameScreen}
	 * @param height
	 *            height of the {@code GameScreen}
	 */
	public void addTiles(int width, int height)
	{
		final int DIM = utils.DIM;

		// Size needed to fit tiles horizontally / 8
		double sizeFitX = width / (3 * DIM - 1) / 7.0;
		// Size needed to fit tiles vertically / 8
		double sizeFitY = height / (1.5 * DIM + 0.5) / 8.0;
		// Best size for tiles
		int sizeFit = 8 * (int) Math.min(sizeFitX, sizeFitY);

		// Create font size
		tileFont = utils.fonts.sourcesans.deriveFont(Font.PLAIN, sizeFit / 6);

		// Center tiles
		int xOffset = (width - sizeFit * (3 * DIM - 1) * 7 / 8) / 2;
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
	 * Paints all the {@code Hexagon}s in the {@code GameScreen}
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
		utils.game.selectTile(highlightedTile);
		utils.game.updateWindow();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		// Highlight tile being moused over
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
		// If double-click on a city tile, growunit popup
		if (e.getClickCount() >= 2 && utils.game.selectedTile != null
				&& utils.game.selectedTile.city != null)
		{
			// Make sure city belongs to current player (cannot be in previous
			// if because null pointer exception if selectedTile is null)
			if (utils.game.selectedTile.nat != utils.game.turnNationality)
			{
				JOptionPane.showMessageDialog(gw, "This city is "
						+ utils.game.selectedTile.nat
						+ ", you cannot grow units here.", "Cannot Grow Unit",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Make sure city not busy
			if (utils.game.selectedTile.city.isGrowing)
			{
				JOptionPane.showMessageDialog(gw,
						"The city is already growing a unit.",
						"Cannot Grow Unit", JOptionPane.ERROR_MESSAGE);
				return;
			}

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

			int confirm = JOptionPane.showConfirmDialog(gw,
					"You are about to grow a unit. This cannot be"
							+ " cancelled.", "Grow Unit Confirmation",
					JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.OK_OPTION)
			{
				utils.game.selectedTile.city.growUnit(
						UnitType.fromString(utString), ul);
			}
		}

		// If move
		if (utils.game.moveSelected
				&& (utils.game.selectedTile.nat == utils.game.selectedUnit.nation.nationality || utils.game.selectedTile.nat == Nationality.NEUTRAL))
		{
			utils.game.selectedUnit.move(utils.game.selectedTile);
			utils.game.moveSelected = false;
			utils.game.selectUnit(null);
			utils.game.gw.buttons[0].setBackground(utils.buttonColors
					.get((utils.game.gw.buttons[0].getText())));
		}

		// If attack
		if (utils.game.attackSelected
				&& utils.game.selectedTile.nat != utils.game.selectedUnit.nation.nationality
				&& utils.game.selectedTile.nat != Nationality.NEUTRAL)
		{
			((Soldier) utils.game.selectedUnit)
					.attackTile(utils.game.selectedTile);
			utils.game.moveSelected = false;
			utils.game.selectUnit(null);
			utils.game.gw.buttons[3].setBackground(utils.buttonColors
					.get((utils.game.gw.buttons[3].getText())));

		}
		
		utils.game.selectedUnit = null;
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
