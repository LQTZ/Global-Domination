package com.lqtz.globaldomination.graphics;

import java.awt.Polygon;

/**
 * 
 * A hexagon (almost regular, but not quite, since <code>java.awt.Polygon</code>
 * only accepts ints)
 * 
 * @author Gitdropcode
 * 
 */
public class Hexagon extends Polygon
{
	private static final long serialVersionUID = 1L;

	/**
	 * Hexagon Polygon to represent map tiles
	 * 
	 * @param centerY
	 *            x-coordinate of center
	 * @param centerX
	 *            y-coordinate of center
	 * @param size
	 *            radius of circle the hexagon is inscribed in (must be a
	 *            multiple of 8 for polygon constructor to work)
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