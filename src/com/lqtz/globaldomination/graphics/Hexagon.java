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