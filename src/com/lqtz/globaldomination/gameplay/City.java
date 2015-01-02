package com.lqtz.globaldomination.gameplay;

import java.util.ArrayList;

import com.lqtz.globaldomination.graphics.Tile;

public class City
{
	public Tile tile;
	public ArrayList<Unit> units;
	
	// For now
	public int soldierCount = 0;
	public int settlerCount = 0;
	
	public City(Tile tile)
	{
		this.tile = tile;
		units = new ArrayList<Unit>();
	}
}
