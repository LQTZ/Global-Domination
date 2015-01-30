package com.lqtz.globaldomination.gameplay;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class City
{
	private Utils utils;

	public Nation nation;
	public Tile tile;

	public City(Tile tile, Nation nation, Utils utils)
	{
		this.utils = utils;

		this.tile = tile;
		this.nation = nation;
	}

	public void growUnit(final UnitType ut, final int level)
	{
		utils.game.countdownTasks.add(new CountdownTask(level * 2)
		{
			@Override
			public void run()
			{
				if (ut == UnitType.SETTLER)
				{
					nation.addSettler(level, tile.xCoord, tile.yCoord);
				}
				else if (ut == UnitType.SOLDIER)
				{
					nation.addSoldier(level, tile.xCoord, tile.yCoord);
				}
			}
		});
	}
}
