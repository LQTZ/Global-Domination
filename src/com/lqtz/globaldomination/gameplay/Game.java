package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

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
		gw.eventLog("System> Testing 1 2 3");
		gw.eventLog("System> Testing 1 2 3");
	}
	
	public void selectTile(Tile selectedTile)
	{
		if (this.selectedTile != null)
		{
			this.selectedTile.isSelected = false;
		}
		selectedTile.isSelected = true;
		this.selectedTile = selectedTile;
	}
}
