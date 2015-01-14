package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

import com.lqtz.globaldomination.graphics.GameWindow;
import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;
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
	}
}
