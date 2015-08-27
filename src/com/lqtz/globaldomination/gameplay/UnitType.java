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

public enum UnitType implements Serializable
{

	/**
	 * {@code Settler}'s {@code UnitType}
	 */
	SETTLER,

	/**
	 * {@code Soldier}'s {@code UnitType}
	 */
	SOLDIER;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString()
	{
		switch (this)
		{
			case SETTLER:
				return "Settler";
			case SOLDIER:
				return "Soldier";
		}
		return null;
	}

	/**
	 * Get {@code UnitType} from a {@code String}
	 *
	 * @param s
	 *            {@code String} to translate
	 * @return {@code UnitType} the {@code String} represents
	 */
	public static UnitType fromString(String s)
	{
		switch (s)
		{
			case "Settler":
				return SETTLER;
			case "Soldier":
				return SOLDIER;
			default:
				return null;
		}
	}
}
