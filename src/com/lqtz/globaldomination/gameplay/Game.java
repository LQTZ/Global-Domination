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
		Map<String, Object> diffs = new HashMap<String, Object>();

		// TODO Implement this correctly
		if (selectedTile != null)
		{
			diffs.put("game", String.valueOf(utils.random.nextGaussian()));
			StyledDocument doc = new DefaultStyledDocument();
			try
			{
				doc.insertString(0, GameWindow.IMAGE_STRING,
						gw.unitImages[utils.random.nextInt(10)]);
				doc.insertString(1, "HI!", gw.body);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
			diffs.put("units", doc);
		}
		else
		{
			diffs.put("units", "(no tile selected)");
		}
		gw.updateTextPanes(diffs);
	}
}
