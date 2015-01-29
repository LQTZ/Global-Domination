package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import com.lqtz.globaldomination.graphics.GameWindow;
import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Tile selectedTile = null;

	private transient Utils utils;
	private transient GameWindow gw;

	public Tile[][] tiles;

	public Game(Utils utils, GameWindow gw, Tile[][] tiles)
	{
		this.tiles = tiles;
		this.utils = utils;
		this.gw = gw;
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

		gw.eventLog("System> Testing 1 2 3 4 5 6 7 8 9");
		for (int i = 0; i < 100; i++)
		{
			gw.eventLog("System> Testing #" + i);
		}
	}

	public void selectTile(Tile selectedTile)
	{
		if (selectedTile != null)
		{
			selectedTile.isSelected = true;
		}
		this.selectedTile = selectedTile;

		updateWindow();
	}

	public void updateWindow()
	{
		Map<String, Object> diffs = new HashMap<String, Object>();

		// TODO Implement this correctly
		if (selectedTile != null)
		{
			if ((selectedTile.soldiers.size() + selectedTile.settlers.size())
					!= 0)
			{
				StyledDocument doc = new DefaultStyledDocument();
				try
				{
					for (Soldier u : selectedTile.soldiers)
					{
						doc.insertString(doc.getLength(),
								GameWindow.IMAGE_STRING,
								gw.soldierImages[u.level - 1]);
						doc.insertString(doc.getLength(), " Soldier Unit\n",
								gw.body);
					}
					for (Settler u : selectedTile.settlers)
					{
						doc.insertString(doc.getLength(),
								GameWindow.IMAGE_STRING,
								gw.settlerImages[u.level - 1]);
						doc.insertString(doc.getLength(), " Settler Unit\n",
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

			// TODO finish
		}
		else
		{
			diffs.put("units", "(no tile selected)");
			diffs.put("city", "(no tile selected)");
			diffs.put("tile", "(no tile selected)");
		}
		gw.updateTextPanes(diffs);
	}
}
