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
	public Nationality turnNationality;

	/**
	 * Turn number
	 */
	public float turnNum = 0;

	/**
	 * Map of {@code Tile}s
	 */
	public Tile[][] tiles;

	/**
	 * Currently selected (clicked) {@code Tile}
	 */
	public transient Tile selectedTile;

	/**
	 * Currently selected (clicked) {@code Unit}
	 */
	public transient Unit selectedUnit;

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

		this.turnNationality = Nationality.RED;
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

		gw.eventLog("Turn #: " + (int) utils.game.turnNum);
		gw.newTurn(turnNationality);
		// Temp
		// test();
	}

	/**
	 * Temporary method for running tests on GD
	 */
	@SuppressWarnings("unused")
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
		if (selectedTile != null)
		{
			selectedTile.isSelected = false;
		}
		if (tileToSelect != null)
		{
			gw.togglePane(2);
			tileToSelect.isSelected = true;
		}
		else
		{
			gw.togglePane(0);
		}
		this.selectedTile = tileToSelect;
	}

	/**
	 * Change {@code selectedUnit}
	 * 
	 * @param unitToSelect
	 *            new selected {@code Unit}
	 */
	public void selectUnit(Unit unitToSelect)
	{
		selectedUnit = unitToSelect;
		if (selectedUnit instanceof Settler)
		{
			gw.togglePane(1);
			utils.game.gw.unitButtons[1].setEnabled(false);
			utils.game.gw.unitButtons[2].setEnabled(true);
		}
		else if (selectedUnit instanceof Soldier)
		{
			gw.togglePane(1);
			utils.game.gw.unitButtons[2].setEnabled(false);
			utils.game.gw.unitButtons[1].setEnabled(true);
		}
		else
		{
			gw.togglePane(0);
		}
	}

	//

	/**
	 * Grow a unit selected by a {@code JOptionPane}
	 * 
	 * @return Error value (-1 if city belongs to someone else, -2 if city
	 *         already building something)
	 */
	public int growUnit()
	{
		// TODO Make growUnit() actually work

		// Make sure city belongs to current player
		if (selectedTile.nat != turnNationality)
		{
			return -1;
		}

		// Make sure city not busy
		if (selectedTile.city.isGrowing)
		{
			return -2;
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
						+ "right now?", "Grow Unit", JOptionPane.PLAIN_MESSAGE,
				null, possibilities, "--");

		// Check for null string
		if (!((s == null) || (s == "--")))
		{
			String utString = s.substring(0, 7);
			int ul = Integer.parseInt(s.substring("Settler Level ".length(),
					s.length()));

			int confirm = JOptionPane.showConfirmDialog(gw,
					"You are about to grow a unit. This cannot be"
							+ " cancelled.", "Grow Unit Confirmation",
					JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.OK_OPTION)
			{
				selectedTile.city.growUnit(UnitType.fromString(utString), ul);
			}
		}

		return 0;
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

			String tileInfoStr = "";

			// City
			if (selectedTile.city != null)
			{
				tileInfoStr = tileInfoStr + "Has a "
						+ utils.game.selectedTile.nat.toString() + " city.\n";

				// Unit being grown
				if (selectedTile.city.isGrowing)
				{
					tileInfoStr = tileInfoStr + "The city is growing a level "
							+ selectedTile.city.growUnitLevel + " "
							+ selectedTile.city.growUnitType + " unit\n";
				}
				tileInfoStr = tileInfoStr + "\n";
			}

			// Number of units
			tileInfoStr = tileInfoStr + "Has "
					+ String.valueOf(utils.game.selectedTile.settlers.size())
					+ " settlers,\n";
			tileInfoStr = tileInfoStr + "and "
					+ String.valueOf(utils.game.selectedTile.soldiers.size())
					+ " soldiers.";

			diffs.put("tile", tileInfoStr);
		}
		else
		{
			diffs.put("units", "(no tile selected)");
			diffs.put("tile", "(no tile selected)");
		}

		if (selectedUnit != null)
		{
			String unitInfoStr = "";

			unitInfoStr = unitInfoStr + "Level:\t"
					+ String.valueOf(selectedUnit.level) + "\n\n";
			unitInfoStr = unitInfoStr + "Current Health Points:\t"
					+ String.valueOf(selectedUnit.currentHealthPoints) + "\n\n";
			unitInfoStr = unitInfoStr + "Max Health Points:\t"
					+ String.valueOf(selectedUnit.maxHealthPoints) + "\n\n";
			unitInfoStr = unitInfoStr + "Moves Left:\t"
					+ String.valueOf(selectedUnit.movesLeft) + "\n\n";
			unitInfoStr = unitInfoStr + "Defend Power:\t"
					+ String.valueOf(selectedUnit.defendPower) + "\n\n";

			if (selectedUnit instanceof Settler
					&& ((Settler) selectedUnit).isBuilding)
			{
				unitInfoStr = unitInfoStr + "Will finish city in "
						+ String.valueOf(((Settler) selectedUnit).turnsToCity)
						+ " turn.\n\n";
			}

			if (selectedUnit instanceof Soldier)
			{
				unitInfoStr = unitInfoStr + "Attack Power:\t"
						+ String.valueOf(((Soldier) selectedUnit).attackPower)
						+ "\n\n";
			}

			diffs.put("selectedUnit", unitInfoStr);
		}
		else
			diffs.put("selectedUnit", "(no unit selected)");

		gw.updateTextPanes(diffs);

		gw.infoBox(turnNationality + " to move");
		gw.newTurn(turnNationality);

		gw.repaint();
	}

	/**
	 * Switch to the next player's turn
	 */
	public void nextTurn()
	{
		Nationality win = tiles[0][0].nat;
		for (Tile[] tt : tiles)
		{
			for (Tile t : tt)
			{
				if (win != null)
				{
					if (t.nat != win)
					{
						win = null;
					}
				}
			}
		}
		if (win != null)
		{
			if (win == Nationality.NEUTRAL)
			{
				JOptionPane.showMessageDialog(gw, "All units dead.", "Draw",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(gw, win.toString() + " wins!",
						"Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
			gw.exit();
			return;
		}

		switch (turnNationality)
		{
			case RED:
			{
				turnNationality = Nationality.YELLOW;
				break;
			}
			case YELLOW:
			{
				turnNationality = Nationality.GREEN;
				break;
			}
			case GREEN:
			{
				turnNationality = Nationality.BLUE;
				break;
			}
			case BLUE:
			{
				turnNationality = Nationality.RED;
				break;
			}
			default:
				break;
		}

		gw.newTurn(turnNationality);

		// Increment turnNum and if new turn log
		utils.game.turnNum += 0.25;
		if (utils.game.turnNum == (int) utils.game.turnNum)
			gw.eventLog("Turn #: " + (int) utils.game.turnNum);

		// Check for CountdownTests
		ArrayList<CountdownTask> newTaskList = new ArrayList<CountdownTask>();
		for (int i = 0; i < countdownTasks.size(); i++)
		{
			CountdownTask t = countdownTasks.get(i);
			t.decrease();
			if (!t.hasRun)
			{
				newTaskList.add(t);
			}

		}
		countdownTasks = newTaskList;
	}

	/**
	 * Call this when deserialized.
	 * 
	 * @param utils
	 * @param gw
	 */
	public void onDeserialization(Utils utils, GameWindow gw)
	{
		this.utils = utils;
		this.gw = gw;
		for (Tile[] tt : tiles)
		{
			for (Tile t : tt)
			{
				t.onDeserialization(utils);
			}
		}
		for (Nation n : nations)
		{
			n.onDeserialization(utils);
		}
	}
}
