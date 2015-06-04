/*******************************************************************************
 * Global Domination is a strategy game.
 * Copyright (C) 2014, 2015  LQTZ Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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

		utils.gw.eventLog(nation.nationality
				+ " has initiated growing of a level " + level + " " + ut
				+ " unit on " + tile + ".\nThis will take " + (level * 2)
				+ " moves.");

		utils.game.countdownTasks.add(new CountdownTask(level * 2)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void run()
			{
				City.this.stopGrowing();
				City.this.utils.gw.eventLog(nation.nationality
						+ " has grown a level " + level + " " + ut
						+ " unit on " + tile + ".");

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

	/**
	 * Reinstate {@code transient} fields
	 *
	 * @param utils
	 *            new {@code Utils}
	 */
	public void onDeserialization(Utils utils)
	{
		this.utils = utils;
	}
}
