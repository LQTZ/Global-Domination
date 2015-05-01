package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class City implements Serializable
{
	private static final long serialVersionUID = 1L;
	private transient Utils utils;

	/**
	 * {@code Nation} of the {@code City}
	 */
	public Nation nation;

	/**
	 * {@code Tile} the {@code City} is on
	 */
	public Tile tile;

	/**
	 * Whether or not the {@code City} is growing a {@code Unit}
	 */
	public boolean isGrowing = false;

	/**
	 * Type of the {@code Unit} the {@code City} is growing
	 */
	public UnitType growUnitType;

	/**
	 * {@code level} of the {@code Unit} the {@code City} is growing
	 */
	public int growUnitLevel;

	/**
	 * {@code City} on a {@code Tile} that grows {@code Unit}s
	 * 
	 * @param tile
	 *            {@code Tile} the {@code City} is on
	 * @param nation
	 *            {@code Nation} the {@code City} belongs to
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public City(Tile tile, Nation nation, Utils utils)
	{
		this.utils = utils;

		this.tile = tile;
		this.nation = nation;

		this.growUnitType = null;
		this.growUnitLevel = -1;
	}

	/**
	 * Grow a unit in the city (dispatch a {@code CountdownTask} that creates a
	 * unit)
	 * 
	 * @param ut
	 *            {@code UnitType} of the {@code Unit} to grow
	 * @param level
	 *            {@code level} of the {@code Unit} to grow
	 */
	public void growUnit(final UnitType ut, final int level)
	{
		growUnitType = ut;
		growUnitLevel = level;
		isGrowing = true;

		utils.game.countdownTasks.add(new CountdownTask(level * 2)
		{
			private static final long serialVersionUID = 1L;

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
	}

	/**
	 * Finish growing the {@code Unit}
	 */
	public void stopGrowing()
	{
		isGrowing = false;
	}

	public void onDeserialization(Utils utils)
	{
		this.utils = utils;
	}
}
