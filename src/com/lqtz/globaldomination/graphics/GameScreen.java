/*******************************************************************************
 * Global Domination is a strategy game.
 * Copyright (C) 2014, 2015  LQTZ Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.lqtz.globaldomination.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.lqtz.globaldomination.gameplay.Nationality;
import com.lqtz.globaldomination.gameplay.Soldier;
import com.lqtz.globaldomination.io.Utils;

public class GameScreen extends JPanel implements MouseInputListener
{
	private static final long serialVersionUID = 1L;
	private Utils utils;
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
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public GameScreen(Utils utils)
	{
		super();
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
	 * @param addTiles
	 *            whether or not to add new {@code Tile}s
	 */
	public void init(int width, int height, boolean addTiles)
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

		if (addTiles)
		{
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

		// If move
		if (utils.game.moveSelected && utils.game.selectedTile != null
				&& utils.game.selectedUnit != null)
		{
			if (!utils.game.selectedTile.settlers.isEmpty()
					&& !utils.game.selectedTile.soldiers.isEmpty()
					&& utils.game.selectedTile.nat != utils.game.selectedUnit.nation.nationality)
			{
				JOptionPane.showMessageDialog(utils.gw,
						"You cannot move to an enemy tile.", "Bad Tile",
						JOptionPane.ERROR_MESSAGE);
			}

			else
			{
				int moveStatus = utils.game.selectedUnit
						.move(utils.game.selectedTile);
				switch (moveStatus)
				{
					case -1:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"You cannot move to a non-adjacent tile.",
								"Bad Tile", JOptionPane.ERROR_MESSAGE);
						break;
					}

					case -2:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"This unit is exhausted, you cannot move it.",
								"Too Much Moving", JOptionPane.ERROR_MESSAGE);
						break;
					}

					case -3:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"This Settler is building, you "
										+ "cannot interupt its building.",
										"Building", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
			}

			utils.game.moveSelected = false;
			utils.game.selectUnit(null);
		}

		// If attack
		if (utils.game.attackSelected && utils.game.selectedTile != null)
		{
			if (utils.game.selectedTile.nat == utils.game.selectedUnit.nation.nationality
					|| utils.game.selectedTile.nat == Nationality.NEUTRAL)
			{
				JOptionPane.showMessageDialog(utils.gw,
						"You cannot attack a friendly tile.", "Bad Tile",
						JOptionPane.ERROR_MESSAGE);
			}

			else
			{
				int attackStatus = ((Soldier) utils.game.selectedUnit)
						.attackTile(utils.game.selectedTile);

				switch (attackStatus)
				{
					case -1:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"You cannot attack a non adjacent tile.",
								"Bad Tile", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}

				utils.game.attackSelected = false;
				utils.game.selectUnit(null);
			}
		}

		utils.game.selectedUnit = null;
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
	{}

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
