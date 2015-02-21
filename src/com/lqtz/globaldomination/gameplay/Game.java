package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import com.lqtz.globaldomination.graphics.GameWindow;
import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;
	private transient Utils utils;

	/**
	 * {@code GameWindow} to display the game
	 */
	public transient GameWindow gw;

	/**
	 * {@code Nationality} of the player whose turn it is currently
	 */
	public Nationality turn;

	/**
	 * Map of {@code Tile}s
	 */
	public Tile[][] tiles;

	/**
	 * Currently selected (clicked) {@code Tile}
	 */
	public Tile selectedTile;

	/**
	 * Currently selected (clicked) {@code Unit}
	 */
	public Unit selectedUnit;

	/**
	 * Move button has been clicked
	 */
	public boolean moveSelected = false;

	/**
	 * Attack button has been clicked
	 */
	public boolean attackSelected = false;

	/**
	 * {@code CountdownTask}s currently running
	 */
	public ArrayList<CountdownTask> countdownTasks;

	/**
	 * All Nations currently in the game
	 */
	public Nation[] nations;

	/**
	 * A GD game
	 *
	 * @param utils
	 *            GD {@code Utils} utility
	 * @param gw
	 *            {@code GameWindow} {@code JFrame} to play on
	 * @param tiles
	 *            map of {@code Tile}s
	 */
	public Game(Utils utils, GameWindow gw, Tile[][] tiles)
	{
		this.tiles = tiles;
		this.utils = utils;
		this.gw = gw;

		this.turn = Nationality.RED;
		this.selectedTile = null;
		this.countdownTasks = new ArrayList<CountdownTask>();
	}

	/**
	 * Initiates game. Necessary because {@code utils.game} reference must be
	 * established before initiation.
	 */
	public void init()
	{
		// Init nations
		Nation redNat = new Nation(Nationality.RED, utils);
		Nation greenNat = new Nation(Nationality.GREEN, utils);
		Nation blueNat = new Nation(Nationality.BLUE, utils);
		Nation yellowNat = new Nation(Nationality.YELLOW, utils);
		nations = new Nation[] {redNat, greenNat, blueNat, yellowNat};

		// Init cities
		redNat.addCity(tiles[0][0]);
		greenNat.addCity(tiles[4][0]);
		blueNat.addCity(tiles[0][4]);
		yellowNat.addCity(tiles[4][4]);

		// Init units in cities
		redNat.addSettler(1, 0, 0);
		greenNat.addSettler(1, 4, 0);
		blueNat.addSettler(1, 0, 4);
		yellowNat.addSettler(1, 4, 4);

		// TMP
		test();
	}

	/**
	 * Temporary method for running tests on GD
	 */
	private void test()
	{
		// Move red settler unit up one
		tiles[0][0].settlers.get(0).move(tiles[0][1]);

		// Build a city on that square
		tiles[0][1].settlers.get(0).buildCity();

		// Add red soldier and blue soldier
		nations[0].addSoldier(4, 0, 0);
		nations[2].addSoldier(4, 0, 4);

		// Move red soldier up 2 and blue soldier down 1
		tiles[0][0].soldiers.get(0).move(tiles[0][2]);
		tiles[0][4].soldiers.get(0).move(tiles[0][3]);

		// Red soldier attack blue soldier
		tiles[0][2].soldiers.get(0).attackTile(tiles[0][3]);

		// Display hp or unit dead
		try
		{
			JOptionPane.showMessageDialog(gw, String
					.valueOf(tiles[0][2].soldiers.get(0).currentHealthPoints));
		}
		catch (IndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(gw, "Unit dead");
		}

		try
		{
			JOptionPane.showMessageDialog(gw, String
					.valueOf(tiles[0][3].soldiers.get(0).currentHealthPoints));
		}
		catch (IndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(gw, "Unit dead");
		}
	}

	/**
	 * Change {@code selectedTile}
	 *
	 * @param tileToSelect
	 *            new selected {@code Tile}
	 */
	public void selectTile(Tile tileToSelect)
	{
		selectedUnit = null;
		if (selectedTile != null)
		{
			selectedTile.isSelected = false;
		}
		if (tileToSelect != null)
		{
			tileToSelect.isSelected = true;
		}
		this.selectedTile = tileToSelect;
	}

	public void selectUnit(Unit u)
	{
		selectedUnit = u;
	}

	/**
	 * Update {@code gw}
	 */
	public void updateWindow()
	{
		Map<String, Object> diffs = new HashMap<String, Object>();

		// TODO Implement this correctly
		if (selectedTile != null)
		{
			if ((selectedTile.soldiers.size() + selectedTile.settlers.size()) != 0)
			{
				StyledDocument doc = new DefaultStyledDocument();
				try
				{
					for (Soldier u : selectedTile.soldiers)
					{
						if (u.equals(selectedUnit))
						{
							doc.insertString(doc.getLength(),
									GameWindow.IMAGE_STRING, gw.pointer);
							doc.insertString(doc.getLength(), " ", gw.body);
						}
						doc.insertString(doc.getLength(),
								GameWindow.IMAGE_STRING,
								gw.soldierImages[u.level - 1]);
						doc.insertString(doc.getLength(), " Soldier Unit ("
								+ u.nation.nationality.toString() + ")\n",
								gw.body);
					}
					for (Settler u : selectedTile.settlers)
					{
						if (u.equals(selectedUnit))
						{
							doc.insertString(doc.getLength(),
									GameWindow.IMAGE_STRING, gw.pointer);
							doc.insertString(doc.getLength(), " ", gw.body);
						}
						doc.insertString(doc.getLength(),
								GameWindow.IMAGE_STRING,
								gw.settlerImages[u.level - 1]);
						doc.insertString(doc.getLength(), " Settler Unit ("
								+ u.nation.nationality.toString() + ")\n",
								gw.body);
					}
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
				diffs.put("units", doc);
			}
			else
			{
				diffs.put("units", "(no units)\n");
			}

			diffs.put("tile", "Revenue: " + selectedTile.tileRevenue
					+ "\nProductivity: " + selectedTile.tileProductivity + "\n");

			if (selectedTile.city != null)
			{

				if (selectedTile.city.isGrowing)
				{
					diffs.put("city", "Is growing a level "
							+ selectedTile.city.growUnitLevel + " "
							+ selectedTile.city.growUnitType + " unit\n");
				}
				else
				{
					diffs.put("city", "This tile has a city.\n");
				}
			}
			else
			{
				diffs.put("city", "(no city)\n");
			}

			// TODO finish
		}
		else
		{
			diffs.put("units", "(no tile selected)");
			diffs.put("city", "(no tile selected)");
			diffs.put("tile", "(no tile selected)");
		}
		gw.updateTextPanes(diffs);

		gw.infoBox(turn + " to move");

		gw.repaint();
	}

	/**
	 * Switch to the next player's turn
	 */
	public void nextTurn()
	{
		switch (turn)
		{
			case RED:
			{
				turn = Nationality.YELLOW;
				break;
			}
			case YELLOW:
			{
				turn = Nationality.GREEN;
				break;
			}
			case GREEN:
			{
				turn = Nationality.BLUE;
				break;
			}
			case BLUE:
			{
				turn = Nationality.RED;
				break;
			}
			default:
				break;
		}
		gw.eventLog("Turn #: " + utils.game.turn);

		ArrayList<CountdownTask> newTaskList = new ArrayList<CountdownTask>();

		for (CountdownTask t : countdownTasks)
		{
			t.decrease();
			if (!t.hasRun)
			{
				newTaskList.add(t);
			}
		}

		countdownTasks = newTaskList;
	}
}
