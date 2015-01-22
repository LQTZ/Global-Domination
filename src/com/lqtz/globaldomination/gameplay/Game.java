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
	private Tile[][] tiles;

	public Game(Utils utils, GameWindow gw, Tile[][] tiles)
	{
		this.tiles = tiles;
		this.utils = utils;
		this.gw = gw;

		tiles[0][0].nat = Nationality.RED;
		tiles[0][0].city = new City(tiles[0][0]);
		tiles[0][4].nat = Nationality.YELLOW;
		tiles[0][4].city = new City(tiles[0][4]);
		tiles[4][0].nat = Nationality.GREEN;
		tiles[4][0].city = new City(tiles[4][0]);
		tiles[4][4].nat = Nationality.BLUE;
		tiles[4][4].city = new City(tiles[4][4]);

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
			if (selectedTile.units.size() != 0)
			{
				StyledDocument doc = new DefaultStyledDocument();
				try
				{

					for (Unit u : selectedTile.units)
					{
						doc.insertString(doc.getLength(),
								GameWindow.IMAGE_STRING,
								gw.unitImages[u.level]);
						if (u instanceof Soldier)
						{
							doc.insertString(doc.getLength(),
									" Soldier Unit\n", gw.body);
						}
						else
						{
							doc.insertString(doc.getLength(),
									" Settler Unit\n", gw.body);
						}
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
			
			diffs.put("tile", "Productivity: " + selectedTile.tileProductivity
					+ "\nRevenue: " + selectedTile.tileRevenue + "\n");
			
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
