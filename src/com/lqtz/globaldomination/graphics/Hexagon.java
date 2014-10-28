package com.lqtz.globaldomination.graphics;

import java.awt.Polygon;

/**
 * A hexagon (almost regular, but not quite, since <code>java.awt.Polygon</code>
 * only accepts ints)
 */
public class Hexagon extends Polygon
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param centerX
	 *            x-coordinate of center
	 * @param centerY
	 *            y-coordinate of center
	 * @param size
	 *            radius of circumscribed circle (multiple of 8)
	 */
	public Hexagon(int centerX, int centerY, int size)
	{
		super(new int[] {centerX + size, centerX + size / 2,
				centerX - size / 2, centerX - size, centerX - size / 2,
				centerX + size / 2}, new int[] {centerY,
				centerY + 7 * size / 8, centerY + 7 * size / 8, centerY,
				centerY - 7 * size / 8, centerY - 7 * size / 8}, 6);

	}
}