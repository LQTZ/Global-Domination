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

public enum Nationality implements Serializable
{
	/**
	 * Red {@code Nation}'s {@code Nationality}
	 */
	RED,

	/**
	 * Green {@code Nation}'s {@code Nationality}
	 */
	GREEN,

	/**
	 * Blue {@code Nation}'s {@code Nationality}
	 */
	BLUE,

	/**
	 * Yellow {@code Nation}'s {@code Nationality}
	 */
	YELLOW,

	/**
	 * Neutral {@code Nationality}
	 */
	NEUTRAL;

	@Override
	public String toString()
	{
		switch (this)
		{
			case RED:
				return "Red";
			case GREEN:
				return "Green";
			case BLUE:
				return "Blue";
			case YELLOW:
				return "Yellow";
			case NEUTRAL:
				return "Neutral";
			default:
				return "";
		}
	}
}
