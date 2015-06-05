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
package com.lqtz.globaldomination.graphics;

import java.awt.Polygon;
import java.io.Serializable;

public class Hexagon extends Polygon implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Hexagon-shaped {@code Polygon} to represent map {@code Tile}s
	 * 
	 * @param centerY
	 *            x-coordinate of center
	 * @param centerX
	 *            y-coordinate of center
	 * @param size
	 *            radius of circle the {@code Hexagon} is inscribed in (must be
	 *            a multiple of 8 for {@code Polygon} constructor to work)
	 */
	public Hexagon(int centerX, int centerY, int size)
	{
		super(new int[] {centerX, centerX + 7 * size / 8,
				centerX + 7 * size / 8, centerX, centerX - 7 * size / 8,
				centerX - 7 * size / 8}, new int[] {centerY + size,
				centerY + size / 2, centerY - size / 2, centerY - size,
				centerY - size / 2, centerY + size / 2}, 6);
	}
}