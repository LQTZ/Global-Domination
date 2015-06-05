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
package com.lqtz.globaldomination.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images
{
	/**
	 * Background image
	 */
	public BufferedImage background;

	/**
	 * City icon
	 */
	public BufferedImage city;

	/**
	 * Productivity icon
	 */
	public BufferedImage productivity;

	/**
	 * Revenue icon
	 */
	public BufferedImage revenue;

	/**
	 * General military icon (for tiles)
	 */
	public BufferedImage soldier;

	/**
	 * General settler icon (for tiles)
	 */
	public BufferedImage settler;

	/**
	 * Soldier icons
	 */
	public BufferedImage[] soldiers;

	/**
	 * Settler icons
	 */
	public BufferedImage[] settlers;

	/**
	 * Pointer icon (yellow triangle next to selected things)
	 */
	public BufferedImage pointer;

	/**
	 * Load images
	 * 
	 * @throws IOException
	 *             error loading images
	 */
	public Images() throws IOException
	{
		background = ImageIO.read(getClass().getResourceAsStream(
				"/images/background.png"));
		city = ImageIO.read(getClass().getResourceAsStream("/images/city.png"));
		productivity = ImageIO.read(getClass().getResourceAsStream(
				"/images/productivity.png"));
		revenue = ImageIO.read(getClass().getResourceAsStream(
				"/images/revenue.png"));
		soldier = ImageIO.read(getClass().getResourceAsStream(
				"/images/soldier.png"));
		settler = ImageIO.read(getClass().getResourceAsStream(
				"/images/settler.png"));
		soldiers = new BufferedImage[10];
		for (int i = 0; i < soldiers.length; i++)
		{
			soldiers[i] = ImageIO.read(getClass().getResourceAsStream(
					"/images/soldier/level" + (i + 1) + ".png"));
		}

		settlers = new BufferedImage[5];
		for (int i = 0; i < settlers.length; i++)
		{
			settlers[i] = ImageIO.read(getClass().getResourceAsStream(
					"/images/settler/level" + (i + 1) + ".png"));
		}

		pointer = ImageIO.read(getClass().getResourceAsStream(
				"/images/pointer.png"));
	}
}
