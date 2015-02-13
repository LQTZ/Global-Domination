package com.lqtz.globaldomination.gameplay;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class City
{
	private Utils utils;

	public Nation nation;
	public Tile tile;
	public boolean isGrowing = false;
	public UnitType growUnitType;
	public int growUnitLevel;

	public City(Tile tile, Nation nation, Utils utils)
	{
		this.utils = utils;

		this.tile = tile;
		this.nation = nation;
		
		this.growUnitType = null;
		this.growUnitLevel = -1;
	}

	public void growUnit(final UnitType ut, final int level)
	{
		growUnitType = ut;
		growUnitLevel = level;
		isGrowing = true;
		
		utils.game.countdownTasks.add(new CountdownTask(level * 2)
		{
			@Override
			public void run()
			{
				City.this.stopGrowing();
				
				if (ut == UnitType.SETTLER)
				{
					nation.addSettler(level, tile.xCoord, tile.yCoord);
				}
				else if (ut == UnitType.SOLDIER)
				{
					nation.addSoldier(level, tile.xCoord, tile.yCoord);
				}
				growUnitType = null;
				growUnitLevel = -1;
			}
		});
		utils.game.updateWindow();
	}
	
	public void stopGrowing()
	{
		isGrowing = false;
	}
}
